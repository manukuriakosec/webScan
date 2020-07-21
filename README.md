# webScan
project for read content from web page and find the most frequent word and word pair

## Built With

* 	[Maven](https://maven.apache.org/) - Dependency Management
* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[H2](www.h2database.com/) - Open-Source Relational Database Management System
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system
* 	[Lombok](https://projectlombok.org/) - Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.

## Running the application locally

* 	Download the zip or clone the Git repository.
* 	Unzip the zip file (if you downloaded one)
* 	Open Command Prompt and Change directory (cd) to folder containing pom.xml
*   mvn clean install
*   cd target
*   java -Xms256m -Xmx512m -jar webscan-0.0.1-SNAPSHOT.jar &
*   Open web browser
*   Enter following url :- http://localhost:8081/ecorp/
*   Click on submit after enter reqired information like url of the page you need to check frequent words and word pair
*   First time save the data in memory h2 database
*   If it required to recompute the page give recompute = TRUE
