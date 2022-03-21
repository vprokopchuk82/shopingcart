# shopingcart
Project requared mysql database. I provide you docker-compose  file
```
version: "3"
services:
  database:
    container_name: database
    image: mysql:8.0.18
    ports:
      - 3307:3306
    volumes:
      - database_data:/var/lib/mysql
    networks:
      - bridge
    environment:
      - MYSQL_ROOT_PASSWORD=root
      - MYSQL_DATABASE=shop

volumes:
  database_data:
networks:
  bridge:
```  
  
  
Instruction for run application:
1. mvn clean package
2. java -jar target/shopingcart-1.0-SNAPSHOT-jar-with-dependencies.jar
