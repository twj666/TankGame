package com.joker.tank.backend._frame.mapper.mysql;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * @author 燧枫
 * @date 2022/12/18 18:00
*/
public class Query {

    private StringBuilder sql = new StringBuilder();

    public Query eqOr(String fieldName, Object fieldValue) {
        if (sql.length() != 0) {
            sql.append(" or ");
        } else {
            sql.append(" where ");
        }
        sql.append(fieldName).append("=").append('\'').append(fieldValue).append('\'');
        return this;
    }

    public Query eqAnd(String fieldName, Object fieldValue) {
        if (sql.length() != 0) {
            sql.append(" and ");
        } else {
            sql.append(" where ");
        }
        sql.append(fieldName).append("=").append('\'').append(fieldValue).append('\'');
        return this;
    }

    public void orderBy(String fieldName, boolean flag) {
        sql.append(" order by ").append(fieldName);
        if (flag) sql.append(" asc");
        else sql.append(" desc");
    }

    public void limit(int level) {
        sql.append(" limit ").append(level);
    }

    public void limit(int minLevel, int maxLevel) {
        sql.append(" limit ").append(minLevel).append(",").append(maxLevel);
    }

    public String getSql() {
        return sql.toString();
    }
}