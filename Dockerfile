FROM openjdk

WORKDIR /app

COPY target/Pedido-0.0.1-SNAPSHOT.jar /app/pedido.jar

ENTRYPOINT ["java", "-jar", "pedido.jar"]