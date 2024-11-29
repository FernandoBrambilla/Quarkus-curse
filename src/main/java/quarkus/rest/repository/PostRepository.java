package quarkus.rest.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.rest.entities.Post;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
}
