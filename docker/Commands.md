# Pré-requisitos
    - docker
    - docker-compose

# Inicializando todas as imagens de uma só vez
docker-compose up -d

# Removendo containers inutilizados
docker-compose rm -f

# Interrompendo todas as imagens
docker-compose down
docker-compose stop scylla-1
docker-compose stop scylla-2
docker-compose stop scylla-3

# Logs do Scylla
docker logs -f scylla-1
docker logs -f scylla-2
docker logs -f scylla-3

# CQLSH
docker exec -it scylla-1 cqlsh

# Keyspace
docker exec -it scylla-1 cqlsh -e "CREATE KEYSPACE teste1 WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '3'}  AND durable_writes = true;"

docker exec -it scylla-1 cqlsh -e "DESCRIBE KEYSPACE teste"

docker exec -it scylla-1 cqlsh -e "DROP KEYSPACE teste1"
