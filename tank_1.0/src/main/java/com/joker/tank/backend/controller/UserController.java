package com.joker.tank.backend.controller;

import com.joker.tank.backend._frame.ResultMessage;
import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.backend.service.UserService;
import com.joker.tank.backend.service.impl.UserServiceImpl;
import com.joker.tank.backend.utils.ResultUtil;

import java.util.List;

/**
 * @author 燧枫
 * @date 2022/12/22 9:21
*/
public class UserController {

    private UserService userService = new UserServiceImpl();

    /**
     * 用户登录
     * @param username
     * @param password
     * @return User
     */
    public ResultMessage login(String username, String password) {
        try {
            User user = userService.login(username, password);
            return ResultUtil.data(user);
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @return User
     */
    public ResultMessage register(String username, String password) {
        try {
            User user = userService.register(username, password);
            return ResultUtil.data(user);
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public ResultMessage updateUserInfo(User userParam) {
        try {
            User user = userService.updateUserInfo(userParam);
            return ResultUtil.data(user);
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }

    /**
     * 一次性获取全部user
     * @return User的List
     */
    public ResultMessage getAllUser() {
        try {
            List<User> userList = userService.getAllUser();
            return ResultUtil.data(userList);
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }
}
