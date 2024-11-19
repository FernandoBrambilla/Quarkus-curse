package quarkus.rest.resources;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import quarkus.rest.dto.UserDto;
import quarkus.rest.model.UserModel;
import quarkus.rest.repository.UserRepository;


@Path("/users")
public class UserResource {
    private final UserRepository repository;

    @Inject
    public UserResource(UserRepository repository){
        this.repository = repository;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createUser(UserDto user){
        UserModel userModel = new UserModel();
        userModel.setAge(user.getAge());
        userModel.setName(user.getName());
        repository.persist(userModel);
        return Response.ok(userModel).build();
    }

    @GET
    public Response listAllUsers(){
        PanacheQuery<UserModel> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
        UserModel user =  repository.findById(id);
        if(user != null) {
            repository.delete(user);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, UserDto userDto){
        UserModel user =  repository.findById(id);
        if(user != null) {
            user.setName(userDto.getName());
            user.setAge(userDto.getAge());
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
