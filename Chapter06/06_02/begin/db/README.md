# PostgreSQL

## Lifecycle tasks

**Run PostgreSQL as a Docker container**

```bash
docker run --name polardb-catalog -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=polardb_catalog -p 5432:5432 -d postgres:13
```

**Stop PostgreSQL container**

```bash
docker stop polardb-catalog
```

**Start PostgreSQL container**

```bash
docker start polardb-catalog
```

**Remove PostgreSQL container**

```bash
docker rm polardb-catalog
```

## Managing tasks

**Starting an interactive psql console**

```bash
docker exec -it polardb-catalog psql -U admin -d polardb_catalog
```

**List all databases**

```
\list
```

**Connect to specific database**

```
\connect polardb_catalog
```

**Quit interactive psql console**

```
\quit
```
