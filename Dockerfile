FROM amazoncorretto:17
RUN mkdir /app
WORKDIR /app
COPY target/*.jar /app/app.jar

CMD ["java","-jar", "/app/app.jar"]