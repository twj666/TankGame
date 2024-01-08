package com.joker.tank.netsystem.client;

import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.netsystem.Message;
import com.joker.tank.netsystem.MessageType;
import com.joker.tank.screen.TankFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

/**
 * @author 燧枫
 * @date 2022/12/24 20:17
*/
public class ClientService {

    private User u = new User();

    private Socket sk;

    private TankFrame tf;

    public ClientService(TankFrame tf) {
        this.tf = tf;
    }

    // 用户登录
    public boolean login(String username, String password) {
        boolean isExist = false;

        u.setUsername(username);
        u.setPassword(password);

        // 连接到服务器，发送u对象
        try {
            sk = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            // 得到ObjectOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(sk.getOutputStream());
            oos.writeObject(u);

            // 读取从服务器回复的Message对象
            ObjectInputStream ois = new ObjectInputStream(sk.getInputStream());
            Message ms = (Message) ois.readObject();

            // 登录成功
            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                isExist = true;
                // 创建一个和服务器端保持通信的线程 -> 创建一个类 CliConSerThread
                CliConSerThread ccst = new CliConSerThread(sk, tf);
                // 启动客户端的线程
                ccst.start();
                // 这里为了后面客户端的扩展，我们将线程放入到集合管理
                ManageClientConnectServerThread.addClientConnectServerThread(username, ccst);
            } else {
                // 如果登录失败，我们就不能启动和服务器通信的线程，关闭socket
                sk.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return isExist;
    }


    // 退出客户端，并给服务端发送一个退出系统的message对象
    public void logout() {
        Message ms = new Message();
        ms.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        // 一定要指定是哪一个
        ms.setSender(u.getUsername());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(sk.getOutputStream());
            oos.writeObject(ms);
            System.out.println(u.getUsername() + "退出系统");
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 私聊，发送消息给某个用户
     *
     * @param sendId   发送者Id
     * @param getterId 接收者Id
     * @param content  内容
     */
    public void sendMessageToOne(String sender, String getter, String content) {
        Message ms = new Message();
        ms.setMesType(MessageType.MESSAGE_COMM_MES);
        ms.setSender(sender);
        ms.setGetter(getter);
        ms.setContent(content);
        ms.setSendTime(new Date().toString());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnetServerThread(sender).getSk().getOutputStream());
            oos.writeObject(ms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 向服务器端请求在线用户列表
    public void onlineList() {
        // 发送Message，类型MESSAGE_GET_ONLINELIST
        Message ms = new Message();
        ms.setMesType(MessageType.MESSAGE_GET_ONLINELIST);
        // 发送给服务器
        // 应该得到当前线程的Socket 对应的ObjectOutputStream对象
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnetServerThread(u.getUsername()).getSk().getOutputStream());
            oos.writeObject(ms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 群发消息
     * @param senderId 发送者Id
     * @param content 内容
     */
    public void sendMessageToAll(String sender, String content) {
        Message ms = new Message();
        ms.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        ms.setSender(sender);
        ms.setContent(content);
        ms.setSendTime(new Date().toString());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnetServerThread(sender).getSk().getOutputStream());
            oos.writeObject(ms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
