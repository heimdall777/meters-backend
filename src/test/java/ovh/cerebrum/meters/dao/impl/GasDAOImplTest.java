package ovh.cerebrum.meters.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ovh.cerebrum.meters.domain.Gas;

import static org.assertj.core.api.Assertions.assertThat;

class GasDAOImplTest {
    
    private GasDAOImpl gasDAO;

    @BeforeEach
    void init() {
        gasDAO = new GasDAOImpl();
    }

    @Test
    void shouldReturnCorrectClassForEnergy() {
        // Given
        String expectedClassName = "Gas";

        // Then
        Class<Gas> clazz = gasDAO.getClazz();

        // When
        assertThat(clazz.getSimpleName()).isEqualTo(expectedClassName);
    }

    @Test
    void shouldReturnCorrectTableName() {
        // Given
        String expectedTableName = "gas";

        // Then
        String tableName = gasDAO.getTableName();

        // When
        assertThat(tableName).isEqualTo(expectedTableName);
    }
}