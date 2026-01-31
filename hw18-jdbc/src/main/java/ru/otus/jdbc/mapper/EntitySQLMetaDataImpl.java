package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final String entityClassName;
    private final List<Field> allFieldNames;
    private final String idFieldName;
    private final List<Field> nonIdFieldNames;
    private final String sqlSelectAll;
    private final String sqlSelectById;
    private final String sqlInsert;
    private final String sqlUpdate;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassName = entityClassMetaData.getName();
        this.allFieldNames = entityClassMetaData.getAllFields();
        this.idFieldName = entityClassMetaData.getIdField().getName();
        this.nonIdFieldNames = entityClassMetaData.getFieldsWithoutId();
        this.sqlSelectAll = initSelectAllSql();
        this.sqlSelectById = initSelectByIdSql();
        this.sqlInsert = initInsertSql();
        this.sqlUpdate = initUpdateSql();
    }

    @Override
    public String getSelectAllSql() {
        return sqlSelectAll;
    }

    @Override
    public String getSelectByIdSql() {
        return sqlSelectById;
    }

    @Override
    public String getInsertSql() {
        return sqlInsert;
    }

    @Override
    public String getUpdateSql() {
        return sqlUpdate;
    }

    private String initSelectAllSql() {
        String queryTemplate = "select %s from %s";
        String columns = allFieldNames.stream().map(Field::getName).collect(Collectors.joining(","));
        return String.format(queryTemplate, columns, entityClassName);
    }

    private String initSelectByIdSql() {
        String queryTemplate = "select * from %s where %s = ?";
        return String.format(queryTemplate, entityClassName, idFieldName);
    }

    private String initInsertSql() {
        String queryTemplate = "insert into %s (%s) values (%s)";
        String columns = nonIdFieldNames.stream().map(Field::getName).collect(Collectors.joining(","));
        String values = nonIdFieldNames.stream().map(field -> "?").collect(Collectors.joining(","));
        return String.format(queryTemplate, entityClassName, columns, values);
    }

    private String initUpdateSql() {
        String queryTemplate = "update %s set %s where %s = ?";
        String columnsValues = nonIdFieldNames.stream()
                .map(field -> String.format("%s = ?", field.getName()))
                .collect(Collectors.joining(","));
        return String.format(queryTemplate, entityClassName, columnsValues, idFieldName);
    }
}
