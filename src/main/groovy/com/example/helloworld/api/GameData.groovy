package com.example.helloworld.api
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.collect.ImmutableList
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class GameData {

    @JsonProperty
    public String gameId

    @JsonProperty
    public Integer initialPot

    @JsonProperty
    public Integer totalPot

    @JsonProperty
    public ImmutableList<String> playerGroup

    @JsonProperty
    public ImmutableList<String> playerOrder

    @JsonProperty
    public ImmutableList<String> playersHit

    @JsonProperty
    public Integer potSplit

    @JsonProperty
    public Boolean gameOver

    GameData(String gameId, Integer initialPot, Integer totalPot, ImmutableList<String> playerGroup, ImmutableList<String> playerOrder, ImmutableList<String> playersHit, Integer potSplit, Boolean gameOver) {
        this.gameId = gameId
        this.initialPot = initialPot
        this.totalPot = totalPot
        this.playerGroup = playerGroup
        this.playerOrder = playerOrder
        this.playersHit = playersHit
        this.potSplit = potSplit
        this.gameOver = gameOver
    }
}
