package com.example.helloworld.api.event

import groovy.transform.ToString
import org.joda.time.DateTime

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@ToString(includeFields = true, includePackage = false, excludes = "metaClass")
public class PlayerMissedEvent implements Event {
    String gameId, handle, dart, distance
    DateTime timestamp

    PlayerMissedEvent(String gameId, String handle, String dart, String distance) {
        this.gameId = gameId
        this.handle = handle
        this.dart = dart
        this.distance = distance
        this.timestamp = new DateTime()
    }

}
