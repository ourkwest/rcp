(ns recipes)

(defn GET [url callback]
  (let [xhr (js/XMLHttpRequest.)]
    (set! (.-onreadystatechange xhr)
          #(when (= (.-readyState xhr) 4)
             (callback (.-responseText xhr))))
    (.open xhr "GET" url true)
    (.send xhr nil)))

(defn GET-json [url callback]
  (GET url #(callback (.parse js/JSON %))))

(defn text [content-string]
  (.createTextNode js/document content-string))

(defn node
  ([node-type content] (node node-type {} content))
  ([node-type attrs content]
   (let [r (.createElement js/document node-type)]
     (doseq [[k v] (merge-with str {"style" "margin:5px 0px;"} attrs)]
       (.setAttribute r k v))
     (doseq [child content]
       (.appendChild r child))
     (.add (.-classList r) "rcp")
     r)))

(defn describe-ingredient [ingredient]
  (if (string? ingredient)
    ingredient
    (let [[thing qty unit] ingredient]
      (str qty (when (< 3 (count unit)) " ") unit " " thing))))

(defn describe [recipe [kind :as path]]
  (case kind
    "method" (node "span" {"style" "font-style: oblique;"} [(text (:desc (get-in recipe path)))])
    "ingredients" (node "span" {"style" "font-weight: bold;"} [(text (describe-ingredient (get-in recipe path)))])
    "equipment" (node "span" {"style" "text-decoration: underline;"} [(text (get-in recipe path))])))

(defn render-map [title m f]
  (when (seq m)
    (node "div" {"style" (str "display: inline-block;"
                              "vertical-align: top;"
                              "width: 40%")}
          [(node "h4" [(text title)])
           (node "ol" (for [[_ v] m]
                        (node "li" [(text (f v))])))])))

(defn parse-dep [path]
  (if (vector? path)
    (let [[[t n a]] path]
      (cond-> [({"m" "method"
                 "i" "ingredients"
                 "e" "equipment"} t) n]
              a (conj a)))
    path))

(defn parse-step [[desc & text :as step]]
  (let [parsed-text (map parse-dep text)
        parsed-step {:text parsed-text
                     :deps (filter vector? parsed-text)}]
    (if (string? desc)
      (assoc parsed-step :desc desc)
      (reduce-kv (fn [s k v]
                   (assoc s k {:desc v}))
                 parsed-step
                 desc))))

(defn map-vals [m f]
  (reduce-kv (fn [s k v]
               (assoc s k (f v)))
             {}
             m))

(defn first-with-no-ingroup-deps [unlevelled-steps]
  (first (remove (fn [[_k step]]
                   (->> step
                        :deps
                        (filter #(and
                                   (= "method" (first %))
                                   (get-in unlevelled-steps (rest %))))
                        seq))
                 unlevelled-steps)))

(defn level-steps [method]
  (loop [unlevelled-steps (map-vals method parse-step)
         levelled-steps {}
         level 0]
    (if-let [[k step] (first-with-no-ingroup-deps unlevelled-steps)]
      (recur (dissoc unlevelled-steps k)
             (assoc levelled-steps k (assoc step :level level))
             (inc level))
      levelled-steps)))

(defn subs-deps [step-text recipe]
  (->> step-text
       (partition-all 3 1)
       (mapcat (fn [[a b c]]
                 (cond
                   (not b) [a]
                   (string? a) [a " "]
                   (string? b) [a " "]
                   (= c "and") [a ", "]
                   (not c) [a " and "]
                   (string? c) [a " and "]
                   :else [a ", "])))
       (map (fn [bit]
              (if (string? bit)
                (text bit)
                (describe recipe bit))))))

(defn ^:export renderRecipe [recipe recipe-url]
  (let [recipe (update recipe "method" level-steps (recipe "method"))
        steps (sort-by :level (vals (recipe "method")))
        final-step (last steps)
        notes (recipe "notes")]
    (node "div"
          [(node "h3" [(if recipe-url
                         (node "a" {"href" recipe-url} [(text (:desc final-step))])
                         (text (:desc final-step)))])
           (render-map "Ingredients" (recipe "ingredients") describe-ingredient)
           (render-map "Equipment" (recipe "equipment") identity)
           (node "div" [(node "h4" [(text "Method")])
                        (node "ol" (for [step steps]
                                     (node "li" (subs-deps (:text step) recipe))))])
           (when notes
             (node "div"
                   [(node "h4" [(text "Notes")])
                    (node "span" [(text notes)])]))])))

(defn displayRecipe [recipe-url element-id]
  (GET recipe-url
       (fn [recipe-str]
         (let [holder (.getElementById js/document element-id)
               recipe (js->clj (.parse js/JSON recipe-str))]
           (.appendChild holder (renderRecipe recipe recipe-url))))))

(defn reloaded []
  (set! (.-textContent (.getElementById js/document "recipeHolder")) "")
  (displayRecipe "lemoncake.json" "recipeHolder"))
