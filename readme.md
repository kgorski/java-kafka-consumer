# Java Kafka Consumer example

Simple Kafka Consumer that consumes message published by the [Producer](https://github.com/kgorski/java-kafka-producer)

## System Requirements / Dependencies
* java 1.7+
* Kafka 0.9+

## Installation
### Checkout the repository

    git clone git@github.com:kgorski/java-kafka-consumer.git

### Run maven package

    mvn package

## Setup
Copy config.properties.dist file into config.properties file and set Kafka ip and port:

    bootstrap.servers=localhost:9092

## Running

java -jar target\kafka.consumer-X.X.X.jar config.properties
