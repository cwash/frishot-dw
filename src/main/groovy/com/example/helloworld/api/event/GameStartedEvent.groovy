package com.example.helloworld.api.event

import groovy.transform.ToString
import org.joda.time.DateTime
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@ToString(includeFields = true, includePackage = false, excludes = "metaClass")
public class GameStartedEvent implements Event {
    String gameId
    DateTime timestamp

    GameStartedEvent(String gameId) {
        this.gameId = gameId
        this.timestamp = new DateTime()
    }
}
