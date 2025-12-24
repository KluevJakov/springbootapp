FROM bellsoft/liberica-runtime-container:jre-21-slim-stream-glibc
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]