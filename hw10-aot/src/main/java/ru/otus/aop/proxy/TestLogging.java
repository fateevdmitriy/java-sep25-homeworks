package ru.otus.aop.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.Log;

public class TestLogging implements TestLoggingInterface {
    private static final Logger logger = LoggerFactory.getLogger(TestLogging.class);

    @Log
    @Override
    public void calculation(int param1) {
        // logger.info("Run calculation({})", param1);
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        // logger.info("Run calculation({}, {})", param1, param2);
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        // logger.info("Run calculation({}, {}, {} )", param1, param2, param3);
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3, boolean param4) {
        // logger.info("Run calculation({}, {}, {}, {})", param1, param2, param3, param4);
    }

    @Override
    public String toString() {
        return "TestLogging{}";
    }
}
