package ovh.cerebrum.meters.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ovh.cerebrum.meters.domain.User;
import ovh.cerebrum.meters.extension.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDAOImplTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email@email.com";
    private UserDAO userDAO;

    @Mock
    private Sql2o sql2o;

    @Mock
    private Connection connection;

    @Mock
    private Query query;

    @BeforeEach
    void init() {
        userDAO = new UserDAOImpl(sql2o);
        when(sql2o.beginTransaction()).thenReturn(connection);
        when(connection.createQuery(anyString())).thenReturn(query);
        when(query.addParameter(anyString(), anyString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(connection);
        when(connection.getKey(Long.class)).thenReturn(1L);
    }

    @Test
    void givenValidUser_whenSaveUser_thenSucceed() {
        // Given
        User user = new User(USERNAME, PASSWORD, EMAIL);

        // When
        Optional<User> createdUser = userDAO.createUser(user);

        // Then
        assertThat(createdUser).isPresent().hasValue(user);
    }

}