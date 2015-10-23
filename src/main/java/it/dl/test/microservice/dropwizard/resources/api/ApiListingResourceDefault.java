package it.dl.test.microservice.dropwizard.resources.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;

import javax.servlet.ServletConfig;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

//import com.wordnik.swagger.jaxrs.listing.ApiListing;

@Path("/api-docs")
@Api("/api-docs")
@Produces("application/json")
public class ApiListingResourceDefault extends ApiListingResourceJSON {

    @Override
    @GET
    @Path("/{route:.*}")
    public Response apiListing(@PathParam("route") String route,
                               @Context Application app, @Context ServletConfig sc,
                               @Context HttpHeaders headers, @Context UriInfo uriInfo) {
        Response response = super.apiListing(route, app, sc, headers, uriInfo);
        return response;
    }
}
