package com.joker.tank.screen;

import com.joker.tank.backend._frame.ResultMessage;
import com.joker.tank.backend.controller.UserController;
import com.joker.tank.backend.entity.dos.Log;
import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;
import com.joker.tank.netsystem.client.ClientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author 燧枫
 * @date 2022/12/29 16:42
*/
// 社交大厅界面
public class MakeFriend extends JFrame {

    private UserController userController = new UserController();

    private User user;
    private TankFrame tf;

    private int opsNum = 0;
    private int opsMax = 2;

    private ArrayList<User> userList;

    // 消息集合
    private ArrayList<String> logs = new ArrayList<>();

    // 用户列表更新频率
    private int updateOnlineUserCnt = 1;

    public MakeFriend(TankFrame tf) {
        this.tf = tf;
        this.user = tf.getUser();
        // 初始化信息
        init();
    }

    // 初始化全部用户到用户列表中
    private void init() {
        // 加载用户列表
        Object result = userController.getAllUser().getResult();
        userList = (ArrayList<User>) result;
        changeOnlineStates(user.getUsername());
    }

    // 查看用户是否在线
    private boolean isOnline(String username) {
        for (int i = 0; i < userList.size(); i++) {
            User temp = userList.get(i);
            if (temp.getUsername().equals(username)) {
                return temp.getDeleteFlag() == 1 ? true : false;
            }
        }
        return false;
    }

    // 改变界面中用户在线状态
    private void changeOnlineStates(String username) {
        for (int i = 0; i < userList.size(); i++) {
            User temp = userList.get(i);
            if (temp.getUsername().equals(username)) {
                temp.setDeleteFlag(temp.getDeleteFlag() == 0 ? 1 : 0);
            }
        }
    }

    // 打印页面
    public void paint(Graphics2D g) {
        ImageUtil.printImage(g, ResourceMgr.menu_user, 750, 420);

        g.setColor(new Color(165, 187, 197));
        g.setFont(new Font("楷体", Font.BOLD, 15));
        for (int i = 0; i < logs.size(); i++) {
            g.drawString(logs.get(i), 100, 140 + 30 * i);
        }

        updateOnlineUserCnt--;
        if (updateOnlineUserCnt == 0) {
            updateOnlineUserCnt = 100;
            tf.clientService.onlineList();
        }

        g.setColor(new Color(220, 162, 208));
        g.setFont(new Font("楷体", Font.BOLD, 40));
        g.drawString("用户列表", 1250, 100);
        g.drawString("我的消息", 100, 100);
        g.setFont(new Font("楷体", Font.BOLD, 20));

        for (int i = 0; i < userList.size(); i++) {
            g.setColor(new Color(0, 255, 255));
            User temp = userList.get(i);
            g.drawString(temp.getUsername(), 1260, 140 + i * 40);
            if (temp.getDeleteFlag() == 1) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.GRAY);
            }
            g.fillRect(1400, 122 + i * 40, 20, 20);
        }

        g.setColor(new Color(137, 142, 145));
        g.drawOval(50, 700, 60, 60);
        g.drawOval(150, 700, 60, 60);
        ImageUtil.printImage(g, ResourceMgr.font_back, 80, 730);
        ImageUtil.printImage(g, ResourceMgr.font_mes, 180, 730);
        ImageUtil.printImage(g, ResourceMgr.font_mess, 280, 730);

        g.setColor(new Color(0, 255, 255));
        g.drawOval(50 + opsNum * 100, 700, 60, 60);

    }

    // 点击了私聊按钮
    private void privateChat() {
        String username = "", content = "";

        username = JOptionPane.showInputDialog("你想对谁说：");
        if (!isOnline(username)) {
            JOptionPane.showMessageDialog(null, "此用户不存在或不在线，请重新输入", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            content = JOptionPane.showInputDialog("请输入你想对他说的话:");
        }
        tf.clientService.sendMessageToOne(user.getUsername(), username, content);
        logAdd("你对" + username + "说: " + content);
    }

    // 点击了群发按钮
    private void AllChat() {
        String content = "";
        content = JOptionPane.showInputDialog("请输入你想对所有人说的话:");
        tf.clientService.sendMessageToAll(user.getUsername(), content);
        logAdd("你对所有人说: " + content);
    }

    // 收到了消息
    public void logAdd(String content) {
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = formatter.format(calendar.getTime());
        content = "[" + dateStr + "]: " + content;
        logs.add(content);
    }

    // 更新在线用户列表
    public void updateOnlineState(String[] onlineUsers) {
        for (int i = 0; i < onlineUsers.length; i++) {
            if (!isOnline(onlineUsers[i])) {
                changeOnlineStates(onlineUsers[i]);
            }
        }
    }

    // 按下键
    public void keyPressed(int key) {
        switch (key) {
            case KeyEvent.VK_LEFT:
                choiceLeft();
                break;
            case KeyEvent.VK_RIGHT:
                choiceRight();
                break;
            case KeyEvent.VK_ENTER:
                choiceMakeSure();
                break;
            default:
                break;
        }
    }

    // 做选择
    public void choiceMakeSure() {
        switch (opsNum) {
            case 0 :
                tf.changeNowFrame(1);
                break;
            case 1 :
                privateChat();
                break;
            case 2 :
                AllChat();
                break;
            default:
                break;
        }
    }

    // 按左箭头
    public void choiceLeft() {
        if (opsNum == 0) {
            opsNum = opsMax;
        } else {
            opsNum--;
        }
    }

    // 按右箭头
    public void choiceRight() {
        if (opsNum == opsMax) {
            opsNum = 0;
        } else {
            opsNum++;
        }
    }
}
