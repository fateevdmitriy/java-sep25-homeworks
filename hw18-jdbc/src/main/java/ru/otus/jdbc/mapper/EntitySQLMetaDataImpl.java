package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final String entityClassName;
    private final String idFieldName;
    private final List<Field> nonIdFieldNames;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassName = entityClassMetaData.getName();
        this.idFieldName = entityClassMetaData.getIdField().getName();
        this.nonIdFieldNames = entityClassMetaData.getFieldsWithoutId();
    }

    @Override
    public String getSelectAllSql() {
        String queryTemplate = "select * from %s";
        return String.format(queryTemplate, entityClassName);
    }

    @Override
    public String getSelectByIdSql() {
        String queryTemplate = "select * from %s where %s = ?";
        return String.format(queryTemplate, entityClassName, idFieldName);
    }

    @Override
    public String getInsertSql() {
        //INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);
        String queryTemplate = "insert into %s (%s) values (%s)";
        String columns = nonIdFieldNames.stream()
                            .map(Field::getName)
                            .collect(Collectors.joining(","));
        String values = nonIdFieldNames.stream()
                            .map(field -> "?")
                            .collect(Collectors.joining(","));
        return String.format(queryTemplate, entityClassName, columns, values);
    }

    @Override
    public String getUpdateSql() {
        // UPDATE table_name SET column1 = value1, column2 = value2 WHERE condition;
        String queryTemplate = "update %s set %s where %s = ?";
        String columnsValues = nonIdFieldNames.stream()
                .map(field -> String.format("%s = ?", field.getName()))
                .collect(Collectors.joining(","));
        return String.format(queryTemplate, entityClassName, columnsValues, idFieldName);
    }
}
