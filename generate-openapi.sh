#!/bin/bash
 
# Generate the code
docker run --rm --user $(id -u):$(id -g) \-v $(pwd):/app  \
  openapitools/openapi-generator-cli:v7.10.0 generate \
  -i /app/openapi/api.yaml -g kotlin-spring --config /app/openapi/config.json --output /app/app  
