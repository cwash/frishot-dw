package com.example.helloworld.resources

import com.example.helloworld.api.DartData
import com.example.helloworld.core.Dart
import com.example.helloworld.db.DartDAO
import com.google.common.base.Optional
import com.google.inject.Inject
import com.sun.jersey.api.NotFoundException
import io.dropwizard.hibernate.UnitOfWork
import io.dropwizard.jersey.params.UUIDParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory

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
@Path("/darts/{dartId}")
@Produces(MediaType.APPLICATION_JSON)
class DartResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DartResource.class);
    final DartDAO dartDAO

    @Inject
    public DartResource(DartDAO dartDAO) {
        this.dartDAO = dartDAO
    }

    @GET
    @UnitOfWork
    public DartData getDart(@PathParam("dartId") UUIDParam dartId) {
        final Optional<Dart> dartOptional = dartDAO.findById(dartId.get().toString())
        if (!dartOptional.isPresent()) {
            throw new NotFoundException("No such dart.")
        }
        Dart dart = dartOptional.get()
        return new DartData(
                    dart.dartId,
                    dart.handle,
                    dart.totalHits,
                    dart.totalShots,
                    dart.positionHitTotals
        )
    }
}
