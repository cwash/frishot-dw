package com.example.helloworld.subscribers.command
import com.example.helloworld.api.DartData
import com.example.helloworld.api.GameData
import com.example.helloworld.api.RecordShotCommand
import com.example.helloworld.api.event.PlayerHitEvent
import com.example.helloworld.api.event.PlayerMissedEvent
import com.example.helloworld.core.Dart
import com.example.helloworld.db.DartDAO
import com.example.helloworld.db.GameDAO
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
public class RecordShotCommandSubscriber extends CommandSubscriber {

    final GameDAO gameDAO
    final PlayerDAO playerDAO
    final DartDAO dartDAO

    @Inject
    RecordShotCommandSubscriber(GameDAO gameDAO, PlayerDAO playerDAO, DartDAO dartDAO) {
        this.gameDAO = gameDAO
        this.playerDAO = playerDAO
        this.dartDAO = dartDAO
    }

    @Subscribe
    void onCommand(RecordShotCommand command) {

        /* find dart */
        Optional<DartData> dartDataOptional = dartDAO.findByHandle(command.dart)

        DartData dartData = null

        /* if dart exists then use, otherwise create one */
        if (dartDataOptional.isPresent()) {
            dartData = dartDataOptional.get()
        }
        else {
            Dart addDart = new Dart()
            addDart.handle = command.dart
            addDart.dartId = UUID.randomUUID()
            addDart.totalHits = 0
            addDart.totalShots = 0
            addDart.positionHitTotals = new HashMap<>()
            dartData = dartDAO.create(addDart)
        }

        if (command.hit)
        {

            /* find game */
            Optional<GameData> gameDataOptional = gameDAO.findById(command.gameId)

            GameData data = gameDataOptional.get()

            /* record position */
            // NOTE: this will not work if people can take multiple turns - instead of inferring need to put sequence
            // on command... could also use event sourcing to save all events and grab it as the index of latest event
            Integer position = data.playerOrder.indexOf(command.handle)

            PlayerHitEvent event = new PlayerHitEvent(command.gameId, command.handle, command.dart, command.distance, position)
            publishEvent(event)
        }
        else {
            PlayerMissedEvent event = new PlayerMissedEvent(command.gameId, command.handle, command.dart, command.distance)
            publishEvent(event)
        }



    }

}
