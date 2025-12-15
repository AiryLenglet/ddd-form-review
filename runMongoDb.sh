#!/bin/sh
openssl rand -base64 756 > mongo-keyfile
chmod 600 mongo-keyfile
docker run -d \
-e MONGO_INITDB_ROOT_USERNAME=root \
-e MONGO_INITDB_ROOT_PASSWORD=pass \
-v mongo-keyfile:/etc/mongo/keyfile:ro \
-p 27017:27017 \
mongo \
--replSet rs0 \
--bind_ip_all \
--keyFile /etc/mongo/keyfile
