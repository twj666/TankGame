package com.joker.tank.game.gamemodel;

import com.joker.tank.backend._frame.ResultMessage;
import com.joker.tank.backend.controller.UserController;
import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.game.chainres.collide.ColliderChain;
import com.joker.tank.game.enums.Dir;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.enums.Group;
import com.joker.tank.game.gameobject.tank.general.FlyTank;
import com.joker.tank.game.gameobject.tank.general.GreenTank;
import com.joker.tank.game.gameobject.tank.general.HeavyTank;
import com.joker.tank.game.gameobject.tank.general.RedTank;
import com.joker.tank.game.gameobject.tank.special.CrabTank;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.gameobject.turret.Turret;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.strategy.flush.FlushStrategy;
import com.joker.tank.game.utils.GameMapUtil;
import com.joker.tank.game.utils.ImageUtil;
import com.joker.tank.screen.TankFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author 燧枫
 * @date 2022/12/13 14:52
 */
public class GameModel {

    UserController userController = new UserController();

    // 游戏暂停标志
    private boolean pauseFlag = false;

    private int opsNum = 0;
    private int opsMax = 1;

    private int play_pause_gameover = 0;

    private TankFrame tf;

    private User user;

    // 使用钻石动画计数
    private int useDaimondCnt = 0;
    // 是否使用成功
    private boolean useDaimondSuccess = true;

    // 主战坦克
    Tank myTank;

    // 水晶
    Turret turret = new Turret(650, 410, Group.GOOD);

    // 游戏物体集合
    public ArrayList<GameObject> gameObjects = new ArrayList<>();

    // 引入责任链
    ColliderChain colliderChain = new ColliderChain(gameObjects);

    // 引入刷新策略
    FlushStrategy flushStrategy = null;

    public GameModel(int tankOpsNum, int mapOpsNum, FlushStrategy flushStrategy, TankFrame tf) {

        int initX = 0;
        int initY = 0;

        switch (mapOpsNum) {
            case 0:
                GameMapUtil.creatGameMap(GameMapUtil.map1, this);
                initX = 700;
                initY = 410;
                break;
            case 1:
                GameMapUtil.creatGameMap(GameMapUtil.map2, this);
                initX = 650;
                initY = 750;
                break;
            case 2:
                GameMapUtil.creatGameMap(GameMapUtil.map3, this);
                initX = 650;
                initY = 450;
                break;
        }

        switch (tankOpsNum) {
            case 0:
                myTank = new RedTank(initX, initY, Group.GOOD, this);
                break;
            case 1:
                myTank = new GreenTank(initX, initY, Group.GOOD, this);
                break;
            case 2:
                myTank = new FlyTank(initX, initY, Group.GOOD, this);
                break;
            case 3:
                myTank = new HeavyTank(initX, initY, Group.GOOD, this);
                break;
            case 4:
                myTank = new CrabTank(initX, initY, Group.GOOD, this);
                break;
        }


        this.myTank = myTank;

        this.tf = tf;

        this.user = tf.getUser();

        myTank.setMoving(false);

        this.flushStrategy = flushStrategy;

        add(myTank);
    }

    // 添加物品到集合
    public void add(GameObject go) {
        this.gameObjects.add(go);
    }

    // 移除集合中的物品
    public void remove(GameObject go) {
        this.gameObjects.remove(go);
    }

    // 重画页面
    public void paint(Graphics2D g) {

        ImageUtil.printImage(g, ResourceMgr.bg, 750, 410);

        // 复活中，打印动画
        if (useDaimondCnt != 0) {
            printDied(g);
            g.setFont(new Font("楷体", Font.BOLD, 40));
            if (useDaimondSuccess == true) {
                g.setColor(new Color(17, 168, 17));
                g.drawString("-10", 905, 415);
            } else {
                g.setColor(new Color(234, 11, 45));
                g.drawString("复活失败", 905, 415);
            }
            useDaimondCnt--;
            if (useDaimondCnt == 0) {
                myTank = new CrabTank(500, 410, Group.GOOD, this);
                myTank.setMoving(false);
                add(myTank);
                pauseFlag = false;
                play_pause_gameover = 0;
            }
            return;
        }

        // 如果主坦克死了，游戏暂停，打印死亡游戏界面
        if (myTank.getHealth() <= 0) {
            pauseFlag = true;
            play_pause_gameover = 2;
            printDied(g);
            return;
        }

        if (pauseFlag == false) {
            // 重新打印
            for (int i = 0; i < gameObjects.size(); i++) {
                gameObjects.get(i).paint(g);
            }

            // 物体碰撞检测
            GameObject o1, o2;
            for (int i = 0; i < gameObjects.size(); i++) {
                o1 = gameObjects.get(i);
                for (int j = i + 1; j < gameObjects.size(); j++) {
                    o2 = gameObjects.get(j);
                    colliderChain.collide(o1, o2);
                }
            }

            // 刷新
            flushStrategy.flush(this);
        } else {
            // 打印暂停界面
            play_pause_gameover = 1;
            printPause(g);
        }
    }


