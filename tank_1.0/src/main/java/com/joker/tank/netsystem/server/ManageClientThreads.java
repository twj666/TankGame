package com.joker.tank.netsystem.server;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 燧枫
 * @date 2022/12/24 21:24
*/
// 该类用于管理和客户端通信的线程
public class ManageClientThreads {

    private static HashMap<String, SerConClieThread> hm = new HashMap<>();

    // 添加线程对象到 hm集合
    public static void addClientThread(String username, SerConClieThread serConClieThread) {
        hm.put(username, serConClieThread);
    }

    // 根据username从hm 中取得线程对象
    public static SerConClieThread getServerConnectClientThread(String username) {
        return hm.get(username);
    }

    // 从集合中移除某个线程对象
    public static void removeServerConnectClientThread(String username) {
        hm.remove(username);
    }

    // 返回在线用户列表
    public static String getOnlineUser() {
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList = "";
        while (iterator.hasNext()) {
            onlineUserList += iterator.next().toString() + " ";
        }
        return onlineUserList;
    }

    public static HashMap<String, SerConClieThread> getHm() {
        return hm;
    }
}
