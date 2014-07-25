package com.example.helloworld.resources

import com.example.helloworld.api.PlayerData
import com.example.helloworld.core.Player
import com.example.helloworld.db.PlayerDAO
import com.google.common.base.Optional
import com.sun.jersey.api.NotFoundException
import io.dropwizard.hibernate.UnitOfWork
import io.dropwizard.jersey.params.UUIDParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@Path("/players/{playerId}")
@Produces(MediaType.APPLICATION_JSON)
class PlayerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerResource.class);
    final PlayerDAO playerDAO

    @Inject
    public PlayerResource(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO
    }

    @GET
    @UnitOfWork
    public PlayerData getPlayer(@PathParam("playerId") UUIDParam playerId) {
        final Optional<Player> playerOptional = playerDAO.findById(playerId.get().toString())
        if (!playerOptional.isPresent()) {
            throw new NotFoundException("No such player.")
        }
        Player player = playerOptional.get()
        return new PlayerData(
                    player.playerId,
                    player.handle,
                    player.totalWinnings,
                    player.maxPotWon,
                    player.totalHits,
                    player.totalShots,
                    player.maxDistanceHit,
                    player.currentHitStreak,
                    player.maxHitStreak,
                    player.positionHitTotals
        )
    }
}
