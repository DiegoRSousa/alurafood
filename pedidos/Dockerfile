FROM openjdk:17
VOLUME /tmp
ADD ./target/pedidos-0.0.1-SNAPSHOT.jar pedidos.jar
ENTRYPOINT ["java","-jar","/pedidos.jar"]