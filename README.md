# WGUI

### Overview
The WireGuard UI is a web-based user interface that allows you to manage your WireGuard setup. It provides an intuitive and convenient way to configure and monitor your WireGuard VPN connections.
<br>
Please note that this application is specifically designed to work in a Linux environment.


### Technologies Used
The WireGuard UI application is built using the following technologies:

* Java
* Spring Boot
* Thymeleaf
* Hibernate
* H2 (embedded database)

### Configuration
The application configuration can be customized by modifying the `application.properties` file. Below are the configurable properties along with their default values:

```
# Database Configuration
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:h2:file:./wgdb;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE}
spring.datasource.driverClassName=${SPRING_DATASOURCE_DRIVER_CLASS_NAME:org.h2.Driver}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:sa}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:}

# JPA Configuration
spring.jpa.database-platform=${SPRING_JPA_DATABASE_PLATFORM:org.hibernate.dialect.H2Dialect}
spring.jpa.hibernate.ddl-auto=${SPRING_JPA_HIBERNATE_DDL_AUTO:create}

# H2 Console Configuration
spring.h2.console.enabled=${SPRING_H2_CONSOLE_ENABLED:true}

# Server Error Configuration
server.error.include-exception=${SERVER_ERROR_INCLUDE_EXCEPTION:true}
server.error.include-stacktrace=${SERVER_ERROR_INCLUDE_STACKTRACE:always}

# Admin Configuration
admin.email=${ADMIN_EMAIL:admin@admin.com}
admin.password=${ADMIN_PASSWORD:admin}

# WireGuard Configuration
wgc.endpointAddress=${WGC_ENDPOINT_ADDRESS:localhost}
wgc.address=${WGC_ADDRESS:10.13.13.1}
wgc.dnsServers=${WGC_DNS_SERVERS:}
wgc.mtu=${WGC_MTU:1450}
wgc.persistentKeepAlive=${WGC_PERSISTENT_KEEP_ALIVE:14}
wgc.fwMark=${WGC_FW_MARK:0xca6c}
wgc.listenPort=${WGC_LISTEN_PORT:51820}
wgc.postUp=${WGC_POST_UP:iptables -A FORWARD -i %i -j ACCEPT; iptables -A FORWARD -o %i -j ACCEPT; iptables -t nat -A POSTROUTING -o eth+ -j MASQUERADE}
wgc.postDown=${WGC_POST_DOWN:iptables -D FORWARD -i %i -j ACCEPT; iptables -D FORWARD -o %i -j ACCEPT; iptables -t nat -D POSTROUTING -o eth+ -j MASQUERADE}
wgc.preUp=${WGC_PRE_UP:}
wgc.preDown=${WGC_PRE_DOWN:}
wgc.wgConfFilePath=${WGC_WG_CONF_FILE_PATH:/etc/wireguard/wg0.conf}

```
Note: Modify these properties according to your specific requirements. It is recommended to set secure values for sensitive properties such as database credentials and admin password.

### Usage
1. Build and deploy the WireGuard UI application using the provided technologies (Java, Spring Boot, etc.).
2. Access the application using a web browser.
3. Use the web user interface to configure and manage your WireGuard setup.
4. Refer to the user interface documentation for detailed instructions on how to use each feature.

### Support and Contributions
If you encounter any issues or have suggestions for improvement, please feel free to create an issue on the project's GitHub repository. Contributions and pull requests are also welcome.