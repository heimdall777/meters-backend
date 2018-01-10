package ovh.cerebrum.meters.dao;

import ovh.cerebrum.meters.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> createUser(User user);

    Optional<List<User>> getAllUsers();

    Optional<User> findUserById(Long id);

    void updateUser(User user);
}
