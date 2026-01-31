package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.otus.annotations.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.allFields = initAllFields(clazz);
        this.idField = initIdField();
        this.fieldsWithoutId = initFieldsWithoutId();
    }

    @Override
    public Constructor<T> getConstructor() throws NoSuchMethodException {
        return clazz.getConstructor();
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    // Поле Id должно определять по наличию аннотации Id
    // Аннотацию @Id надо сделать самостоятельно
    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

    private List<Field> initAllFields(Class<T> clazz) {
        return new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
    }

    private Field initIdField() {
        return allFields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Field annotated with @Id is not found."));
    }

    private List<Field> initFieldsWithoutId() {
        return allFields.stream()
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();
    }
}
