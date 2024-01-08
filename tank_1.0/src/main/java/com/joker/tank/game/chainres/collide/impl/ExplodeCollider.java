package com.joker.tank.game.chainres.collide.impl;

import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.bullet.Bullet;
import com.joker.tank.game.gameobject.bullet.special.BlueBeamBullet;
import com.joker.tank.game.gameobject.bullet.special.GreenBeamBullet;
import com.joker.tank.game.gameobject.bullet.special.RedBeamBullet;
import com.joker.tank.game.gameobject.explode.Explode;
import com.joker.tank.game.gameobject.map.Wall;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.manager.PropertyMgr;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author 燧枫
 * @date 2022/12/13 14:50
 */
public class ExplodeCollider {

    private static final ExplodeCollider INSTANCE = new ExplodeCollider();

    public static ExplodeCollider getInstance() {
        return INSTANCE;
    }

    public void explodeCollider(GameObject o1, ArrayList<GameObject> gameObject) {
        if (o1 instanceof Bullet) {
            Bullet b = (Bullet) o1;
            Explode e = b.getExplode();
            Rectangle rectangle = e.getRectangle();
            GameObject o2;

            for (int i = 0; i < gameObject.size(); i++) {
                o2 = gameObject.get(i);
                // 爆炸与墙壁
                if (o2 instanceof Wall) {
                    Wall w = (Wall) o2;
                    if (rectangle.intersects(w.getRectangle())) {
                        w.setHealth(w.getHealth() - b.getDamage());
                        if (w.getHealth() <= 0) {
                            w.die();
                        }
                    }
                }
                // 爆炸与坦克
                if (o2 instanceof Tank) {
                    Tank t = (Tank) o2;
                    if (rectangle.intersects(t.getRectangle())) {
                        if (t.getGroup() == b.getGroup()) continue;
                        // 如果是蓝色光束的爆炸，就设置坦克的停止计数
                        if (b instanceof BlueBeamBullet) {
                            t.setMovingCnt(PropertyMgr.getInt("blueBeamMovingCnt"));
                        }
                        // 如果是绿色光束的爆炸，就设置坦克的中毒计数
                        if (b instanceof GreenBeamBullet) {
                            t.setPoisonCnt(PropertyMgr.getInt("greenBeamPoisonCnt"));
                        }
                        // 如果是红色光束的爆炸，就设置坦克的破甲计数
                        if (b instanceof RedBeamBullet) {
                            t.setPeneCnt(PropertyMgr.getInt("redBeamPeneCnt"));
                        }
                        t.getHurt(b.getDamage(), b.getPene());
                    }
                }
            }
        }
    }

}
