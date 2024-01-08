package com.joker.tank.netsystem;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 燧枫
 * @date 2022/12/24 18:46
*/
// 网络通信消息类
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    // 发送者用户名
    private String sender;

    // 接受者用户名
    private String getter;

    // 消息内容
    private String content;

    // 发送时间
    private String sendTime;

    // 消息类型
    private String mesType;
}
