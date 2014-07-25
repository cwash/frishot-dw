package com.example.helloworld.api.event

import groovy.transform.ToString
import org.joda.time.DateTime

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@ToString(includeFields = true, includePackage = false, excludes = "metaClass")
public class PlayerAddedEvent implements Event {
    String gameId, handle
    DateTime timestamp

    public PlayerAddedEvent(String gameId, String handle) {
        this.gameId = gameId
        this.handle = handle
        this.timestamp = new DateTime()
    }
}
