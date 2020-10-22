# PostgreSQL

## Lifecycle tasks

### Run PostgreSQL as a Docker container

docker run --name polardb_catalog -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=polardb_catalog -p 5432:5432 -d postgres:13

### Stop PostgreSQL container

docker stop polardb_catalog

### Start PostgreSQL container

docker start polardb_catalog

### Remove PostgreSQL container

docker remove polardb_catalog

## Managing tasks

### Starting an interactive psql console

docker exec -it polardb_catalog psql -U admin -d polardb_catalog

### List all databases

\list

### Connect to specific database

\connect polardb_catalog

### Quit interactive psql console

\quit
