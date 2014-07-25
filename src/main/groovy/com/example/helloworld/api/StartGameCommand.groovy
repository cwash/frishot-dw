package com.example.helloworld.api
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import groovy.transform.ToString

import javax.validation.constraints.NotNull
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@JsonTypeName("StartGameCommand")
@ToString(includeFields = true, includePackage = false, excludes = "metaClass")
public class StartGameCommand implements Command {

    @JsonProperty
    @NotNull
    public String gameId

}
