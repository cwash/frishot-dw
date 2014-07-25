package com.example.helloworld.subscribers.command
import com.example.helloworld.api.CreateGameCommand
import com.example.helloworld.api.event.GameCreatedEvent
import com.example.helloworld.core.Game
import com.example.helloworld.db.GameDAO
import com.example.helloworld.engine.CommandSubscriber
import com.google.common.eventbus.Subscribe

import javax.inject.Inject
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class CreateGameCommandSubscriber extends CommandSubscriber {

    final GameDAO gameDAO

    @Inject
    CreateGameCommandSubscriber(GameDAO gameDAO) {
        this.gameDAO = gameDAO
    }

    @Subscribe
    void onCommand(CreateGameCommand command) {

        Game game = new Game();
        game.gameId = command.gameId;
        game.initialPot = command.initialPot;
        game.createdBy = command.createdBy;
        game.gameOver = false;

        gameDAO.create(game)

        GameCreatedEvent gameCreatedEvent = new GameCreatedEvent(game.gamePk, game.gameId, command.initialPot, command.createdBy)
        publishEvent(gameCreatedEvent)
    }

}
