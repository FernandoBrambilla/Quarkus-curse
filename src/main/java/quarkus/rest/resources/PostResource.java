package quarkus.rest.resources;


import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import quarkus.rest.dto.PostDto;
import quarkus.rest.entities.Post;
import quarkus.rest.entities.User;
import quarkus.rest.repository.PostRepository;
import quarkus.rest.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Path("/users/{userID}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {


    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userID") Long userId, PostDto postRequest){
        User user = userRepository.findById(userId);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Post post = new Post();
        post.setText(postRequest.getText());
        post.setUser(user);
        post.setDateTime(LocalDateTime.now());
        postRepository.persist(post);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userID") Long userId){
        User user = userRepository.findById(userId);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        PanacheQuery<Post> query = postRepository.find("user", Sort.by("dateTime", Sort.Direction.Descending), user);
        List<Post> list = query.list();
        var postResponseList = list.stream().map(PostDto::fromEntity).toList();
        return Response.ok(postResponseList).build();
    }
}
