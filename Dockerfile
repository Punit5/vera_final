FROM openjdk:8u131-jre-alpine
ADD target/shopping-flight-offers*.jar //
ENTRYPOINT java $JAVA_OPTS -jar /shopping-flight-offers*.jar