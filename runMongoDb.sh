#!/bin/sh
docker run -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=pass -d -p 27017:27017 mongo
