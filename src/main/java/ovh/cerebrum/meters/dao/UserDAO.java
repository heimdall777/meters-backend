package ovh.cerebrum.meters.dao;

import ovh.cerebrum.meters.domain.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> createUser(User user);
}
