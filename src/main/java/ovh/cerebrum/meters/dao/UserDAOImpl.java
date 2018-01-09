package ovh.cerebrum.meters.dao;

import lombok.extern.slf4j.Slf4j;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ovh.cerebrum.meters.domain.User;

import javax.inject.Inject;
import java.util.Optional;

@Slf4j
public class UserDAOImpl implements UserDAO {

    private Sql2o sql2o;

    @Inject
    public UserDAOImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> createUser(User user) {
        log.debug("Creating new user.");

        try (Connection conn = sql2o.beginTransaction()) {
            Long id = conn.createQuery("INSERT INTO users(username, password, email) VALUES(:username, :password, :email)")
                    .addParameter("username", user.getUsername())
                    .addParameter("password", user.getPassword())
                    .addParameter("email", user.getEmail())
                    .executeUpdate()
                    .getKey(Long.class);

            if (id == null) {
                return Optional.empty();
            } else {
                user.setId(id);
                return Optional.of(user);
            }
        }
    }
}
