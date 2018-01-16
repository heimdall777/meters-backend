package ovh.cerebrum.meters.dao;

import ovh.cerebrum.meters.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> create(User user);

    Optional<List<User>> getAll();

    Optional<User> findById(Long id);

    void update(User user);
}
