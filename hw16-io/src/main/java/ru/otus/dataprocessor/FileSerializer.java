package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSerializer implements Serializer {
    private static final Logger logger = LoggerFactory.getLogger(FileSerializer.class);
    private final ObjectMapper mapper;
    private final File file;

    public FileSerializer(String fileName) {
        this.mapper = JsonMapper.builder()
                .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
                .build();
        this.file = new File(fileName);
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        try {
            mapper.writeValue(file, data);
        } catch (IOException e) {
            throw new FileProcessException(e.getMessage());
        }
    }
}
