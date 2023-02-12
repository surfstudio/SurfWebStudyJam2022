FROM amazoncorretto:17-alpine AS run
COPY build/libs/*-SNAPSHOT.jar /usr/local/lib/app.jar
ENTRYPOINT ["java","-Xms32m","-Xmx500m","-jar","/usr/local/lib/app.jar"]
