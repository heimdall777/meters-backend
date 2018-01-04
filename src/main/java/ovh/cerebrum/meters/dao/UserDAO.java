package ovh.cerebrum.meters.dao;

import ovh.cerebrum.meters.domain.User;

public interface UserDAO {

    User createUser(String username, String password, String email);
}
