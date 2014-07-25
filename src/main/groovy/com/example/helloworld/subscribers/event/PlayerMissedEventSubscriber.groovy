package com.example.helloworld.subscribers.event

import com.example.helloworld.api.DartData
import com.example.helloworld.api.GameData
import com.example.helloworld.api.PlayerData
import com.example.helloworld.api.event.PlayerMissedEvent
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
public class PlayerMissedEventSubscriber extends EventSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMissedEvent.class)

    final GameDAO gameDAO
    final PlayerDAO playerDAO
    final DartDAO dartDAO

    @Inject
    PlayerMissedEventSubscriber(GameDAO gameDAO, PlayerDAO playerDAO, DartDAO dartDAO) {
        this.gameDAO = gameDAO
        this.playerDAO = playerDAO
        this.dartDAO = dartDAO
    }

    @Subscribe
    void onEvent(PlayerMissedEvent event) {
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

        /* increment player.totalShots */
        playerDAO.incrementTotalShots(playerData.playerId)

        /* increment dart.totalShots */
        dartDAO.incrementTotalShots(dartData.dartId)

    }

}
