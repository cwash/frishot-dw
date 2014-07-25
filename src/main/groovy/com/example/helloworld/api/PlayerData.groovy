package com.example.helloworld.api
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.collect.ImmutableMap
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class PlayerData {

    @JsonProperty
    public String playerId

    @JsonProperty
    public String handle

    @JsonProperty
    public Integer totalWinnings

    @JsonProperty
    public Integer maxPotWon

    @JsonProperty
    public Integer totalHits

    @JsonProperty
    public Integer totalShots

    @JsonProperty
    public Integer maxDistanceHit

    @JsonProperty
    public Integer currentHitStreak

    @JsonProperty
    public Integer maxHitStreak

    @JsonProperty
    public ImmutableMap<Integer, Integer> positionHitTotals

    PlayerData(String playerId, String handle, Integer totalWinnings, Integer maxPotWon, Integer totalHits, Integer totalShots, Integer maxDistanceHit, Integer currentHitStreak, Integer maxHitStreak, ImmutableMap<Integer,Integer> positionHitTotals) {
        this.playerId = playerId
        this.handle = handle
        this.totalWinnings = totalWinnings
        this.maxPotWon = maxPotWon
        this.totalHits = totalHits
        this.totalShots = totalShots
        this.maxDistanceHit = maxDistanceHit
        this.currentHitStreak = currentHitStreak
        this.maxHitStreak = maxHitStreak
        this.positionHitTotals = positionHitTotals
    }
}
