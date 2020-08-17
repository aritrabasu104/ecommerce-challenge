# Spring Boot "Microservice" Project - ecommerce api

This is a sample Java / Spring Boot application Spring Caching,Exception Handling and Swagger. The api details can be found in http://localhost:8080/swagger-ui.html
Extra code/api present for testing setup

functionality: 

1. seller can post product with details
2. User can add product to users cart
3. User can checkout
4. User can search product, filtered on search string, paginated by provided pagesize and pagenumber and sorted on any 1 column
## How to Run 

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you have JRE/JDK 1.8 or higher 
```
        java -jar  tomtom-ecommerce-1.0.jar
or
        mvnw spring-boot:run 
```