    // 死亡界面
    private void printDied(Graphics2D g) {
        g.setColor(new Color(220, 162, 208));
        g.setFont(new Font("楷体", Font.BOLD, 40));
        g.drawString("用户名: " + user.getUsername(), 620, 350);
        ImageUtil.printImage(g, ResourceMgr.daimond, 700, 400);
        g.drawString("    x ", 655, 410);
        g.drawString("      " + user.getDiamond(), 655, 415);

        ImageUtil.printImage(g, ResourceMgr.gameover, 750, 250);
        g.setColor(new Color(137, 142, 145));
        g.drawOval(650, 500, 60, 60);
        g.drawOval(800, 500, 60, 60);
        ImageUtil.printImage(g, ResourceMgr.font_house, 680, 530);
        ImageUtil.printImage(g, ResourceMgr.font_revive, 830, 530);
        g.setColor(new Color(0, 255, 255));
        g.drawOval(650 + opsNum * 150, 500, 60, 60);
    }

    // 暂停界面
    private void printPause(Graphics2D g) {
        ImageUtil.printImage(g, ResourceMgr.pause, 750, 250);
        g.setColor(new Color(137, 142, 145));
        g.drawOval(650, 500, 60, 60);
        g.drawOval(800, 500, 60, 60);
        ImageUtil.printImage(g, ResourceMgr.font_pause, 680, 530);
        ImageUtil.printImage(g, ResourceMgr.font_house, 830, 530);
        g.setColor(new Color(0, 255, 255));
        g.drawOval(650 + opsNum * 150, 500, 60, 60);
    }

    private boolean bL = false;
    private boolean bU = false;
    private boolean bR = false;
    private boolean bD = false;

    // 按下键
    public void keyPressed(int key) {
        switch (key) {
            case KeyEvent.VK_A:
                bL = true;
                break;
            case KeyEvent.VK_W:
                bU = true;
                break;
            case KeyEvent.VK_D:
                bR = true;
                break;
            case KeyEvent.VK_S:
                bD = true;
                break;
            case KeyEvent.VK_SPACE:
                myTank.setSpeed(myTank.getSrcSpeed() * 2);
                break;
            case KeyEvent.VK_ESCAPE:
                pauseFlag = pauseFlag == true ? false : true;
                play_pause_gameover = play_pause_gameover == 0 ? 1 : 0;
                break;
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
        setMainTankDir();
    }

    // 松开键
    public void keyReleased(int key) {

        switch (key) {
            case KeyEvent.VK_A:
                bL = false;
                break;
            case KeyEvent.VK_W:
                bU = false;
                break;
            case KeyEvent.VK_D:
                bR = false;
                break;
            case KeyEvent.VK_S:
                bD = false;
                break;
            case KeyEvent.VK_J:
                myTank.fire();
                break;
            case KeyEvent.VK_U:
                myTank.changeFireStrategy();
                break;
            case KeyEvent.VK_SPACE:
                myTank.setSpeed(myTank.getSrcSpeed());
                break;
            default:
                break;
        }
        setMainTankDir();
    }

    // 使用钻石复活
    public void revive() {
        useDaimondCnt = 50;
        // 如果砖石不够，显示失败，并返回
        if (user.getDiamond() < 10) {
            useDaimondSuccess = false;
            return;
        }
        user.setDiamond(user.getDiamond() - 10);
        // 调用修改信息接口
        ResultMessage updateUserInfoMsg = userController.updateUserInfo(user);
        // 复活不成功
        if (updateUserInfoMsg.isSuccess() == false) {
            useDaimondSuccess = false;
            // 回滚
            user.setDiamond(user.getDiamond() + 10);
            return;
        }
        // 复活成功
        useDaimondSuccess = true;
    }

    // 做选择
    public void choiceMakeSure() {
        // 游戏结束时做的选择
        if (play_pause_gameover == 2) {
            switch (opsNum) {
                case 0 :
                    tf.changeNowFrame(1);
                    break;
                case 1 :
                    revive();
                    break;
                default:
                    break;
            }
            // 游戏暂停时做的选择
        } else if (play_pause_gameover == 1) {
            switch (opsNum) {
                case 0 :
                    pauseFlag = false;
                    play_pause_gameover = 0;
                    break;
                case 1 :
                    tf.changeNowFrame(1);
                    break;
                default:
                    break;
            }
        }
    }

    // 按左箭头
    public void choiceLeft() {
        if (play_pause_gameover == 2 || play_pause_gameover == 1) {
            if (opsNum == 0) {
                opsNum = opsMax;
            } else {
                opsNum--;
            }
        }
    }

    // 按右箭头
    public void choiceRight() {
        if (play_pause_gameover == 2 || play_pause_gameover == 1) {
            if (opsNum == opsMax) {
                opsNum = 0;
            } else {
                opsNum++;
            }
        }
    }

    // 设置主坦克移动
    private void setMainTankDir() {
        if (!bL && !bU && !bR && !bD) {
            myTank.setMoving(false);
        } else {
            myTank.setMoving(true);
            if (bL) myTank.changeDir(Dir.LEFT);
            if (bU) myTank.changeDir(Dir.UP);
            if (bR) myTank.changeDir(Dir.RIGHT);
            if (bD) myTank.changeDir(Dir.DOWN);
        }
    }

    public Tank getMyTank() {
        return myTank;
    }

}
