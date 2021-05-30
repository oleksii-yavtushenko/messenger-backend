FROM adoptopenjdk:11-jre-openj9
COPY ./ServerSide/target/ServerSide-1.0-SNAPSHOT-spring-boot.jar /server-side.jar
ENTRYPOINT ["java", "-jar", "server-side.jar"]
