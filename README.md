# Recipe Conformity Project

## Purpose

To create an open-source, public, machine-readable standard for recipes.
This could enable:
* Complex programmatic search, e.g. I have x, y, z. What can I make? Or: I want a recipe for A, but I can't eat B.
* Aggregation of recipes from multiple sources in a consistent format.
* Automatic scaling of recipes to adjust all quantities while preserving the ratios of ingredients.
* Automatic conversion of recipes from metric units to antiquated ones and vice versa.
* Automatic creation of shopping lists or shopping carts for online shopping.
* Automatic time/cost estimation for recipes.
* Automatic application of common substitutions.

## TODO:
1. build a recipe composition tool
2. build a recipe rendering tool
3. create a library of example recipes

### Composition
Free text editor, with some cleverness?
Paste in existing recipes and have them interpreted?

### Rendering
Possibly the simplest part? But technology! Options:
1. Cljs render with reagent/react components?
2. Js render by parsing JSON and creating html -> setInnerHtml?
3. Js render by creating document element objects?

### Library
1. initially in a git repo
2. can create a web interface that loads all the recipes straight from a github repo over http into memory
   1. how scalable is this? 100 recipes should be absolutely fine. 1000, probably fine. 10,000...? 
   2. I'm unlikely to personally curate >1000 recipes
3. behind a web server?
   1. could build a specialist website, recipes indexed in a db
   2. would allow for user-feedback, e.g. ratings
   3. would allow for user-submitted recipes