FROM java:8
VOLUME /tmp
ADD target/demo-1.0-SNAPSHOT.jar demo.jar
RUN sh -c 'touch /demo.jar'
ENTRYPOINT ["java","-jar","/demo.jar"]
