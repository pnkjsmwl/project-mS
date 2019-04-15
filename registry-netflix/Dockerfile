FROM openjdk:8
MAINTAINER Pankaj Semwal <pnkjsmwl09@gmail.com>
ADD target/registry-netflix.jar registry-netflix.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "registry-netflix.jar"]