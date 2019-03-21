package br.com.aqueteron.oauth2.authorizer.api;

import br.com.aqueteron.oauth2.authorizer.model.UserApiEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/user")
public interface UserApiResource {

    @OPTIONS
    Response options();

    @GET
    @Produces(value = {"application/json"})
    Response getUserSet(@Context final SecurityContext securityContext);

    @POST
    @Consumes(value = {"application/json"})
    @Produces(value = {"application/json"})
    Response postUser(final UserApiEntity newUserApiEntity, @Context final SecurityContext securityContext);

    @GET
    @Path("/{login}")
    @Produces(value = {"application/json"})
    Response getUser(@PathParam("login") final String login, @Context final SecurityContext securityContext);
}
