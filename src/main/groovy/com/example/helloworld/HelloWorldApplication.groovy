package com.example.helloworld

import com.example.helloworld.api.AddPlayerCommand
import com.example.helloworld.api.CreateGameCommand
import com.example.helloworld.api.RecordShotCommand
import com.example.helloworld.api.StartGameCommand
import com.example.helloworld.cli.RenderCommand
import com.example.helloworld.core.Dart
import com.example.helloworld.core.Game
import com.example.helloworld.core.Person
import com.example.helloworld.core.Player
import com.example.helloworld.guice.AutoConfigApplication
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableList
import com.google.inject.Guice
import com.google.inject.Injector
import io.dropwizard.assets.AssetsBundle
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.hibernate.HibernateBundle
import io.dropwizard.hibernate.SessionFactoryFactory
import io.dropwizard.migrations.MigrationsBundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

public class HelloWorldApplication extends AutoConfigApplication<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args)
    }

    public HelloWorldApplication() {
        super("hello-world", "com.example.helloworld")
    }

    final HibernateBundle<HelloWorldConfiguration> hibernateBundle =
        new HibernateBundle<HelloWorldConfiguration>(ImmutableList.copyOf([Person, Player, Game, Dart]), new SessionFactoryFactory()) {
            @Override
            public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
                return configuration.getDataSourceFactory()
            }
        }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.addCommand(new RenderCommand())
        bootstrap.addBundle(new AssetsBundle())
        bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
                return configuration.getDataSourceFactory()
            }
        })
        bootstrap.addBundle(hibernateBundle)
    }

    @Override
    protected Injector createInjector(HelloWorldConfiguration configuration) {
        return Guice.createInjector(new HelloWorldServiceModule(configuration, hibernateBundle.getSessionFactory(), "com.example.helloworld"));
    }

    @Override
    protected void runWithInjector(HelloWorldConfiguration configuration, Environment environment,
                                   Injector injector) throws Exception {
        super.runWithInjector(configuration, environment, injector)

        // TODO: scan for these instead of hardcoding
        Class<?>[] subtypes = [CreateGameCommand.class, StartGameCommand.class, RecordShotCommand.class, AddPlayerCommand.class].toArray()
        ObjectMapper objectMapper = environment.getObjectMapper()
        objectMapper.registerSubtypes(subtypes)
    }

}
