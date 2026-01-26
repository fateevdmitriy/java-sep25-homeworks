package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

/** "Разбирает" объект на составные части */
public interface EntityClassMetaData<T> {

    Constructor<T> getConstructor() throws NoSuchMethodException;

    String getName();

    // Поле Id должно определять по наличию аннотации Id
    // Аннотацию @Id надо сделать самостоятельно
    Field getIdField();

    List<Field> getAllFields();

    List<Field> getFieldsWithoutId();
}
