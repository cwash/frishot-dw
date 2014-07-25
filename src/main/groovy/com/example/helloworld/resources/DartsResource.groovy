package com.example.helloworld.resources
import com.example.helloworld.api.DartData
import com.example.helloworld.db.DartDAO
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
@Path("/darts")
@Produces(MediaType.APPLICATION_JSON)
class DartsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DartsResource.class);

    final DartDAO dartDAO

    @Inject
    public DartsResource(DartDAO dartDAO) {
        this.dartDAO = dartDAO
    }

    @GET
    @UnitOfWork
    public List<DartData> listDarts() {
        return dartDAO.findAll()
    }
}
