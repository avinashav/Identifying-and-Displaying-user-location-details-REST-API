FROM https://github.com/avinashav/Identifying-and-Displaying-user-location-details-REST-API

ARG JAR_FILENAME
ADD $JAR_FILENAME app.jar
ARG PORT_NBR
EXPOSE $PORT_NBR
ENTRYPOINT ["sh", "-c", "dockerize -timeout $WAIT_HEALTH_TIMEOUT -wait $WAIT_HEALTH_URL; java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]