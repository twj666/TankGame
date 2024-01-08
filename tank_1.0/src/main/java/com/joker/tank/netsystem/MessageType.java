package com.joker.tank.netsystem;

/**
 * @author 燧枫
 * @date 2022/12/24 18:51
*/
public interface MessageType {

    // 表示登录成功
    String MESSAGE_LOGIN_SUCCEED = "1";

    // 表示登录失败
    String MESSAGE_LOGIN_FAIL = "2";

    // 普通信息包
    String MESSAGE_COMM_MES = "3";

    // 请求返回在线用户列表
    String MESSAGE_GET_ONLINELIST = "4";

    // 返回在线用户列表
    String MESSAGE_RET_ONLINELIST = "5";

    // 客户端退出
    String MESSAGE_CLIENT_EXIT = "6";

    // 群发消息
    String MESSAGE_TO_ALL_MES = "7";
}
