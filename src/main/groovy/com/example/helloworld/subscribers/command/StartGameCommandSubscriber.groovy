package com.example.helloworld.subscribers.command

import com.example.helloworld.api.GameData
import com.example.helloworld.api.StartGameCommand
import com.example.helloworld.api.event.GameStartedEvent
import com.example.helloworld.db.GameDAO
import com.example.helloworld.engine.CommandSubscriber
import com.google.common.eventbus.Subscribe

import javax.inject.Inject

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class StartGameCommandSubscriber extends CommandSubscriber {

    final GameDAO gameDAO

    @Inject
    StartGameCommandSubscriber(GameDAO gameDAO) {
        this.gameDAO = gameDAO
    }

    @Subscribe
    void onCommand(StartGameCommand command) {

        GameData gameData = gameDAO.startGame(command.gameId)

        LOGGER.info("Total Pot: {}", gameData.totalPot)
        LOGGER.info("Player Order: {}", gameData.playerOrder)

        GameStartedEvent gameStartedEvent = new GameStartedEvent(command.gameId)
        publishEvent(gameStartedEvent)

    }

}
