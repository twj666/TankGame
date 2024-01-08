package com.joker.tank.backend._frame.mapper.mysql;

import com.joker.tank.backend._frame.Annontation.Table;
import com.joker.tank.backend._frame.mapper.BaseMapper;
import com.joker.tank.backend._frame.dbconnect.MySQLConnect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author 燧枫
 * @date 2022/12/18 18:00
 */
public class BaseMapperMysql<T> implements BaseMapper<T> {

    private final ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
    private final Type type = parameterizedType.getActualTypeArguments()[0];
    private String tableName;

    public BaseMapperMysql() {
        tableName = getTableName();
    }

    @Override
    public T selectOne(Query query) {
        MySQLConnect mySQLConnect = new MySQLConnect();
        try {
            Statement statement = mySQLConnect.connect();
            assert statement != null;
            T t = ((Class<T>) type).getConstructor().newInstance();
            query.limit(1);
            ResultSet result = statement.executeQuery("select * from " + tableName + query.getSql());
            copyToData(t, result);
            return t;
        } catch (InvocationTargetException | InstantiationException | SQLException | IllegalAccessException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            mySQLConnect.close();
        }
        return null;
    }

    @Override
    public ArrayList<T> selectAll(Query query) {
        MySQLConnect mySQLConnect = new MySQLConnect();
        try {
            Statement statement = mySQLConnect.connect();
            assert statement != null;
            ArrayList<T> list = new ArrayList<>();
            ResultSet result = statement.executeQuery("select * from " + tableName + query.getSql());
            copyToData(list, result);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConnect.close();
        }
        return null;
    }

    @Override
    public void insert(T t) {
        MySQLConnect mySQLConnect = new MySQLConnect();
        try {
            Statement statement = mySQLConnect.connect();
            assert statement != null;
            String sql = "insert into " + tableName;
            StringBuilder key = new StringBuilder(" (");
            StringBuilder value = new StringBuilder(" (");

            Field[] field = t.getClass().getDeclaredFields();
            field[0].setAccessible(true);
            key.append(field[0].getName());
            value.append('\'').append(field[0].get(t));
            for (int i = 1; i < field.length; i++) {
                field[i].setAccessible(true);
                key.append(",").append(field[i].getName());
                value.append('\'' + "," + '\'').append(field[i].get(t));
            }
            sql += key + ") values " + value + '\'' + ")";
            statement.executeUpdate(sql);
        } catch (IllegalAccessException | SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConnect.close();
        }
    }

    @Override
    public void upData(T t) {
        MySQLConnect mySQLConnect = new MySQLConnect();
        try {
            Statement statement = mySQLConnect.connect();
            assert statement != null;
            Field[] field = t.getClass().getDeclaredFields();
            field[0].setAccessible(true);
            for (int i = 1; i < field.length; i++) {
                field[i].setAccessible(true);
                statement.executeUpdate("update " + tableName + " set " + field[i].getName() + "=" + '\'' + field[i].get(t) + '\'' + " where " + field[0].getName() + "=" + '\'' + field[0].get(t) + '\'');
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            mySQLConnect.close();
        }
    }

    public String getTableName() {
        Class<?> c = null;
        try {
            c = Class.forName(type.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        boolean hasAnnotation = c.isAnnotationPresent(Table.class);
        if (hasAnnotation) {
            Table table = c.getAnnotation(Table.class);
            return table.TableName();
        }
        return null;
    }

    public void copyProperties(Object source, Object target) {
        if (target == null || source == null) {
            return;
        }
        Field[] fieldTarget = target.getClass().getDeclaredFields();
        Field[] fieldSource = source.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fieldTarget.length; i++) {
                for (int j = 0; j < fieldSource.length; j++) {
                    if (fieldTarget[i].getName().equals(fieldSource[j].getName()) &&
                            fieldTarget[i].getGenericType().toString().equals(fieldSource[i].getGenericType().toString())) {
                        fieldTarget[i].setAccessible(true);
                        fieldSource[j].setAccessible(true);
                        fieldTarget[j].set(target, fieldSource[i].get(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyToData(T t, ResultSet result) {
        Field[] fieldTarget = t.getClass().getDeclaredFields();
        try {
            while (result.next())
                for (Field field : fieldTarget) {
                    field.setAccessible(true);
                    field.set(t, result.getObject(field.getName()));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyToData(ArrayList<T> t, ResultSet result) {
        int len = 0;
        try {
            while (result.next()) {
                t.add(((Class<T>) type).getConstructor().newInstance());
                Field[] fieldTarget = t.get(len).getClass().getDeclaredFields();
                for (Field field : fieldTarget) {
                    field.setAccessible(true);
                    field.set(t.get(len), result.getObject(field.getName()));
                }
                len++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
