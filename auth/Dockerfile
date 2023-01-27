FROM amazoncorretto:19-alpine AS run
COPY build/libs/auth-*-SNAPSHOT.jar /usr/local/lib/app.jar
ENTRYPOINT ["java","-Xms32m","-Xmx500m","-jar","/usr/local/lib/app.jar"]
