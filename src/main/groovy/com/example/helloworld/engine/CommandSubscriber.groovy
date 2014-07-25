package com.example.helloworld.engine

import com.example.helloworld.api.event.Event
import com.google.common.eventbus.EventBus
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Inject
import javax.inject.Named

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public abstract class CommandSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandSubscriber.class)

    @Inject @Named("eventBus")
    private EventBus eventBus

    public void publishEvent(Event event) {
        LOGGER.info("Publishing Event: {}", event)
        eventBus.post(event)
    }
}
