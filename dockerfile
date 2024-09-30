 FROM alpine:latest

# Install required packages
RUN apk add --no-cache wireguard-tools openjdk17

# Set environment variables
ENV JAVA_HOME=/usr/lib/jvm/default-jvm
ENV PATH=$JAVA_HOME/bin:$PATH
ENV PUID=1000
ENV GUID=1000
ENV SPRING_DATASOURCE_URL=jdbc:h2:file:./wgdb;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=user
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=create
ENV SPRING_H2_CONSOLE_ENABLED=true
ENV SERVER_ERROR_INCLUDE_EXCEPTION=true
ENV SERVER_ERROR_INCLUDE_STACKTRACE=always
ENV ADMIN_EMAIL=admin@admin.com
ENV ADMIN_PASSWORD=admin
ENV WGC_ENDPOINT_ADDRESS=localhost
ENV WGC_ADDRESS=10.13.13.1
ENV WGC_DNS_SERVERS=
ENV WGC_MTU=1450
ENV WGC_PERSISTENT_KEEP_ALIVE=14
ENV WGC_FW_MARK=0xca6c
ENV WGC_LISTEN_PORT=51820
ENV WGC_POST_UP=
ENV WGC_POST_DOWN=
ENV WGC_PRE_UP=
ENV WGC_PRE_DOWN=

# Create wireguard user and group
RUN addgroup -g $GUID wireguard && adduser -D -u $PUID -G wireguard wireguard

# Set working directory
WORKDIR /app

# Copy the Spring Boot application JAR file to the container
COPY target/*.jar ./app.jar

# Set permissions
RUN chown -R wireguard:wireguard /app

# Start the application
CMD ["java", "-jar", "app.jar"]

