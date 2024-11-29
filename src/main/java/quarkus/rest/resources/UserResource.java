package quarkus.rest.resources;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import quarkus.rest.dto.ResponseError;
import quarkus.rest.dto.UserDto;
import quarkus.rest.entities.User;
import quarkus.rest.repository.UserRepository;

import java.util.Set;


@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserRepository repository;
    private final Validator validator;

    @Inject
    public UserResource(UserRepository repository, Validator validator){
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(UserDto user){
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);
        if(!violations.isEmpty()){
            return ResponseError.createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
        User userModel = new User();
        userModel.setAge(user.getAge());
        userModel.setName(user.getName());
        repository.persist(userModel);
        return Response.status(Response.Status.CREATED.getStatusCode())
                .entity(userModel)
                .build();
    }

    @GET
    public Response listAllUsers(){
        PanacheQuery<User> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
        User user =  repository.findById(id);
        if(user != null) {
            repository.delete(user);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, UserDto userDto){
        User user =  repository.findById(id);
        if(user != null) {
            user.setName(userDto.getName());
            user.setAge(userDto.getAge());
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
