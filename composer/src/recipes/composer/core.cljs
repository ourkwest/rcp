(ns recipes.composer.core
  (:require
    [reagent.dom :as dom]
    [reagent.core :as r]))


(defonce !state (r/atom nil))




(defn render-app []
  (let [{:keys []} @!state]

    [:div "hello!"
     [:div "todo"]]
    ))

; paste a recipe in? all in one? separate ingredients? just a method?
; compose from scratch?
; Start with compose from scratch! - then try to be clever!

(defn mount-root []
  (dom/render [render-app] (.getElementById js/document "composer")))

(defn ^:export init []
  (mount-root))

(defn dev-reload []
  (println "dev-reload...")
  (mount-root))
