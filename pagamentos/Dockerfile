FROM openjdk:17
VOLUME /tmp
ADD ./target/pagamentos-0.0.1-SNAPSHOT.jar pagamentos.jar
ENTRYPOINT ["java","-jar","/pagamentos.jar"]