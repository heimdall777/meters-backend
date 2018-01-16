package ovh.cerebrum.meters.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ovh.cerebrum.meters.domain.HotWater;

import static org.assertj.core.api.Assertions.assertThat;

class HotWaterDAOImplTest {
    private HotWaterDAOImpl hotWaterDAO;

    @BeforeEach
    void init() {
        hotWaterDAO = new HotWaterDAOImpl();
    }

    @Test
    void shouldReturnCorrectClassForEnergy() {
        // Given
        String expectedClassName = "HotWater";

        // Then
        Class<HotWater> clazz = hotWaterDAO.getClazz();

        // When
        assertThat(clazz.getSimpleName()).isEqualTo(expectedClassName);
    }

    @Test
    void shouldReturnCorrectTableName() {
        // Given
        String expectedTableName = "hot_water";

        // Then
        String tableName = hotWaterDAO.getTableName();

        // When
        assertThat(tableName).isEqualTo(expectedTableName);
    }
}