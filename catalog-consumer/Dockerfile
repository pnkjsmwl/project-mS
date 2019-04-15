FROM openjdk:8
MAINTAINER Pankaj Semwal <pnkjsmwl09@gmail.com>
ADD target/docker-demo.jar docker-demo.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "docker-demo.jar"]