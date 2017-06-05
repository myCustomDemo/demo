#### Cassandra Docker image
**Building**

    cd $basedir/cassandra
    docker build -t cassandra .

**Running**

    cd $basedir/cassandra
    docker run -d --name cassandra -p 9042:9042 cassandra

#### Spring Boot Docker image
**Building**

    cd $basedir
    mvn package docker:build

**Running**

    docker run --name demo --link cassandra:db -p 8080:8080 demo/demo

### Inserting data directly on Cassandra

    docker exec -it cassandra bash
    cassandra$ ./bin/cqlsh
    cqlsh$ USE sample;
    cqlsh$ INSERT INTO users (id, data) values (1, 0x74657374);




