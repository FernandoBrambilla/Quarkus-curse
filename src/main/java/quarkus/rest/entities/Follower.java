package quarkus.rest.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "followers")
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    @NotNull
    private User follower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    public @NotNull User getFollower() {
        return follower;
    }

    public void setFollower(@NotNull User follower) {
        this.follower = follower;
    }
}
