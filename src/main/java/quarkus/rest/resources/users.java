package quarkus.rest.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import quarkus.rest.dto.UserDto;

@Path("/users")
public class users {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserDto user){
        return Response.ok().build();
    }

    @GET
    public Response listAllUsers(){
        return Response.ok().build();
    }

}
