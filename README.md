# Dropwizard + Gradle Friday Shots

REST + CQRS design, H2 + Dropwizard - Jetty based microservice architecture. Liquibase is used to manage migrations and schema changes.
 
## Checkout
 
 `$ git clone git@github.com:cwash/frishot-dw.git`
 
 `$ cd frishot-dw`
 
## Database setup
 
 `$ ./gradlew fatJar`
 
 `$ java -jar build/libs/dropwizard-gradle-groovy-fat.jar db migrate src/dist/config/example.yml`
            
## Run
 
 `$ ./gradlew run`
 
## Exit

 `Ctrl-C`

## Design

The rough design doc I through together when starting on the project can be found under `design/original-design.pdf`

The overall architectural design for the project follows a CQRS style.  All read/query operations take the form of GET 
operations on resources, and all writes are handled by POST operations with Command payloads.  If needed, the read and 
write components could be completely separated.  The current application design facilitates this by introducing two different
event buses (currently in process) - one dispatching commands, and another for dispatching events.  Users interact with 
the service through Commands, and Commands can produce domain events.  This decouples most of the logic needed to distribute
data updates between various parts of the application, and could also be shared with a denormalized, read-optimized view of the 
data that the query layer uses.  (Notice the query sections of the application are much simplier than the command based 
 sections).  This architecture also could be transitioned to one where storage on the command side is event-sourced without 
  too much impact to other application areas (as opposed to CRUD-style).

## Testing

Some sample command payloads were crafted to do rudimentary testing.  You can find these under `src/test/resources`

There is a quick screen-capture style demo of the application available on [Vimeo](https://vimeo.com/101686779) - use the 
password "fridayshots"

## Gotchas

The FatJar plugin for Gradle was intended to package the project, but it appears after upgrading to a new
version of Dropwizard, fatJar has some issues getting all of the dependencies resolved into the final 
runtime classpath.  There is a problem loading Jersey Resources currently.  Requires more investigation.

The Gradle distZip also has the same issue.  The best way to run the application for the time being is checking 
the source out and doing `gradlew run`