package quarkus.rest.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.rest.model.UserModel;
import quarkus.rest.resources.UserResource;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserModel> {



}
