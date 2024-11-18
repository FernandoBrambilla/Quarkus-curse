package quarkus.rest.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import quarkus.rest.dto.UserDto;
import quarkus.rest.model.UserModel;


@Path("/users")
public class users {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createUser(UserDto user){
        UserModel userModel = new UserModel();
        userModel.setAge(user.getAge());
        userModel.setName(user.getName());
        userModel.persist();
        return Response.ok(userModel).build();
    }

    @GET
    public Response listAllUsers(){
        return Response.ok().build();
    }

}
