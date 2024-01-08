package com.joker.tank.game.chainres.collide.impl;

import com.joker.tank.game.chainres.collide.Collider;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.bullet.Bullet;
import com.joker.tank.game.gameobject.tank.Tank;

import java.util.ArrayList;

/**
* @author 燧枫
* @date 2022/12/13 14:50
*/
public class BulletTankCollider implements Collider {

    ArrayList<GameObject> gameObjects = null;

    public BulletTankCollider(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet b = (Bullet) o1;
            if (b.isLiving()) {
                Tank t = (Tank) o2;
                if (b.getGroup() != t.getGroup()) {
                    if (b.getRectangle().intersects(t.getRectangle())) {
                        b.die();
                        ExplodeCollider.getInstance().explodeCollider(b, gameObjects);
                    }
                }
            }
            return false;
        } else if (o1 instanceof Tank && o2 instanceof Bullet) {
            return collide(o2, o1);
        }
        return true;
    }
}
