package com.example.helloworld.resources
import com.example.helloworld.api.PlayerData
import com.example.helloworld.db.PlayerDAO
import io.dropwizard.hibernate.UnitOfWork

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
@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
class PlayersResource {

    final PlayerDAO playerDAO

    @Inject
    public PlayersResource(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO
    }

    @GET
    @UnitOfWork
    public List<PlayerData> listPlayers() {
        return playerDAO.findAll()
    }

}
