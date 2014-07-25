# Dropwizard + Gradle Friday Shots

REST + CQRS design, H2 + Dropwizard - Jetty based microservice architecture. Liquibase is used to manage migrations and schema changes.
 
## Checkout
 
 `$ git clone git@github.com:cwash/frishot-dw.git`
 
## Database setup
 
 `$ ./gradlew fatJar`
 `$ java -jar build/libs/dropwizard-gradle-groovy-fat.jar db migrate src/dist/config/example.yml`
            
## Run
 
 `$ ./gradlew run`

## Gotchas

The FatJar plugin for Gradle was intended to package the project, but it appears after upgrading to a new
version of Dropwizard, fatJar has some issues getting all of the dependencies resolved into the final 
runtime classpath.  There is a problem loading Jersey Resources currently.  Requires more investigation.

The Gradle distZip also has the same issue.  The best way to run the application for the time being is checking 
the source out and doing `gradlew run`