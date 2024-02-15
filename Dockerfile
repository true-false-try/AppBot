FROM openjdk:17
EXPOSE 8094
COPY "/target/MentalBot-0.0.1-SNAPSHOT.jar" app.jar
CMD [ "java", "-jar", "app.jar" ]