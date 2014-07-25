package com.example.helloworld.subscribers.event


import com.example.helloworld.engine.EventSubscriber
import com.example.helloworld.api.event.GameStartedEvent
import com.google.common.eventbus.Subscribe

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class GameStartedEventSubscriber extends EventSubscriber {

    @Subscribe
    void onEvent(GameStartedEvent event) {
        LOGGER.info("Received Event: {}", event)
    }

}
