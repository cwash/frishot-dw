package com.example.helloworld.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.collect.ImmutableMap

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class DartData {

    @JsonProperty
    public String dartId

    @JsonProperty
    public String handle

    @JsonProperty
    public Integer totalHits

    @JsonProperty
    public Integer totalShots

    @JsonProperty
    public ImmutableMap<Integer,Integer> positionHitTotals

    DartData(String dartId, String handle, Integer totalHits, Integer totalShots, ImmutableMap<Integer,Integer> positionHitTotals) {
        this.dartId = dartId
        this.handle = handle
        this.totalHits = totalHits
        this.totalShots = totalShots
        this.positionHitTotals = positionHitTotals
    }
}
