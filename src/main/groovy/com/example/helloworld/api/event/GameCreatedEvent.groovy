package com.example.helloworld.api.event

import groovy.transform.ToString
import org.joda.time.DateTime
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@ToString(includeFields = true, includePackage = false, excludes = "metaClass")
public class GameCreatedEvent implements Event {
    Long gamePk
    String gameId, createdBy
    Integer initialPot
    DateTime timestamp

    GameCreatedEvent(Long gamePk, String gameId, Integer initialPot, String createdBy) {
        this.gamePk = gamePk
        this.gameId = gameId
        this.initialPot = initialPot
        this.createdBy = createdBy
        this.timestamp = new DateTime()
    }
}
