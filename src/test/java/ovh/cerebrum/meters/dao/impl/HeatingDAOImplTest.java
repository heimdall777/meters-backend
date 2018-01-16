package ovh.cerebrum.meters.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ovh.cerebrum.meters.domain.Heating;

import static org.assertj.core.api.Assertions.assertThat;

class HeatingDAOImplTest {
    private HeatingDAOImpl heatingDAO;

    @BeforeEach
    void init() {
        heatingDAO = new HeatingDAOImpl();
    }

    @Test
    void shouldReturnCorrectClassForEnergy() {
        // Given
        String expectedClassName = "Heating";

        // Then
        Class<Heating> clazz = heatingDAO.getClazz();

        // When
        assertThat(clazz.getSimpleName()).isEqualTo(expectedClassName);
    }

    @Test
    void shouldReturnCorrectTableName() {
        // Given
        String expectedTableName = "heating";

        // Then
        String tableName = heatingDAO.getTableName();

        // When
        assertThat(tableName).isEqualTo(expectedTableName);
    }
}