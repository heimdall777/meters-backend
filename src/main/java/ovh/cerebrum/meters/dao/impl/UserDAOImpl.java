package ovh.cerebrum.meters.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ovh.cerebrum.meters.dao.UserDAO;
import ovh.cerebrum.meters.domain.User;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserDAOImpl implements UserDAO {

    private Sql2o sql2o;

    @Inject
    public UserDAOImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> create(User user) {
        log.debug("Creating new user.");

        try (Connection conn = sql2o.beginTransaction()) {
            Long id = conn.createQuery("INSERT INTO users(username, password, email) VALUES(:username, :password, :email)", true)
                    .addParameter("username", user.getUsername())
                    .addParameter("password", user.getPassword())
                    .addParameter("email", user.getEmail())
                    .executeUpdate()
                    .getKey(Long.class);

            conn.commit();

            if (id == null) {
                return Optional.empty();
            } else {
                log.debug("User created with id: " + id);
                user.setId(id);
                return Optional.of(user);
            }
        }
    }

    @Override
    public Optional<List<User>> getAll() {
        log.debug("Get all users.");

        try (Connection conn = sql2o.open()) {
            List<User> users = conn.createQuery("SELECT * FROM users").executeAndFetch(User.class);

            return Optional.ofNullable(users);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Connection conn = sql2o.open()) {
            List<User> users = conn.createQuery("SELECT * FROM users WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetch(User.class);

            if (users == null || users.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(users.get(0));
            }
        }
    }

    @Override
    public void update(User user) {
        try (Connection conn = sql2o.beginTransaction()) {
           conn.createQuery("update users set username=:username, password=:password, email=:email where id=:id")
                   .addParameter("username", user.getUsername())
                   .addParameter("password", user.getPassword())
                   .addParameter("email", user.getEmail())
                   .addParameter("id", user.getId())
                   .executeUpdate();

           conn.commit();
        }
    }

}
