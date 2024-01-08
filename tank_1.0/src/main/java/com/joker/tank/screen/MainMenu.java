package com.joker.tank.screen;

import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;
import com.joker.tank.netsystem.client.ClientService;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author 燧枫
 * @date 2022/12/21 19:53
*/
// 主菜单界面
public class MainMenu {

    private int nowChoice = 0;
    private int choiceMax = 3;

    private TankFrame tf;
    private User user;

    public MainMenu(TankFrame tf) {
        this.tf = tf;
        this.user = tf.getUser();
    }

    // 打印页面
    public void paint(Graphics2D g) {

        ImageUtil.printImage(g, ResourceMgr.menu_main, 750, 410);
        g.setColor(new Color(200, 200, 200));
        g.fillRoundRect(150, 200, 200, 60, 20, 20);
        g.fillRoundRect(150, 300, 200, 60, 20, 20);
        g.fillRoundRect(150, 400, 200, 60, 20, 20);
        g.fillRoundRect(150, 500, 200, 60, 20, 20);

        ImageUtil.printImage(g, ResourceMgr.font_single, 250, 230);
        ImageUtil.printImage(g, ResourceMgr.font_friend, 250, 330);
        ImageUtil.printImage(g, ResourceMgr.font_user, 250, 430);
        ImageUtil.printImage(g, ResourceMgr.font_exit, 250, 530);

        g.setColor(new Color(0, 255, 255));
        g.drawRoundRect(150, 200 + nowChoice * 100, 200, 60, 20, 20);
        ImageUtil.printImage(g, ResourceMgr.tank_4_R, 100, 230 + nowChoice * 100);

        g.setColor(new Color(168, 176, 176));
        g.setFont(new Font("楷体", Font.PLAIN, 30));
        g.drawString("当前用户: " + user.getUsername(), 20, 70);
    }

    // 按键
    public void keyPressed(int key) {
        switch (key) {
            case KeyEvent.VK_UP:
                choiceUp();
                break;
            case KeyEvent.VK_DOWN:
                choiceDown();
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
        switch (nowChoice) {
            case 0 :
                tf.changeNowFrame(2);
                break;
            case 1 :
                tf.changeNowFrame(3);
                break;
            case 2 :
                tf.changeNowFrame(4);
                break;
            case 3 :
                tf.clientService.logout();
                System.exit(0);
                break;
            default:
                break;
        }
    }

    // 按上键
    public void choiceUp() {
        if (nowChoice == 0) {
            nowChoice = choiceMax;
        } else {
            nowChoice--;
        }
    }

    // 按下键
    public void choiceDown() {
        if (nowChoice == choiceMax) {
            nowChoice = 0;
        } else {
            nowChoice++;
        }
    }

}
