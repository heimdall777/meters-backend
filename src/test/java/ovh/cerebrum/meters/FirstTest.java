package ovh.cerebrum.meters;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FirstTest {

    @Test
    void firstTest() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        assertTrue(numbers.stream()
                .mapToInt(i -> i)
                .sum() > 5, "Sum should be greater than 5");
    }
}
