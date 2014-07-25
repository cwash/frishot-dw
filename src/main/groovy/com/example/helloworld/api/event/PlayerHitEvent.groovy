package com.example.helloworld.api.event

import groovy.transform.ToString
import org.joda.time.DateTime

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@ToString(includeFields = true, includePackage = false, excludes = "metaClass")
public class PlayerHitEvent implements Event {
    String gameId, handle, dart
    Integer distance, position
    DateTime timestamp

    PlayerHitEvent(String gameId, String handle, String dart, Integer distance, Integer position) {
        this.gameId = gameId
        this.handle = handle
        this.dart = dart
        this.distance = distance
        this.position = position
        this.timestamp = new DateTime()
    }

}
