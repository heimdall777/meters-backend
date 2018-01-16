package ovh.cerebrum.meters.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ovh.cerebrum.meters.domain.Energy;

import static org.assertj.core.api.Assertions.assertThat;

class EnergyDAOImplTest {

    private EnergyDAOImpl energyDAO;

    @BeforeEach
    void init() {
        energyDAO = new EnergyDAOImpl();
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


}