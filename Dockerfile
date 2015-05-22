FROM java:8u45-jdk

# install Maven
RUN apt-get update
RUN apt-get install -y maven

WORKDIR /code

# download, compile, test, package...
ADD pom.xml /code/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]
ADD src /code/src
RUN ["mvn", "package"]
# ... and go!
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/fuse-1.0-SNAPSHOT-jar-with-dependencies.jar"]
