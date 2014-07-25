package com.example.helloworld.api
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@JsonTypeName("AddPlayerCommand")
@ToString(includeFields = true, includePackage = false, excludes = "metaClass")
public class AddPlayerCommand implements Command {

    @JsonProperty
    @NotEmpty
    public String gameId

    @JsonProperty
    @NotEmpty
    public String handle

}
