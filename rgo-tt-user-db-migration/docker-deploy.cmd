docker-compose up -d
docker rmi michisig/tt-client-db:%1
docker container commit client-db michisig/tt-client-db:%1
docker image push michisig/tt-client-db:%1
docker-compose stop
docker rm client-db
docker rmi client-db:latest