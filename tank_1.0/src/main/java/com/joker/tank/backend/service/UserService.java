package com.joker.tank.backend.service;
import com.joker.tank.backend.entity.dos.User;

import java.util.List;

/**
 * @author 燧枫
 * @date 2022/12/21 21:02
*/
public interface UserService {

    User login(String username, String password) throws Exception;

    User register(String username, String password) throws Exception;

    User updateUserInfo(User userParam) throws Exception;

    List<User> getAllUser() throws Exception;
}
