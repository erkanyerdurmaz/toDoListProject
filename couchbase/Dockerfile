FROM java:8
VOLUME /tmp
ADD target/todo-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]