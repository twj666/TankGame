package com.joker.tank.screen;

import com.joker.tank.backend.controller.LogController;
import com.joker.tank.backend.controller.UserController;
import com.joker.tank.backend.entity.dos.Log;
import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.netsystem.server.TankServer;
import lombok.Data;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author 燧枫
 * @date 2022/12/29 0:06
*/
// 服务器界面
public class TankServerFrame extends JFrame {

    TankServer tankServer;

    private UserController userController = new UserController();

    private LogController logController = new LogController();

    private JButton startButton;
    private JTextField textField;
    private JLabel label;
    private JLabel statusLabel;
    private JTextArea logArea;
    private JList userList;
    private JTextField inputField;
    private JButton inputButton;
    private JLabel onlineCountLabel;

    // 所有用户集合
    ArrayList<User> userInfoList;

    private boolean isRunning = false;

    public TankServerFrame() {
        startButton = new JButton("Start Server");
        textField = new JTextField(20);
        label = new JLabel("Enter your name:");
        statusLabel = new JLabel();
        logArea = new JTextArea();
        Font font = new Font("微软雅黑", Font.PLAIN, 15);
        logArea.setFont(font);

        // 初始化信息
        init();

        inputField = new JTextField(20);
        inputButton = new JButton("发布公告");
        onlineCountLabel = new JLabel();

        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(inputButton);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(startButton, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(userList), BorderLayout.WEST);
        contentPane.add(new JScrollPane(logArea), BorderLayout.CENTER);
        contentPane.add(statusLabel, BorderLayout.EAST);
        contentPane.add(inputPanel, BorderLayout.SOUTH);

        setTitle("TankServer | 玩家总数:" + userList.getModel().getSize() + " | 在线总数:" + getOnlineCount());
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        updateStatusLabel();

        ActionListener listener = new MyActionListener();
        startButton.addActionListener(listener);
        inputButton.addActionListener(listener);
    }

    // 初始化全部用户到用户列表中，并加载全部日志到界面上
    private void init() {
        // 加载用户列表
        Object result = userController.getAllUser().getResult();
        userInfoList = (ArrayList<User>) result;
        UserT[] users = new UserT[userInfoList.size()];
        for (int i = 0; i < userInfoList.size(); i++) {
            users[i] = new UserT(userInfoList.get(i).getUsername(), false);
        }
        userList = new JList(users);
        userList.setFixedCellHeight(40);
        userList.setCellRenderer(new UserListCellRenderer());

        // 加载日志
        ArrayList<Log> logList = (ArrayList<Log>)logController.getAllLog().getResult();
        for (Log log : logList) {
            logArea.append(log.getContent() + "\n");
        }
    }

    // 启动/关闭服务器界面显示
    private void updateStatusLabel() {
        int size = 20;
        statusLabel.setPreferredSize(new Dimension(size, size));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(isRunning ? Color.GREEN : Color.RED);
        startButton.setText(isRunning ? "关闭服务器" : "启动服务器");
    }

    // 启动服务器
    private void startServer() {
        tankServer = new TankServer(this);
        new Thread(tankServer).start();
    }

    // 顶部按钮与底部推送按钮
    private class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 点击启动/关闭服务器
            if (e.getSource() == startButton) {
                if (isRunning == false) {
                    startServer();
                }
                isRunning = !isRunning;
                updateStatusLabel();
                String log = isRunning ? "服务器已启动..." : "服务器已关闭...";
                appendLog(log);
                // 点击发送公告
            } else if (e.getSource() == inputButton) {
                String input = inputField.getText();
                if (input.length() == 0) return;
                tankServer.sendNewsToAllService.send(input);
                input = "服务器发布了一条公告，内容为: (" + input + ")";
                appendLog(input);
                inputField.setText("");
            }
        }
    }

    private class UserListCellRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBorder(new EmptyBorder(10, 10, 10, 10));
            UserT user = (UserT) value;
            JLabel label = new JLabel(user.getUsername());
            panel.add(label, BorderLayout.WEST);
            int size = 20;
            JLabel statusLabel = new JLabel();
            statusLabel.setPreferredSize(new Dimension(size, size));
            statusLabel.setOpaque(true);
            statusLabel.setBackground(user.isOnline() ? Color.GREEN : Color.GRAY);
            panel.add(statusLabel, BorderLayout.EAST);
            return panel;
        }
    }

    // 用户登录成功
    public void loginSuccess(String username) {
        for (int i = 0; i < userList.getModel().getSize(); i++) {
            UserT user = (UserT) userList.getModel().getElementAt(i);
            if (user.getUsername().equals(username)) {
                user.setOnline(!user.online);
                userList.repaint();
            }
        }
        setTitle("TankServer | 玩家总数:" + userList.getModel().getSize() + " | 在线总数:" + getOnlineCount());
        appendLog("用户<" + username + "> 登录成功");
    }

    // 某人对某人说了什么话
    public void oneToOne(String sender, String getter, String content) {
        appendLog(sender + "对" + getter + "说: " + content);
    }

    // 某人对大家说了什么话
    public void oneToAll(String sender, String content) {
        appendLog(sender + "对所有人说: " + content);
    }

    // 用户退出游戏
    public void logOut(String username) {
        for (int i = 0; i < userList.getModel().getSize(); i++) {
            UserT user = (UserT) userList.getModel().getElementAt(i);
            if (user.getUsername().equals(username)) {
                user.setOnline(!user.online);
                userList.repaint();
            }
        }
        setTitle("TankServer | 玩家总数:" + userList.getModel().getSize() + " | 在线总数:" + getOnlineCount());
        appendLog("用户<" + username + "> 退出游戏");
    }

    // 获取当前在线用户数量
    private int getOnlineCount() {
        int count = 0;
        for (int i = 0; i < userList.getModel().getSize(); i++) {
            UserT user = (UserT) userList.getModel().getElementAt(i);
            if (user.isOnline()) {
                count++;
            }
        }
        return count;
    }

    // 辅助User类
    @Data
    private class UserT {
        private String username;
        private boolean online;

        public UserT(String username, boolean online) {
            this.username = username;
            this.online = online;
        }
    }

    // 追加日志
    public void appendLog(String content) {
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = formatter.format(calendar.getTime());
        content = "[" + dateStr + "]: " + content;
        // 将此条日志添加至数据库
        logController.addOneLog(content);
        logArea.append(content + "\n");
    }
}