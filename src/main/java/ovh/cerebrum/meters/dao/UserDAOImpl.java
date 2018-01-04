package ovh.cerebrum.meters.dao;

import ovh.cerebrum.meters.domain.User;

public class UserDAOImpl implements UserDAO {
    @Override
    public User createUser(String username, String password, String email) {
        return new User();
    }
}
