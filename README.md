# alurafood

## Requirements

For building and running the application you need:
- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3](https://maven.apache.org)

## Running the application locally

```shell
docker run --name alurafood -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:8.0.30  
cd ./server
mvn spring-boot:run

cd ../gateway
mvn spring-boot:run

cd ../pedidos
mvn spring-boot:run

cd ../pedidos
mvn spring-boot:run
```

Open the follow url
http://localhost:8081/
