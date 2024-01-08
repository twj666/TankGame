package com.joker.tank.screen;

import com.joker.tank.backend._frame.ResultMessage;
import com.joker.tank.backend.controller.UserController;
import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author 燧枫
 * @date 2022/12/22 10:05
 */
// 用户信息界面
public class UserInfo {

    UserController userController = new UserController();

    private User user;
    private TankFrame tf;

    private int opsNum = 0;
    private int opsMax = 1;

    // 购买钻石动画计数
    private int buyDaimondCnt = 0;
    // 购买是否成功
    private boolean buyDaimondSuccess = true;

    public UserInfo(TankFrame tf) {
        this.tf = tf;
        this.user = tf.getUser();
    }

    // 打印页面
    public void paint(Graphics2D g) {
        ImageUtil.printImage(g, ResourceMgr.menu_user, 750, 420);
        g.setColor(new Color(220, 162, 208));
        g.setFont(new Font("楷体", Font.BOLD, 40));
        g.drawString("用户ID: " + user.getId(), 50, 100);
        g.drawString("用户名: " + user.getUsername(), 50, 200);
        g.drawString("密码: " + user.getPassword(), 50, 300);
        g.drawString("性别: " + (user.getSex() == 1 ? "男" : "女"), 50, 400);
        g.drawString("对局总场次: " + user.getGameNum(), 50, 500);
        ImageUtil.printImage(g, ResourceMgr.daimond, 95, 585);
        g.drawString("    x ", 50, 595);
        g.drawString("      " + user.getDiamond(), 50, 600);

        g.setColor(new Color(137, 142, 145));
        g.drawOval(50, 700, 60, 60);
        g.drawOval(150, 700, 60, 60);
        ImageUtil.printImage(g, ResourceMgr.font_back, 80, 730);
        ImageUtil.printImage(g, ResourceMgr.font_add_daimond, 180, 730);

        g.setColor(new Color(0, 255, 255));
        g.drawOval(50 + opsNum * 100, 700, 60, 60);

        if (buyDaimondCnt != 0) {
            if (buyDaimondSuccess == true) {
                g.setColor(new Color(17, 168, 17));
                g.drawString("+10", 300, 600);
            } else {
                g.setColor(new Color(234, 11, 45));
                g.drawString("购买失败", 300, 600);
            }
            buyDaimondCnt--;
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
                buyDaimond();
                break;
            default:
                break;
        }
    }

    // 购买钻石
    public void buyDaimond() {
        buyDaimondCnt = 50;
        user.setDiamond(user.getDiamond() + 10);
        // 调用修改信息接口
        ResultMessage updateUserInfoMsg = userController.updateUserInfo(user);
        // 购买不成功
        if (updateUserInfoMsg.isSuccess() == false) {
            buyDaimondSuccess = false;
            // 回滚
            user.setDiamond(user.getDiamond() - 10);
            return;
        }
        // 购买成功
        buyDaimondSuccess = true;
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
