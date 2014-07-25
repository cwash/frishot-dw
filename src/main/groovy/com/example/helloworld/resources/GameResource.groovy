package com.example.helloworld.resources
import com.codahale.metrics.annotation.Timed
import com.example.helloworld.HelloWorldApplication
import com.example.helloworld.api.AddPlayerCommand
import com.example.helloworld.api.Command
import com.example.helloworld.api.CreateGameCommand
import com.example.helloworld.api.GameData
import com.example.helloworld.api.RecordShotCommand
import com.example.helloworld.api.StartGameCommand
import com.example.helloworld.db.GameDAO
import com.example.helloworld.engine.BusService
import com.google.common.base.Optional
import com.sun.jersey.api.ConflictException
import com.sun.jersey.api.NotFoundException
import io.dropwizard.hibernate.UnitOfWork
import io.dropwizard.jersey.params.UUIDParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Inject
import javax.validation.Valid
import javax.validation.ValidationException
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@Path("/games/{gameId}")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameResource.class)

    final GameDAO gameDAO

    final BusService busService

    final HelloWorldApplication helloWorldService


    @Inject
    public GameResource(GameDAO gameDAO,
                        BusService busService,
                        HelloWorldApplication helloWorldService) {
        this.gameDAO = gameDAO
        this.busService = busService
        this.helloWorldService = helloWorldService
    }

    @GET
    @Timed
    @UnitOfWork(readOnly = true)
    public GameData getGame(@PathParam("gameId") UUIDParam gameId) {
        final Optional<GameData> gameOptional = gameDAO.findById(gameId.get().toString())
        if (!gameOptional.isPresent()) {
            throw new NotFoundException("No such game.")
        }
        return gameOptional.get()
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork(transactional = true)
    public Response processCommand(@Valid Command command, @Context UriInfo uriInfo) {
        LOGGER.info("Received Command: {}", command)

        /* validate data in the command against the current DB values */
        /* TODO: refactor this into a polymorphic method. get rid of the switch and have validation context with DAO */
        switch (command.class) {
            case CreateGameCommand.class:
                validateCreateGameCommand(command, uriInfo);
                break;
            case AddPlayerCommand.class:
                validateAddPlayerCommand(command, uriInfo);
                break;
            case StartGameCommand.class:
                validateStartGameCommand(command, uriInfo);
                break;
            case RecordShotCommand.class:
                validateRecordShotCommand(command, uriInfo);
                break;
            /* more validation */
        }

        busService.executeCommand(command)
        return Response.status(202).entity("" + command + " for gameId[" + command.gameId + "] accepted").type("text/plain").build()
    }



    private String printGameUri(Command command, UriInfo uriInfo) {
        uriInfo.getBaseUriBuilder().path("/games/{gameId}").build(command.gameId).toString()
    }

    private void validateCreateGameCommand(CreateGameCommand createGameCommand, UriInfo uriInfo) {
        final Optional<GameData> gameOptional = gameDAO.findById(createGameCommand.gameId)
        if (gameOptional.isPresent()) {
            throw new ConflictException(printGameUri(createGameCommand, uriInfo));
        }
    }

    private void validateAddPlayerCommand(AddPlayerCommand addPlayerCommand, UriInfo uriInfo) {
        final Optional<GameData> gameOptional = gameDAO.findById(addPlayerCommand.gameId)
        if (!gameOptional.isPresent()) {
            throw new ValidationException("Unable to add player to game: " + printGameUri(addPlayerCommand, uriInfo) + " not found")
        }
        GameData gameData = gameOptional.get()
        if (!(gameData.playerOrder?.empty)) {
            throw new ValidationException("Unable to add player to game: " + printGameUri(addPlayerCommand, uriInfo) + " game has already started")
        }
        if (gameData.gameOver) {
            throw new ValidationException("Unable to add player to game: " + printGameUri(addPlayerCommand, uriInfo) + " game is already over")
        }
    }

    private void validateStartGameCommand(StartGameCommand startGameCommand, UriInfo uriInfo) {
        final Optional<GameData> gameOptional = gameDAO.findById(startGameCommand.gameId)
        if (!gameOptional.isPresent()) {
            throw new ValidationException("Unable to start game: " + printGameUri(startGameCommand, uriInfo) + " not found")
        }
        GameData gameData = gameOptional.get()
        if (gameData.playerGroup?.empty) {
            throw new ValidationException("Unable to start game: " + printGameUri(startGameCommand, uriInfo) + " must have added players to the game already")
        }
        if (!(gameData.playerOrder?.empty)) {
            throw new ValidationException("Unable to start game: " + printGameUri(startGameCommand, uriInfo) + " game has already started")
        }
        if (gameData.gameOver) {
            throw new ValidationException("Unable to start game: " + printGameUri(startGameCommand, uriInfo) + " game is already over")
        }
    }

    private void validateRecordShotCommand(RecordShotCommand recordShotCommand, UriInfo uriInfo) {
        final Optional<GameData> gameOptional = gameDAO.findById(recordShotCommand.gameId)
        if (!gameOptional.isPresent()) {
            throw new ValidationException("Unable to record shot: " + printGameUri(recordShotCommand, uriInfo) + " not found")
        }
        GameData gameData = gameOptional.get()
        if (gameData.playerOrder?.empty) {
            throw new ValidationException("Unable to record shot: " + printGameUri(recordShotCommand, uriInfo) + " game has not started yet")
        }
        if (gameData.gameOver) {
            throw new ValidationException("Unable to record shot: " + printGameUri(recordShotCommand, uriInfo) + " game is already over")
        }
        if (!gameData.playerGroup.contains(recordShotCommand.handle)) {
            throw new ValidationException("Unable to record shot: " + printGameUri(recordShotCommand, uriInfo) + " does not contain a player with handle '" + recordShotCommand.handle + "'")
        }
    }

}