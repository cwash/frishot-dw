package com.example.helloworld.subscribers.event

import com.example.helloworld.api.DartData
import com.example.helloworld.api.GameData
import com.example.helloworld.api.PlayerData
import com.example.helloworld.api.event.PlayerHitEvent
import com.example.helloworld.db.DartDAO
import com.example.helloworld.db.GameDAO
import com.example.helloworld.db.PlayerDAO
import com.example.helloworld.engine.EventSubscriber
import com.google.common.base.Optional
import com.google.common.eventbus.Subscribe
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Inject

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class PlayerHitEventSubscriber extends EventSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerHitEvent.class)

    final GameDAO gameDAO
    final PlayerDAO playerDAO
    final DartDAO dartDAO

    @Inject
    PlayerHitEventSubscriber(GameDAO gameDAO, PlayerDAO playerDAO, DartDAO dartDAO) {
        this.gameDAO = gameDAO
        this.playerDAO = playerDAO
        this.dartDAO = dartDAO
    }

    @Subscribe
    void onEvent(PlayerHitEvent event) {
        LOGGER.info("Received Event: {}", event)

        /* find game */
        Optional<GameData> gameDataOptional = gameDAO.findById(event.gameId)
        GameData gameData = gameDataOptional.get()

        /* find player */
        Optional<PlayerData> playerDataOptional = playerDAO.findByHandle(event.handle)
        PlayerData playerData = playerDataOptional.get()

        /* find dart */
        Optional<DartData> dartDataOptional = dartDAO.findByHandle(event.dart)
        DartData dartData = dartDataOptional.get()

        /* add handle to game.playersHit */
        gameDAO.addPlayerHit(event.gameId, event.handle)

        /* increment player.totalShots and player.totalHits */
        playerDAO.incrementTotalShots(playerData.playerId)
        playerDAO.incrementTotalHits(playerData.playerId)

        /* check for player.maxDistance and update */
        playerDAO.checkForMaxDistanceAndUpdate(playerData.playerId, event.distance)

        /* increment dart.totalShots and dart.totalHits */
        dartDAO.incrementTotalShots(dartData.dartId)
        dartDAO.incrementTotalHits(dartData.dartId)


        /* update player.positionalHitTotals */
        // histogram ; increment at index
        playerDAO.updatePositionalHitTotals(playerData.playerId, event.position)

        /* update dart.positionalHitTotals */
        // histogram ; increment at index
        dartDAO.updatePositionalHitTotals(dartData.dartId, event.position)

    }

}
