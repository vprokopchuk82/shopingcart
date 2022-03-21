FROM java:8-jdk-alpine
COPY ./target/shopingcart-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/app/shoppingcart.jar
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shoppingcart.jar"]