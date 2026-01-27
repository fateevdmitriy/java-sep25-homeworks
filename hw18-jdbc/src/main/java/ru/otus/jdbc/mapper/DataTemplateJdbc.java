package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/** Сохраняет объект в базу, читает объект из базы */
@SuppressWarnings({"java:S1068", "java:S3011"})
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return getEntity(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    var entityList = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            entityList.add(getEntity(rs));
                        }
                        return entityList;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T entity) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getParams(entity));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T entity) {
        try {
            dbExecutor.executeStatement(
                    connection,
                    entitySQLMetaData.getUpdateSql(),
                    List.of(getParams(entity), entityClassMetaData.getIdField()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T getEntity(ResultSet resultSet) {
        try {
            /*
            T inst = entityClassMetaData.getConstructor().newInstance();
            List<Field> fields = entityClassMetaData.getAllFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                Object fieldValue = resultSet.getObject(fieldName);
                field.setAccessible(true);
                field.set(inst, fieldValue);
            }
            return inst;
            */
            List<Object> instanceFieldValues = new ArrayList<>();
            instanceFieldValues.add(
                    resultSet.getObject(entityClassMetaData.getIdField().getName()));
            List<Field> nonIdFields = entityClassMetaData.getFieldsWithoutId();
            for (Field field : nonIdFields) {
                instanceFieldValues.add(resultSet.getObject(field.getName()));
            }
            return entityClassMetaData.getConstructor().newInstance(instanceFieldValues.toArray());
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getParams(T object) {
        try {
            List<Object> params = new ArrayList<>();
            List<Field> nonIdFields = entityClassMetaData.getFieldsWithoutId();
            for (Field field : nonIdFields) {
                field.setAccessible(true);
                Object value = field.get(object);
                params.add(value);
            }
            return params;
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }
}
