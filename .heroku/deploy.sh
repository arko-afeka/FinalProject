#!/bin/bash

heroku container:login
cd $TRAVIS_BUILD_DIR/dist/waf
heroku container:push -a arkokat-afeka-waf-staging $DOCKER_IMAGE
heroku container:release -a arkokat-afeka-waf-staging $DOCKER_IMAGE