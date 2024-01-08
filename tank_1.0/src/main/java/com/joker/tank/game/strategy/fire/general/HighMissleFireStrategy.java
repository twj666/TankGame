package com.joker.tank.game.strategy.fire.general;

import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.gameobject.bullet.general.HighMissileBullet;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.strategy.fire.FireStrategy;
import com.joker.tank.game.utils.ImageUtil;

import java.awt.*;

/**
 * @author 燧枫
 * @date 2022/12/13 14:54
*/
public class HighMissleFireStrategy implements FireStrategy {

    private int bulletNums;

    public HighMissleFireStrategy(int bulletNums) {
        this.bulletNums = bulletNums;
    }

    @Override
    public boolean addBulletNum(FireStrategy fireStrategy) {
        if (fireStrategy instanceof HighMissleFireStrategy) {
            this.bulletNums += ((HighMissleFireStrategy) fireStrategy).getBulletNums();
            return true;
        }
        return false;
    }

    @Override
    public void fire(Tank tank) {
        switch (tank.getDir()) {
            case LEFT:
                new HighMissileBullet(tank, ResourceMgr.bullet_4_L);
                break;
            case UP:
                new HighMissileBullet(tank, ResourceMgr.bullet_4_U);
                break;
            case RIGHT:
                new HighMissileBullet(tank, ResourceMgr.bullet_4_R);
                break;
            case DOWN:
                new HighMissileBullet(tank, ResourceMgr.bullet_4_D);
                break;
            default:
                break;
        }
        subBullet(tank);
    }

    @Override
    public void paintFireStrategy(Graphics2D g, Rectangle rectangle) {
        ImageUtil.printImage(g, ResourceMgr.bullet_4_X, rectangle.x + 10 , rectangle.y + rectangle.height + 8);
        g.setColor(Color.WHITE);
        g.setFont(new Font("宋体", Font.PLAIN, 12));
        g.drawString(bulletNums + "", rectangle.x + 20, rectangle.y + rectangle.height + 12);
    }

    @Override
    public int getBulletWidth() {
        return ResourceMgr.bullet_4_U.getWidth();
    }

    private void subBullet(Tank tank) {
        if (--bulletNums == 0) {
            tank.subFireStrategy(this);
        }
    }

    public int getBulletNums() {
        return bulletNums;
    }
}
