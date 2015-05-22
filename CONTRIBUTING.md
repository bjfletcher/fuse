# Development

Development using either Docker or local Java 8 SDK and Maven.

## Docker

1. compile, test and package Fuse app: `make build` or `docker build -t bjfletcher/fuse .`
1. run Fuse app: `make run` or `docker run -it bjfletcher/fuse`

The first time usually takes a while. The subsequent runs should be faster.

## Local Java 8 SDK and Maven

1. compile, test, and package Fuse app: `mvn package`
1. run Fuse app: `java -jar target/fuse-1.0-SNAPSHOT-jar-with-dependencies.jar`
