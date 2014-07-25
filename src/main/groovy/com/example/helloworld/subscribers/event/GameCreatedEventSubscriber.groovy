package com.example.helloworld.subscribers.event
import com.example.helloworld.api.event.GameCreatedEvent
import com.example.helloworld.engine.EventSubscriber
import com.google.common.eventbus.Subscribe
import org.slf4j.Logger
import org.slf4j.LoggerFactory
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class GameCreatedEventSubscriber extends EventSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameCreatedEventSubscriber.class)

    @Subscribe
    void onEvent(GameCreatedEvent event) {

        LOGGER.info("Received Event: {}", event)

    }

}
