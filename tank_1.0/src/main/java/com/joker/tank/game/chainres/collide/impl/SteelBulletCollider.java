package com.joker.tank.game.chainres.collide.impl;

import com.joker.tank.game.chainres.collide.Collider;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.bullet.Bullet;
import com.joker.tank.game.gameobject.map.Steel;

import java.util.ArrayList;

/**
* @author 燧枫
* @date 2022/12/13 14:51
*/
public class SteelBulletCollider implements Collider {

    ArrayList<GameObject> gameObjects = null;

    public SteelBulletCollider(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Steel) {
            Bullet b = (Bullet) o1;
            if (b.isLiving()) {
                Steel s = (Steel) o2;
                if (b.getRectangle().intersects(s.getRectangle())) {
                    b.die();
                    ExplodeCollider.getInstance().explodeCollider(b, gameObjects);
                }
            }
            return false;
        } else if (o1 instanceof Steel && o2 instanceof Bullet) {
            return collide(o2, o1);
        }
        return true;
    }
}
