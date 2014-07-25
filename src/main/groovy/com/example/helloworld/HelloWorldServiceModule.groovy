package com.example.helloworld
import com.example.helloworld.engine.CommandSubscriber
import com.example.helloworld.engine.EventSubscriber
import com.google.common.eventbus.EventBus
import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import com.google.inject.name.Names
import io.dropwizard.Configuration
import org.hibernate.SessionFactory
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
public class HelloWorldServiceModule extends AbstractModule {

    HelloWorldConfiguration configuration
    SessionFactory sessionFactory
    Reflections reflections

    public HelloWorldServiceModule(HelloWorldConfiguration configuration,
                                   SessionFactory sessionFactory,
                                   java.lang.String... basePackages) {
        this.configuration = configuration
        this.sessionFactory = sessionFactory

        ConfigurationBuilder cfgBldr = new ConfigurationBuilder()
        FilterBuilder filterBuilder = new FilterBuilder()
        for (String basePkg : basePackages) {
            cfgBldr.addUrls(ClasspathHelper.forPackage(basePkg))
            filterBuilder.include(FilterBuilder.prefix(basePkg))
        }

        cfgBldr.filterInputsBy(filterBuilder).setScanners(
                new SubTypesScanner(), new TypeAnnotationsScanner())
        this.reflections = new Reflections(cfgBldr)
    }

    @Override
    protected void configure() {
        bind(Configuration.class).toInstance(configuration)
        bind(SessionFactory.class).toInstance(sessionFactory)
        bind(EventBus.class).annotatedWith(Names.named("commandBus")).toInstance(new EventBus())
        bind(EventBus.class).annotatedWith(Names.named("eventBus")).toInstance(new EventBus())

        Multibinder<CommandSubscriber> commandSubscriberMultibinder = Multibinder.newSetBinder(binder(), CommandSubscriber.class)
        Set<Class<?>> commandSubscriberClasses = reflections.getSubTypesOf(CommandSubscriber.class)
        for (Class<?> commandSubscriber : commandSubscriberClasses) {
            commandSubscriberMultibinder.addBinding().to(commandSubscriber)
        }

        Multibinder<EventSubscriber> eventSubscriberMultibinder = Multibinder.newSetBinder(binder(), EventSubscriber.class)
        Set<Class<?>> eventSubscriberClasses = reflections.getSubTypesOf(EventSubscriber.class)
        for (Class<?> eventSubscriber : eventSubscriberClasses) {
            eventSubscriberMultibinder.addBinding().to(eventSubscriber)
        }
    }

}

