package com.example.helloworld.subscribers.event

import com.example.helloworld.engine.EventSubscriber
import com.google.common.eventbus.DeadEvent
import com.google.common.eventbus.Subscribe
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class DeadEventSubscriber extends EventSubscriber {


    private static final Logger LOGGER = LoggerFactory.getLogger(DeadEventSubscriber.class)

    @Subscribe
    public void onEvent(DeadEvent deadEvent)
    {
        LOGGER.warn("*** {} produced a DEAD EVENT: {}", deadEvent.source, deadEvent.getEvent())
    }

}
