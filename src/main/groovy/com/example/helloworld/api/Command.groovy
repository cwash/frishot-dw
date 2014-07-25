package com.example.helloworld.api
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.dropwizard.jackson.Discoverable

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes([
    @Type(name="addPlayerCommand", value=AddPlayerCommand.class),
    @Type(name="createGameCommand", value=CreateGameCommand.class),
    @Type(name="recordShotCommand", value=RecordShotCommand.class),
    @Type(name="startGameCommand", value=StartGameCommand.class)
])
public interface Command extends Discoverable {
}