package com.example.helloworld.resources
import com.example.helloworld.api.GameData
import com.example.helloworld.db.GameDAO
import io.dropwizard.hibernate.UnitOfWork
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
public class GamesResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GamesResource.class);

    final GameDAO gameDAO

    @Inject
    public GamesResource(GameDAO gameDAO) {
        this.gameDAO = gameDAO
    }

    @GET
    @UnitOfWork
    public List<GameData> listGames() {
        // TODO: support limit and offset for paging results
        return gameDAO.findAll()
    }
}
