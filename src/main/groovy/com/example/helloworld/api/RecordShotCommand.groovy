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
@JsonTypeName("RecordShotCommand")
@ToString(includeFields = true, includePackage = false, excludes = "metaClass")
public class RecordShotCommand implements Command {

    @JsonProperty
    @NotNull
    public String gameId

    @JsonProperty
    @NotNull
    public String handle

    @JsonProperty
    @NotNull
    public Integer distance

    @JsonProperty
    @NotNull
    public String dart

    @JsonProperty
    @NotNull
    public Boolean hit

}
