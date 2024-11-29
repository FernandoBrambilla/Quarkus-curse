package quarkus.rest.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import quarkus.rest.dto.FollowerDto;
import quarkus.rest.entities.Follower;
import quarkus.rest.entities.User;
import quarkus.rest.repository.FollowerRepository;
import quarkus.rest.repository.UserRepository;

import java.lang.annotation.Repeatable;

@Path("/users/{userID}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerRessource {

    private final FollowerRepository repository;
    private final UserRepository userRepository;


    @Inject
    public FollowerRessource(FollowerRepository repository, UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @PUT
    @Transactional
    public Response followUser(@PathParam("userID") Long userId, FollowerDto follower){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        var followers = userRepository.findById(follower.getFollowerId());
        boolean follows = repository.followers(followers, user);
        if (!follows){
            Follower entity = new Follower();
            entity.setUser(user);
            entity.setFollower(followers);
            repository.persist(entity);
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
