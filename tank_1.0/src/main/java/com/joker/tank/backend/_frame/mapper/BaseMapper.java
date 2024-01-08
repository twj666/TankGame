package com.joker.tank.backend._frame.mapper;

import com.joker.tank.backend._frame.mapper.mysql.Query;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author 燧枫
 * @date 2022/12/18 18:00
*/
public interface BaseMapper<T> {

    T selectOne(Query query);

    ArrayList<T> selectAll(Query query);

    void insert(T t) throws SQLException;

    void upData(T t) throws SQLException, IllegalAccessException;
}
