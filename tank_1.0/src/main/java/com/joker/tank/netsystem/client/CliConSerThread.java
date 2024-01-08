package com.joker.tank.netsystem.client;

import com.joker.tank.netsystem.Message;
import com.joker.tank.netsystem.MessageType;
import com.joker.tank.screen.TankFrame;
import lombok.Data;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author 燧枫
 * @date 2022/12/24 20:28
 */
@Data
public class CliConSerThread extends Thread {

    // 该线程需要持有Socket
    private Socket sk;

    private TankFrame tf;

    // 构造器接收一个Socket对象
    public CliConSerThread(Socket sk, TankFrame tf) {
        this.sk = sk;
        this.tf = tf;
    }

    @Override
    public void run() {
        // 因为Thread需要在后台和服务器通信，因此我们while循环
        while (true) {

            try {
                ObjectInputStream ois = new ObjectInputStream(sk.getInputStream());
                // 如果服务器没有发送Message对象，线程会阻塞在这里
                Message ms = (Message) ois.readObject();
                // 注意，后面我们需要去使用message
                //判断这个message类型，然后做相应的业务处理

                // 如果是读取到的是服务端返回的在线用户列表
                if (ms.getMesType().equals(MessageType.MESSAGE_RET_ONLINELIST)) {
                    String[] onlineUsers = ms.getContent().split(" ");
                    // 更新社交大厅的在线用户列表
                    tf.getMf().updateOnlineState(onlineUsers);
                } else if (ms.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    String content = ms.getSender() + "对你说: " + ms.getContent();
                    tf.getMf().logAdd(content);
                } else if (ms.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // 是一个普通的聊天消息
                    String content = ms.getSender() + "对你说: " + ms.getContent();
                    tf.getMf().logAdd(content);
                } else {
                    System.out.println("是其他类型的message，暂时不处理");
                }

            } catch (Exception e) {
            }
        }
    }
}
