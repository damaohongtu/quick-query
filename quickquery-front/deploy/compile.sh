#!/usr/bin/env bash
# npm run build:prod

docker build -f ./Dockerfile -t quickquery-front:0.0.1 .
