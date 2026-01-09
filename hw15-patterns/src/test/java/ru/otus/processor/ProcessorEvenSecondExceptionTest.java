package ru.otus.processor;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorEvenSeconds;
import ru.otus.provider.DateTimeProvider;

class ProcessorEvenSecondExceptionTest {

    @Test
    @DisplayName("Тест выброса исключения на четной секунде")
    void ThrowingExceptionAtEvenSecond() {
        // given
        DateTimeProvider dateTimeProvider = () -> LocalDateTime.of(2026, 1, 7, 10, 50, 10);
        // when
        Processor processor = new ProcessorEvenSeconds(dateTimeProvider);
        Message message = new Message.Builder(1L).build();
        // then
        assertThatThrownBy(() -> {
                    processor.process(message);
                })
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Тест отсутствия исключения на нечетной секунде")
    void NonThrowingExceptionAtOddSecond() {
        // given
        DateTimeProvider dateTimeProvider = () -> LocalDateTime.of(2026, 1, 7, 10, 50, 15);
        // when
        Processor processor = new ProcessorEvenSeconds(dateTimeProvider);
        Message message = new Message.Builder(2L).build();
        // then
        assertThatCode(() -> {
                    processor.process(message);
                })
                .doesNotThrowAnyException();
    }
}
