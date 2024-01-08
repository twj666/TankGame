package com.joker.tank.netsystem.server;

import com.joker.tank.backend._frame.ResultMessage;
import com.joker.tank.backend.controller.UserController;
import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.netsystem.Message;
import com.joker.tank.netsystem.MessageType;
import com.joker.tank.screen.TankServerFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 燧枫
 * @date 2022/12/24 21:00
*/
// 这是服务器，在监听9999，等待客户端的连接，并保持通信
public class TankServer extends Thread {

    UserController userController = new UserController();

    private ServerSocket ssk;

    private TankServerFrame tankServerFrame;

    public TankServer(TankServerFrame tankServerFrame) {
        this.tankServerFrame = tankServerFrame;
    }

    public SendNewsToAllService sendNewsToAllService;

    @Override
    public void run() {
        try {
            // 服务器推送消息线程
            sendNewsToAllService = new SendNewsToAllService();
            new Thread(sendNewsToAllService).start();
            ssk = new ServerSocket(9999);
            while (true) {
                Socket sk = ssk.accept();
                // 得到socket关联的对象输入流
                ObjectInputStream ois = new ObjectInputStream(sk.getInputStream());
                // 得到socket关联的对象输出流
                ObjectOutputStream oos = new ObjectOutputStream(sk.getOutputStream());
                // 读取客户端发送的User对象
                User u = (User) ois.readObject();
                Message ms = new Message();
                // 调用登录接口
                ResultMessage loginMsg = userController.login(u.getUsername(), u.getPassword());
                // 登录成功
                if (loginMsg.isSuccess() == true) {
                    ms.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(ms);
                    // 创建一个线程和客户端保存通信，该线程需要持有socket对象
                    SerConClieThread scct = new SerConClieThread(sk, u.getUsername(), tankServerFrame);
                    scct.start();
                    // 把该线程对象，放入到一个集合中，进行管理
                    ManageClientThreads.addClientThread(u.getUsername(), scct);
                    // 并更新服务器界面的信息
                    tankServerFrame.loginSuccess(u.getUsername());
                } else {
                    // 登录失败
                    System.out.println("用户 username=" + u.getUsername() + "登录失败");
                    ms.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(ms);
                    sk.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 如果服务端退出while循环，说明服务器端不在监听，因此关闭ServerSocket
            try {
                ssk.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
