package ovh.cerebrum.meters.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ovh.cerebrum.meters.domain.ColdWater;

import static org.assertj.core.api.Assertions.assertThat;

class ColdWaterDAOImplTest {
    private ColdWaterDAOImpl coldWaterDAO;

    @BeforeEach
    void init() {
        coldWaterDAO = new ColdWaterDAOImpl();
    }

    @Test
    void shouldReturnCorrectClassForEnergy() {
        // Given
        String expectedClassName = "ColdWater";

        // Then
        Class<ColdWater> clazz = coldWaterDAO.getClazz();

        // When
        assertThat(clazz.getSimpleName()).isEqualTo(expectedClassName);
    }

    @Test
    void shouldReturnCorrectTableName() {
        // Given
        String expectedTableName = "cold_water";

        // Then
        String tableName = coldWaterDAO.getTableName();

        // When
        assertThat(tableName).isEqualTo(expectedTableName);
    }
}