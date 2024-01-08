package com.joker.tank.netsystem.server;

import com.joker.tank.netsystem.Message;
import com.joker.tank.netsystem.MessageType;
import com.joker.tank.screen.TankServerFrame;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 燧枫
 * @date 2022/12/24 21:15
 */
// 该类的一个对象和某个客户端保持通信
public class SerConClieThread extends Thread {

    private Socket sk;

    private String username;

    private TankServerFrame tankServerFrame;

    public SerConClieThread(Socket sk, String username, TankServerFrame tankServerFrame) {
        this.sk = sk;
        this.username = username;
        this.tankServerFrame = tankServerFrame;
    }

    public Socket getSk() {
        return sk;
    }

    @Override
    // 这里线程处于run的状态，可以发送/接收消息
    public void run() {
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(sk.getInputStream());
                Message ms = (Message) ois.readObject();
                // 后面会使用message，根据message的类型，做相应的业务处理
                if (ms.getMesType().equals(MessageType.MESSAGE_GET_ONLINELIST)) {
                    // 客户端要在线用户列表
                    String onlineUser = ManageClientThreads.getOnlineUser();
                    // 返回message
                    // 构建一个message对象，返回给客户端
                    Message ms1 = new Message();
                    ms1.setMesType(MessageType.MESSAGE_RET_ONLINELIST);
                    ms1.setContent(onlineUser);
                    // 返回给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(sk.getOutputStream());
                    oos.writeObject(ms1);
                } else if (ms.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // 更新服务器界面的信息
                    tankServerFrame.oneToOne(ms.getSender(), ms.getGetter(), ms.getContent());
                    // 转发聊天消息
                    // 根据message获取getter id，然后在得到对应先线程
                    SerConClieThread scct = ManageClientThreads.getServerConnectClientThread(ms.getGetter());
                    // 得到对应socket的对象输出流，将message对象转发给指定的客户
                    ObjectOutputStream oos = new ObjectOutputStream(scct.getSk().getOutputStream());
                    // 如果客户不在线，可以保存到数据库
                    oos.writeObject(ms);
                } else if (ms.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    // 群发消息
                    // 需要遍历管理线程的集合，把所有的线程的socket得到，然后把ms进行转发
                    HashMap<String, SerConClieThread> hm = ManageClientThreads.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {
                        // 取出在线用户id
                        String onLineUserId = iterator.next();
                        if (!onLineUserId.equals(ms.getSender())) { // 排除群发消息的用户
                            // 进行转发
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(onLineUserId).getSk().getOutputStream());
                            oos.writeObject(ms);
                        }
                    }
                    // 更新服务器界面的信息
                    tankServerFrame.oneToAll(ms.getSender(), ms.getContent());
                } else if (ms.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    // 将这个客户端对应线程，从集合删除
                    ManageClientThreads.removeServerConnectClientThread(ms.getSender());
                    sk.close();
                    // 并更新服务器界面的信息
                    tankServerFrame.logOut(ms.getSender());
                    break;
                } else {
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
