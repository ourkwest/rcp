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

## Components
1. A recipe composer - a text editing tool to facilitate creating conforming recipe documents.
2. A recipe renderer - a web library for rendering conforming recipe documents.
3. A library - of conforming recipe documents.

### 1. Composer
#### Status
Not started

#### Vague thoughts
Free text editor, with some cleverness?
Paste in existing recipes and have them interpreted?

### 2. Renderer
#### Status
An initial prototype has been completed.
A demonstration page is viewable at [https://ourkwest.github.io/rcp](https://ourkwest.github.io/rcp) 

TODO:
   Improve layout on mobile devices

### 3. Library
#### Status
There is 1 recipe in this git repo at:
`library/lemoncake.json`
#### Long term plan / vague thoughts
1. initially in a git repo
2. can create a web interface that loads all the recipes straight from a github repo over http into memory
   1. how scalable is this? 100 recipes should be absolutely fine. 1000, probably fine. 10,000...? 
   2. I'm unlikely to personally curate >1000 recipes
3. behind a web server?
   1. could build a specialist website, recipes indexed in a db
   2. would allow for user-feedback, e.g. ratings
   3. would allow for user-submitted recipes