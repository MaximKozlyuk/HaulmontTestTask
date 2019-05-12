Test Task
=========

Prerequisites
-------------

* [Java Development Kit (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3](https://maven.apache.org/download.cgi)

Build and Run
-------------
1. Set valid pathToCreationScript in DataBaseManager class according to your file system

2. Run in the command line:
	```
	mvn package
	mvn jetty:run
	```

3. Open `http://localhost:8080` in a web browser.
