package ru.otus.processor.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.provider.DateTimeProvider;

public class ProcessorEvenSeconds implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(ProcessorEvenSeconds.class);
    private final DateTimeProvider dateTimeProvider;

    public ProcessorEvenSeconds(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        int second = dateTimeProvider.getDateTime().getSecond();
        if (second % 2 == 0) {
            logger.error("Throw RuntimeException at an even second: {}", second);
            throw new RuntimeException("An exception occurred at an even second: " + second);
        }
        return message;
    }
}
