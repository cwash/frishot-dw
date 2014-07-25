package com.example.helloworld.subscribers.command
import com.example.helloworld.api.AddPlayerCommand
import com.example.helloworld.api.PlayerData
import com.example.helloworld.api.event.PlayerAddedEvent
import com.example.helloworld.core.Player
import com.example.helloworld.db.PlayerDAO
import com.example.helloworld.engine.CommandSubscriber
import com.google.common.base.Optional
import com.google.common.eventbus.Subscribe

import javax.inject.Inject
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class AddPlayerCommandSubscriber extends CommandSubscriber {


    final PlayerDAO playerDAO

    @Inject
    AddPlayerCommandSubscriber(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO
    }

    @Subscribe
    void onCommand(AddPlayerCommand command) {

        /* find player */
        Optional<PlayerData> playerDataOptional = playerDAO.findByHandle(command.handle)

        PlayerData playerData = null

        /* if player exists then use, otherwise create one */
        if (playerDataOptional.isPresent()) {
            playerData = playerDataOptional.get()
        }
        else {
            Player addPlayer = new Player()
            addPlayer.handle = command.handle
            addPlayer.playerId = UUID.randomUUID()
            addPlayer.currentHitStreak = 0
            addPlayer.maxDistanceHit = 0
            addPlayer.maxHitStreak = 0
            addPlayer.maxPotWon = 0
            addPlayer.totalHits = 0
            addPlayer.totalShots = 0
            addPlayer.totalWinnings = 0
            addPlayer.positionHitTotals = new HashMap<>()
            playerData = playerDAO.create(addPlayer)
        }

        PlayerAddedEvent event = new PlayerAddedEvent(command.gameId, playerData.handle)
        publishEvent(event)
    }

}
