package ovh.cerebrum.meters.dao.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ovh.cerebrum.meters.domain.Unit;
import ovh.cerebrum.meters.extension.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitDAOImplTest {

    private static final String TEST_UNIT_CD = "TEST_UNIT_CD";
    private static final String TEST_NAME = "TEST_NAME";
    private static final Long TEST_ID = 1L;
    private UnitDAOImpl unitDAO;

    @Mock
    private Sql2o sql2o;

    @Mock
    private Connection connection;

    @Mock
    private Query query;

    @BeforeEach
    void init() {
        unitDAO = new UnitDAOImpl(sql2o);
        when(sql2o.beginTransaction()).thenReturn(connection);
        when(sql2o.open()).thenReturn(connection);
        when(connection.createQuery(anyString())).thenReturn(query);
        when(connection.createQuery(anyString(), anyBoolean())).thenReturn(query);
        when(query.addParameter(anyString(), anyString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(connection);
    }

    @Test
    void shouldReturnValidUnitWhenSavedSuccess() {
        // Given
        when(connection.getKey(Long.class)).thenReturn(1L);
        Unit unit = new Unit();
        unit.setUnitCd(TEST_UNIT_CD);
        unit.setName(TEST_NAME);
        unit.setId(TEST_ID);

        // When
        Optional<Unit> createdUnit = unitDAO.create(unit);

        // Then
        assertThat(createdUnit).satisfies(u -> {
            assertThat(u).isPresent();
            assertThat(u.get().getId()).isEqualTo(TEST_ID);
            assertThat(u.get().getUnitCd()).isEqualTo(TEST_UNIT_CD);
        });
    }

    @Test
    void shouldReturnEmptyOptionalWhenSaveFailed() {
        // Given
        when(connection.getKey(Long.class)).thenReturn(null);
        Unit unit = new Unit(TEST_UNIT_CD, TEST_NAME);

        // When
        Optional<Unit> emptyResult = unitDAO.create(unit);

        // Then
        assertThat(emptyResult).isEmpty();
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoRecordInDatabase() {
        // Given
        when(query.executeAndFetch(Unit.class)).thenReturn(null);

        // When
        Optional<List<Unit>> results = unitDAO.getAll();

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    void shouldReturnOptionalWithListWhenUnitRecordsInDatabase() {
        // Given
        List<Unit> units = new ArrayList<>();
        Unit unit = new Unit(TEST_UNIT_CD, TEST_NAME);
        units.add(unit);

        when(query.executeAndFetch(Unit.class)).thenReturn(units);

        // When
        Optional<List<Unit>> results = unitDAO.getAll();

        // Then
        assertThat(results).satisfies(u -> {
            assertThat(u).isPresent();
            assertThat(u.get()).hasSize(1);
            assertThat(u.get().get(0).getUnitCd()).isEqualTo(TEST_UNIT_CD);
        });
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoUnitWithGivenId() {
        // Given
        when(query.addParameter("id", TEST_ID)).thenReturn(query);
        when(query.executeAndFetch(Unit.class)).thenReturn(null);

        // When
        Optional<Unit> result = unitDAO.findById(TEST_ID);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnOptionalUnitWhenUnitInDatabaseWithId() {
        // Given
        Unit unit = new Unit(TEST_UNIT_CD, TEST_NAME);

        when(query.addParameter("id", TEST_ID)).thenReturn(query);
        when(query.executeAndFetch(Unit.class)).thenReturn(Collections.singletonList(unit));

        // When
        Optional<Unit> result = unitDAO.findById(TEST_ID);

        // Then
        assertThat(result).satisfies(u -> {
            assertThat(u).isPresent();
            assertThat(u).hasValue(unit);
        });
    }

    @Test
    void shouldUpdateUnitWhenValid() {
        // Given
        when(query.addParameter("id", TEST_ID)).thenReturn(query);

        Unit unit = new Unit(TEST_UNIT_CD, TEST_NAME);
        unit.setId(TEST_ID);
        // When
        unitDAO.update(unit);

        //Then
        verify(connection, times(1)).createQuery(anyString());
        verify(query, times(2)).addParameter(anyString(), anyString());
        verify(query, times(1)).addParameter("id", TEST_ID);
        verify(query,  times(1)).executeUpdate();
    }

}