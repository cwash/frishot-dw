package com.example.helloworld.subscribers.event

import com.example.helloworld.api.GameData
import com.example.helloworld.api.event.PlayerAddedEvent
import com.example.helloworld.db.GameDAO
import com.example.helloworld.engine.EventSubscriber
import com.google.common.eventbus.Subscribe
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Inject

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class PlayerAddedEventSubscriber extends EventSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerAddedEventSubscriber.class)

    final GameDAO gameDAO

    @Inject
    PlayerAddedEventSubscriber(GameDAO gameDAO) {
        this.gameDAO = gameDAO
    }

    @Subscribe
    void onEvent(PlayerAddedEvent event) {
        LOGGER.info("Received Event: {}", event)

        GameData gameData = gameDAO.addPlayerToGame(event.gameId, event.handle)

        LOGGER.info("Added player to game: {}", gameData)
    }

}
