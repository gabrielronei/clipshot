FROM maven:3-amazoncorretto-23 as build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests \
    && rm -rf /root/.m2

FROM amazoncorretto:23-alpine

WORKDIR /app

COPY --from=build /app/target/clipshot.jar .

CMD ["java", "-jar", "clipshot.jar"]
