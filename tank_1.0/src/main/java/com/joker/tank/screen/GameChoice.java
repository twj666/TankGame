package com.joker.tank.screen;

import com.joker.tank.game.enums.Group;
import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.gameobject.tank.general.FlyTank;
import com.joker.tank.game.gameobject.tank.general.GreenTank;
import com.joker.tank.game.gameobject.tank.general.HeavyTank;
import com.joker.tank.game.gameobject.tank.general.RedTank;
import com.joker.tank.game.gameobject.tank.special.CrabTank;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.strategy.flush.impl.DefaultFlashStrategy;
import com.joker.tank.game.utils.ImageUtil;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author 燧枫
 * @date 2022/12/30 14:01
 */
public class GameChoice {

    private GameModel gm;

    private int layerNum = 0;
    private int layerMax = 1;

    private int tankOpsNum = 0;
    private int tankOpsMax = 4;

    private int mapOpsNum = 0;
    private int mapOpsMax = 2;

    private boolean startFlag = false;

    TankFrame tf;

    public GameChoice(TankFrame tf) {
        this.tf = tf;
    }

    public void paint(Graphics2D g) {

        if (startFlag == true) {
            gm.paint(g);
            return;
        }

        ImageUtil.printImage(g, ResourceMgr.bg, 750, 410);

        g.setFont(new Font("楷体", Font.BOLD, 40));
        if (layerNum == 0) {
            g.setColor(new Color(162, 213, 220));
            g.drawString("坦克选择", 670, 150);
            g.setColor(new Color(83, 91, 91));
            g.drawString("地图选择", 670, 400);
        } else {
            g.setColor(new Color(83, 91, 91));
            g.drawString("坦克选择", 670, 150);
            g.setColor(new Color(162, 213, 220));
            g.drawString("地图选择", 670, 400);
        }


        g.setColor(new Color(83, 91, 91));
        g.fillOval(300, 200, 100, 100);
        ImageUtil.printImage(g, ResourceMgr.tank_1_U, 350, 250);
        g.fillOval(500, 200, 100, 100);
        ImageUtil.printImage(g, ResourceMgr.tank_2_U, 550, 250);
        g.fillOval(700, 200, 100, 100);
        ImageUtil.printImage(g, ResourceMgr.tank_3_U, 750, 250);
        g.fillOval(900, 200, 100, 100);
        ImageUtil.printImage(g, ResourceMgr.tank_4_U, 950, 250);
        g.fillOval(1100, 200, 100, 100);
        ImageUtil.printImage(g, ResourceMgr.tank_5_U, 1150, 250);
        g.setColor(new Color(0, 255, 255));
        g.drawOval(300 + 200 * tankOpsNum, 200, 100, 100);

        ImageUtil.printImage(g, ResourceMgr.map1, 300, 600);
        ImageUtil.printImage(g, ResourceMgr.map2, 750, 600);
        ImageUtil.printImage(g, ResourceMgr.map3, 1200, 600);

        g.drawRoundRect(146 + mapOpsNum * 450, 503, 307, 192, 0, 0);
    }

    // 按下键
    public void keyPressed(int key) {
        if (startFlag == true) {
            gm.keyPressed(key);
            return;
        }
        switch (key) {
            case KeyEvent.VK_LEFT:
                choiceLeft();
                break;
            case KeyEvent.VK_RIGHT:
                choiceRight();
                break;
            case KeyEvent.VK_UP:
                choiceUp();
                break;
            case KeyEvent.VK_DOWN:
                choiceDown();
                break;
            case KeyEvent.VK_ENTER:
                startGame();
                break;
            default:

                break;
        }
    }

    // 开始游戏
    private void startGame() {
        gm = new GameModel(tankOpsNum, mapOpsNum, new DefaultFlashStrategy(), tf);
        startFlag = true;
    }

    // 做选择
    public void choiceMakeSure() {
        switch (tankOpsNum) {
            case 0:
                tf.changeNowFrame(1);
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    // 按左箭头
    public void choiceLeft() {
        if (layerNum == 0) {
            if (tankOpsNum == 0) {
                tankOpsNum = tankOpsMax;
            } else {
                tankOpsNum--;
            }
        } else {
            if (mapOpsNum == 0) {
                mapOpsNum = mapOpsMax;
            } else {
                mapOpsNum--;
            }
        }

    }

    // 按右箭头
    public void choiceRight() {
        if (layerNum == 0) {
            if (tankOpsNum == tankOpsMax) {
                tankOpsNum = 0;
            } else {
                tankOpsNum++;
            }
        } else {
            if (mapOpsNum == mapOpsMax) {
                mapOpsNum = 0;
            } else {
                mapOpsNum++;
            }
        }
    }

    // 按上键
    public void choiceUp() {
        if (layerNum == 0) {
            layerNum = layerMax;
        } else {
            layerNum--;
        }
    }

    // 按下键
    public void choiceDown() {
        if (layerNum == layerMax) {
            layerNum = 0;
        } else {
            layerNum++;
        }
    }

    public void keyReleased(int key) {
        if (startFlag == true) {
            gm.keyReleased(key);
        }
    }
}
