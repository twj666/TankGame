package com.joker.tank.backend.service.impl;

import com.joker.tank.backend._frame.Annontation.Table;
import com.joker.tank.backend._frame.mapper.mysql.Query;
import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.backend.mapper.UserMapper;
import com.joker.tank.backend.service.UserService;
import com.joker.tank.backend.utils.SnowFlakeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 燧枫
 * @date 2022/12/19 9:44
*/
public class UserServiceImpl implements UserService {

    UserMapper userMapper = new UserMapper();

    /**
     * 用户登录
     * @param username
     * @param password
     * @return User
     */
    @Override
    public User login(String username, String password) throws Exception {
        // 先根据用户名得到用户实体类
        User user = userMapper.selectOneByUsername(username);
        // 如果查不到，直接抛异常
        if (user == null)
            throw new Exception("用户名不存在");
        // 再判断密码是否是正确的, 不正确，直接抛异常
        if (!user.getPassword().equals(password))
            throw new Exception("密码错误");
        return user;
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @return User
     */
    @Override
    public User register(String username, String password) throws Exception {
        // 先根据用户名得到用户实体类
        User user = userMapper.selectOneByUsername(username);
        // 如果查到了，直接抛异常
        if (user != null)
            throw new Exception("此用户名已存在");
        // 没查到，根据传过来的账号，密码，利用雪花算法生成一个新的用户实体类
        user = new User();
        user.setId(new SnowFlakeUtil(0, 0).nextId());
        user.setUsername(username);
        user.setPassword(password);
        // 将此数据插入数据库中
        userMapper.insert(user);
        return user;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public User updateUserInfo(User userParam) throws Exception {
        // 先根据用户名查看是否存在此用户
        User user = userMapper.selectOneByUsername(userParam.getUsername());
        // 如果查不到，直接抛异常
        if (user == null)
            throw new Exception("此用户不存在");
        // 查到了，更新数据到数据库
        userMapper.upData(userParam);
        return user;
    }

    /**
     * 一次性获取全部user
     * @return
     * @throws Exception
     */
    @Override
    public List<User> getAllUser() throws Exception {
        List<User> userList = userMapper.selectAllByDeleteFlag();
        return userList;
    }
}
