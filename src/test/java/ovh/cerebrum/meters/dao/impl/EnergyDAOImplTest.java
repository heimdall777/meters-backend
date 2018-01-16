package ovh.cerebrum.meters.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ovh.cerebrum.meters.domain.Energy;
import ovh.cerebrum.meters.extension.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnergyDAOImplTest {

    private static final Double TEST_QUANTITY = 125.23d;
    private static final String TEST_DATE = "2018-03-12";
    private static final LocalDate TEST_READ_DATE = LocalDate.parse(TEST_DATE);
    private static final Long TEST_ID = 1L;
    private static final Long TEST_UNIT_ID = 2L;
    private static final Long TEST_USER_ID = 3L;

    private EnergyDAOImpl energyDAO;

    @Mock
    private Sql2o sql2o;

    @Mock
    private Connection connection;

    @Mock
    private Query query;

    @BeforeEach
    void init() {
        energyDAO = new EnergyDAOImpl();
        energyDAO.setSql2o(sql2o);
        when(sql2o.beginTransaction()).thenReturn(connection);
        when(sql2o.open()).thenReturn(connection);
        when(connection.createQuery(anyString(), anyBoolean())).thenReturn(query);
        when(connection.createQuery(anyString())).thenReturn(query);
        when(query.addParameter(anyString(), anyDouble())).thenReturn(query);
        when(query.addParameter(anyString(), any(LocalDate.class))).thenReturn(query);
        when(query.addParameter(anyString(), anyLong())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(connection);
    }

    @Test
    void shouldReturnCorrectClassForEnergy() {
        // Given
        String expectedClassName = "Energy";

        // Then
        Class<Energy> clazz = energyDAO.getClazz();

        // When
        assertThat(clazz.getSimpleName()).isEqualTo(expectedClassName);
    }

    @Test
    void shouldReturnCorrectTableName() {
        // Given
        String expectedTableName = "energy";

        // Then
        String tableName = energyDAO.getTableName();

        // When
        assertThat(tableName).isEqualTo(expectedTableName);
    }

    @Test
    void shouldReturnValidEnergyWhenSavedSuccess() {
        // Given
        when(query.addParameter("unit_id", TEST_UNIT_ID)).thenReturn(query);
        when(query.addParameter("user_id", TEST_USER_ID)).thenReturn(query);
        when(connection.getKey(Long.class)).thenReturn(TEST_ID);
        Energy energy = new Energy();
        energy.setQuantity(TEST_QUANTITY);
        energy.setReadDate(TEST_READ_DATE);
        energy.setUnitId(TEST_UNIT_ID);
        energy.setUserId(TEST_USER_ID);

        // When
        Optional<Energy> energyCreated = energyDAO.create(energy);

        // Then
        assertThat(energyCreated).satisfies(e -> {
            assertThat(e).isPresent();
            assertThat(e.get().getId()).isEqualTo(TEST_ID);
            assertThat(e.get().getReadDate()).isEqualTo(TEST_DATE);
        });
    }

    @Test
    void shouldReturnEmptyOptionalWhenSaveFailed() {
        // Given
        when(query.addParameter("unit_id", TEST_UNIT_ID)).thenReturn(query);
        when(query.addParameter("user_id", TEST_USER_ID)).thenReturn(query);
        when(connection.getKey(Long.class)).thenReturn(null);

        Energy energy = new Energy(TEST_QUANTITY, TEST_READ_DATE, TEST_UNIT_ID, TEST_USER_ID);

        // When
        Optional<Energy> emptyResult = energyDAO.create(energy);

        // Then
        assertThat(emptyResult).isEmpty();

    }

    @Test
    void shouldReturnEmptyOptionalWhenNoRecordInDatabase() {
        // Given
        when(query.executeAndFetch(Energy.class)).thenReturn(null);

        // When
        Optional<List<Energy>> results = energyDAO.getAll();

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    void shouldReturnOptionalWithEntityListWhenRecordsInDatabase() {
        // Given
        List<Energy> energyList = new ArrayList<>();
        Energy energy = new Energy(TEST_QUANTITY, TEST_READ_DATE, TEST_UNIT_ID, TEST_USER_ID);
        energyList.add(energy);

        when(query.executeAndFetch(Energy.class)).thenReturn(energyList);

        // When
        Optional<List<Energy>> results = energyDAO.getAll();

        // Then
        assertThat(results).satisfies(e -> {
            assertThat(e).isPresent();
            assertThat(e.get()).hasSize(1);
            assertThat(e.get().get(0).getQuantity()).isEqualTo(TEST_QUANTITY);
        });

    }

    @Test
    void shouldReturnEmptyOptionalWhenNoEnergyWithGivenId() {
        // Given
        when(query.addParameter("id", TEST_ID)).thenReturn(query);
        when(query.executeAndFetch(Energy.class)).thenReturn(null);

        // When
        Optional<Energy> result = energyDAO.findById(TEST_ID);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnOptionalEnergyWhenEnergyExistInDBWithId() {
        // Given
        Energy energy = new Energy(TEST_QUANTITY, TEST_READ_DATE, TEST_UNIT_ID, TEST_USER_ID);
        energy.setId(TEST_ID);
        when(query.addParameter("id", TEST_ID)).thenReturn(query);
        when(query.executeAndFetch(Energy.class)).thenReturn(Collections.singletonList(energy));

        // When
        Optional<Energy> result = energyDAO.findById(TEST_ID);

        // Then
        assertThat(result).satisfies(e -> {
            assertThat(e).isPresent();
            assertThat(e).hasValue(energy);
        });
    }

    @Test
    void shouldUpdateEnergyWhenValid() {
        when(query.addParameter("id", TEST_ID)).thenReturn(query);
        when(query.addParameter("unit_id", TEST_UNIT_ID)).thenReturn(query);
        when(query.addParameter("user_id", TEST_USER_ID)).thenReturn(query);

        Energy energy = new Energy(TEST_QUANTITY, TEST_READ_DATE, TEST_UNIT_ID, TEST_USER_ID);
        energy.setId(TEST_ID);

        // When
        energyDAO.update(energy);

        // Then
        verify(connection, times(1)).createQuery(anyString());
        verify(query, times(1)).addParameter(anyString(), anyDouble());
        verify(query, times(1)).addParameter(anyString(), any(LocalDate.class));
        verify(query, times(1)).addParameter("unit_id", TEST_UNIT_ID);
        verify(query, times(1)).addParameter("user_id", TEST_USER_ID);
        verify(query, times(1)).executeUpdate();
    }

}