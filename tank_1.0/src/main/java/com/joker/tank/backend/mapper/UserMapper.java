package com.joker.tank.backend.mapper;

import com.joker.tank.backend._frame.mapper.BaseMapper;
import com.joker.tank.backend._frame.mapper.mysql.BaseMapperMysql;
import com.joker.tank.backend._frame.mapper.mysql.Query;
import com.joker.tank.backend.entity.dos.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 燧枫
 * @date 2022/12/22 21:37
*/
public class UserMapper extends BaseMapperMysql<User> {

    // 根据用户名获取一个user
    public User selectOneByUsername(String username) {
        User user = selectOne(new Query().eqOr("username", username));
        if (user.getUsername() == null) return null;
        return user;
    }

    // 获取全部用户
    public List<User> selectAllByDeleteFlag() {
        List<User> userList = selectAll(new Query().eqOr("deleteFlag", 0));
        return userList;
    }

}
