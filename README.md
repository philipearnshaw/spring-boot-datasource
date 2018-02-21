# Overview
Creating a datasource based on external configuration and profiles.

# Findbugs
Findbugs runs as part of the build and will fail on certain conditions.

Findbugs can be run independently with the following commands:
 - `mvn findbugs:findbugs` - see report in `target`.
 - `mvn findbugs:gui` - displays a GUI showing any problems found.

Findbugs analyses the compiled .class files so ensure a build has taken place before running.

# Jacoco
Jacoco is a code coverage reports generator for Java projects. JaCoCo runs as a Java agent, it is responsible for instrumenting the bytecode while running the tests. JaCoCo drills into each instruction and shows which lines are exercised during each test. 
 - `mvn jacoco:report` - see report index in `target/site/jacoco/index.html`.
 
 For more information on the design see `http://www.eclemma.org/jacoco/trunk/doc/implementation.html`.
 
# Swagger
  - GUI documentation can be viewed on `http://localhost:8080/swagger-ui.html`.
  - API documentation can be viewed on `http://localhost:8080/v2/api-docs`.