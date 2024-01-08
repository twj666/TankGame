package com.joker.tank.game.strategy.tankai.impl;

import com.joker.tank.game.enums.Dir;
import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.map.*;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.strategy.tankai.TankAiStrategy;

import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * @author 燧枫
 * @date 2022/12/15 8:50
*/
public class DefaultTankAiStrategy implements TankAiStrategy {

    SecureRandom random = new SecureRandom();

    @Override
    public void tankAiFire(GameModel gm, Tank tank) {
        // 坦克正前方发现目标就直接开火
        Rectangle rectangle = null;
        if (tank.getFireStrategies().size() == 0) return;
        int bulletWidth = tank.getFireStrategies().get(tank.getNowFireStrategy()).getBulletWidth();
        // 取敌方坦克位置
        int eX = tank.getX(), eY = tank.getY();
        switch (tank.getDir()) {
            case UP:
                rectangle = new Rectangle(eX - bulletWidth / 2, eY - 1000, bulletWidth, 1000);
                break;
            case DOWN:
                rectangle = new Rectangle(eX - bulletWidth / 2, eY, bulletWidth, 1000);
                break;
            case LEFT:
                rectangle = new Rectangle(eX - 1500, eY - bulletWidth / 2, 1500, bulletWidth);
                break;
            case RIGHT:
                rectangle = new Rectangle(eX, eY - bulletWidth / 2, 1500, bulletWidth);
                break;
        }
        // 如果敌方坦克正对着主坦克
        if (rectangle.intersects(gm.getMyTank().getRectangle())) {
            // 通过判断两者之间是否有steel
            boolean isHaveSteel = false;
            ArrayList<Steel> steelList = new ArrayList<>();
            for (GameObject gameObject : gm.gameObjects) {
                if (gameObject instanceof Steel) steelList.add((Steel) gameObject);
            }
            // 取得主坦克的位置
            int mX = gm.getMyTank().getX(), mY = gm.getMyTank().getY();
            switch (tank.getDir()) {
                case UP:
                    rectangle = new Rectangle(eX - bulletWidth / 2, mY, bulletWidth, eY - mY);
                    break;
                case DOWN:
                    rectangle = new Rectangle(eX - bulletWidth / 2, eY, bulletWidth, mY - eY);
                    break;
                case LEFT:
                    rectangle = new Rectangle(mX, eY - bulletWidth / 2, eX - mX, bulletWidth);
                    break;
                case RIGHT:
                    rectangle = new Rectangle(eX, eY - bulletWidth / 2, mX - eX, bulletWidth);
                    break;
            }
            for (Steel steel : steelList) {
                if (rectangle.intersects(steel.getRectangle())){
                    isHaveSteel = true;
                }
            }
            // 没有钢板，停下来开火
            if (!isHaveSteel) {
                tank.fire();
                tank.setMoving(false);
                tank.changeFireStrategy();
            }
        } else {
            if (random.nextInt(1000) > 998) tank.fire();
        }
    }

    @Override
    public void tankAiMove(GameModel gm, Tank tank) {
        tank.setMoving(true);
        // 先随机移动坦克
        tank.setMoving(true);
        tank.randomDir();
        // 如果坦克已经没有子弹了，就不用对着主坦克了
        if (tank.getFireStrategies().size() == 0) return;
        // 与坦克形成十字架就对着主坦克
        int bulletWidth = tank.getFireStrategies().get(tank.getNowFireStrategy()).getBulletWidth();
        Rectangle rectangle = null;
        Rectangle myRectange = gm.getMyTank().getRectangle();
        rectangle = new Rectangle(tank.getX() - bulletWidth / 2, tank.getY() - 1000, bulletWidth, 1000);
        if (rectangle.intersects(myRectange)) {
            tank.changeDir(Dir.UP);
            return;
        }
        rectangle = new Rectangle(tank.getX() - bulletWidth / 2, tank.getY(), bulletWidth, 1000);
        if (rectangle.intersects(myRectange)) {
            tank.changeDir(Dir.DOWN);
            return;
        }
        rectangle = new Rectangle(tank.getX() - 1500, tank.getY() - bulletWidth / 2, 1500, bulletWidth);
        if (rectangle.intersects(myRectange)) {
            tank.changeDir(Dir.LEFT);
            return;
        }
        rectangle = new Rectangle(tank.getX(), tank.getY() - bulletWidth / 2, 1500, bulletWidth);
        if (rectangle.intersects(myRectange)) {
            tank.changeDir(Dir.RIGHT);
            return;
        }
    }

    @Override
    public void tankAiChangeFire(GameModel gm, Tank tank) {
        if (random.nextInt(1000) > 990) tank.changeFireStrategy();
    }
}
