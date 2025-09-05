FROM maven:3.9-eclipse-temurin-21 AS development

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

ENV SPRING_DEVTOOLS_RESTART_ENABLED=true
ENV SPRING_DEVTOOLS_RESTART_ADDITIONAL_PATHS=/app/src
ENV SPRING_DEVTOOLS_RESTART_POLL_INTERVAL=1000
ENV SPRING_DEVTOOLS_RESTART_QUIET_PERIOD=400

EXPOSE 8081

# Устанавливаем плагин для непрерывной компиляции
#RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.1.2:copy \
#    -Dartifact=org.apache.maven.plugins:maven-compiler-plugin:3.11.0

CMD ["mvn", "spring-boot:run"]

# CMD ["mvn", "spring-boot:run", \
#      "-Dspring-boot.run.addResources=true", \
#      "-Dspring-boot.run.watch.enabled=true", \
#      "-Dspring-boot.run.watchDirectory=/app/src", \
#      "-Dspring-boot.run.jvmArguments=-Dspring.devtools.restart.enabled=true -Dspring.devtools.restart.additional-paths=/app/src"]

     # CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.addResources=true"]

#CMD ["mvn", "spring-boot:run", \
#     "-Dspring-boot.run.addResources=true", \
#     "-Dspring-boot.run.watch.enabled=true", \
#     "-Dspring-boot.run.jvmArguments=-Dspring.devtools.restart.enabled=true"]
#CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Dspring.devtools.restart.enabled=true", "compile"]
#CMD ["mvn", "compile", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Dspring.devtools.restart.enabled=true"]
#CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Dspring.devtools.restart.enabled=true"]
#CMD ["mvn", "spring-boot:run"]
#     "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005",
#     "-Dspring-boot.run.profiles=dev"]
