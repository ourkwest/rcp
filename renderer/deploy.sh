#!/usr/bin/env bash

npx shadow-cljs release library

aws --profile deploy \
  s3 cp --acl public-read \
  ./public/js/main.js s3://ourkwest/public/rcp/rcp-lib.js

# deploys to https://ourkwest.s3.amazonaws.com/public/rcp/rcp-lib.js
# xdg-open 'https://ourkwest.s3.amazonaws.com/public/rcp/rcp-lib.js'
