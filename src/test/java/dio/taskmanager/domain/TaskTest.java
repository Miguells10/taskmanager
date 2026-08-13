package dio.taskmanager.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskTest {

    @Test
    void shouldRejectBlankTitle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(new TaskId(), " ", Optional.empty(), TaskStatus.Pending)
        );
    }
}
