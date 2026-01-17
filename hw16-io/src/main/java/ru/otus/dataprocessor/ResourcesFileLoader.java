package ru.otus.dataprocessor;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Measurement;

@JsonPropertyOrder({"version", "startLat", "startLng", "endLat", "endLng"})
public class ResourcesFileLoader implements Loader {
    // читает файл, парсит и возвращает результат
    private static final Logger logger = LoggerFactory.getLogger(ResourcesFileLoader.class);
    private final ObjectMapper mapper;
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.mapper = JsonMapper.builder().build();
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            return mapper.readValue(inputStream, new TypeReference<List<Measurement>>() {});
        } catch (IOException e) {
            logger.error("An exception occurred: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}
