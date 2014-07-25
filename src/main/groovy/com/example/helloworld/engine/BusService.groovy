package com.example.helloworld.engine
import com.example.helloworld.api.Command
import com.google.common.eventbus.EventBus
import com.google.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Named
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class BusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusService.class)

    private EventBus commandBus
    private Set<CommandSubscriber> commandSubscribers
    private EventBus eventBus
    private Set<EventSubscriber> eventSubscribers

    @Inject
    public BusService(@Named("commandBus") EventBus commandBus,
                      Set<CommandSubscriber> commandSubscribers,
                      @Named("eventBus") EventBus eventBus,
                      Set<EventSubscriber> eventSubscribers
    ) {
        this.commandBus = commandBus
        this.eventBus = eventBus
        this.commandSubscribers = commandSubscribers
        for (CommandSubscriber subscriber : commandSubscribers) {
            commandBus.register(subscriber)
        }
        this.eventSubscribers = eventSubscribers
        for (EventSubscriber subscriber : eventSubscribers) {
            eventBus.register(subscriber)
        }
    }

    public void executeCommand(Command command) {
        LOGGER.info("Executing Command: {}", command)
        commandBus.post(command)
    }

}
