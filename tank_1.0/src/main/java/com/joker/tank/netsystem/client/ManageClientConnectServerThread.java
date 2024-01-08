package com.joker.tank.netsystem.client;

import java.util.HashMap;

/**
 * @author 燧枫
 * @date 2022/12/24 20:45
*/
// 管理客户端连接到服务器的线程的类
public class ManageClientConnectServerThread {

    // 我们把多个线程放入一个HashMap集合，key 就是用户id，value就是线程
    private static HashMap<String, CliConSerThread> hm = new HashMap<>();

    // 将某个线程加入到集合
    public static void addClientConnectServerThread(String username, CliConSerThread cliConSerThread) {
        hm.put(username, cliConSerThread);
    }

    // 通过username得到对应的线程
    public static CliConSerThread getClientConnetServerThread(String username) {
        return hm.get(username);
    }
}
