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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDAOImplTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@email.com";
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
        when(sql2o.open()).thenReturn(connection);
        when(connection.createQuery(anyString())).thenReturn(query);
        when(connection.createQuery(anyString(), anyBoolean())).thenReturn(query);
        when(query.addParameter(anyString(), anyString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(connection);
    }

    @Test
    void shouldReturnValidUserWhenSaveSuccess() {
        // Given
        when(connection.getKey(Long.class)).thenReturn(1L);
        User user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setUsername(USERNAME);

        // When
        Optional<User> createdUser = userDAO.create(user);

        // Then
        assertThat(createdUser).isPresent().hasValue(user);
        assertThat(createdUser.get().getId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnEmptyOptionalWhenSaveFailed() {
        // Given
        when(connection.getKey(Long.class)).thenReturn(null);
        User user = new User(USERNAME, PASSWORD, EMAIL);

        // When
        Optional<User> createdUser = userDAO.create(user);

        // Then
        assertThat(createdUser).isEmpty();
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoRecordInDatabase() {
        // Given
        when(query.executeAndFetch(User.class)).thenReturn(null);

        // When
        Optional<List<User>> results = userDAO.getAll();

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    void shouldReturnOptionalWithListWhenRecordsInDatabase() {
        // Given
        List<User> users = new ArrayList<>();
        User user = new User(USERNAME, PASSWORD, EMAIL);
        users.add(user);

        when(query.executeAndFetch(User.class)).thenReturn(users);

        // When
        Optional<List<User>> results = userDAO.getAll();

        // Then
        assertThat(results).satisfies(u -> {
           assertThat(u).isPresent();
           assertThat(u.get()).hasSize(1);
        });
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoUserWithGivenId() {
        // Given
        Long id = 1L;
        when(query.addParameter("id", id)).thenReturn(query);
        when(query.executeAndFetch(User.class)).thenReturn(null);

        // When
        Optional<User> result = userDAO.findById(id);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnOptionalUserWhenUserWithId() {
        // Given
        Long id = 1L;
        User user = new User(USERNAME, PASSWORD, EMAIL);
        when(query.addParameter("id", id)).thenReturn(query);
        when(query.executeAndFetch(User.class)).thenReturn(Arrays.asList(user));

        // When
        Optional<User> result = userDAO.findById(id);

        // Then
        assertThat(result).satisfies(u -> {
            assertThat(u).isPresent();
            assertThat(u).hasValue(user);
        });
    }

    @Test
    void shouldUpdateUserWhenValid() {
        // Given
        User user = new User(USERNAME, PASSWORD, EMAIL);

        // When
        userDAO.update(user);

        //Then
        verify(connection, times(1)).createQuery(anyString());
        verify(query, times(3)).addParameter(anyString(), anyString());
        verify(query,  times(1)).executeUpdate();
    }


}