package quarkus.rest.resources;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.h2.command.ddl.CreateUser;
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
        PanacheQuery<UserModel> query = UserModel.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
        UserModel user =  UserModel.findById(id);
        if(user != null) {
            user.delete();
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, UserDto userDto){
        UserModel user =  UserModel.findById(id);
        if(user != null) {
            user.setName(userDto.getName());
            user.setAge(userDto.getAge());
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
