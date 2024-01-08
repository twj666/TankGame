package com.joker.tank.game.chainres.collide.impl;

import com.joker.tank.game.chainres.collide.Collider;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.map.Wall;
import com.joker.tank.game.gameobject.tank.Tank;

/**
* @author 燧枫
* @date 2022/12/13 14:51
*/
public class WallTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Wall && o2 instanceof Tank) {
            Wall w = (Wall) o1;
            Tank t = (Tank) o2;
            if (w.getRectangle().intersects(t.getRectangle())) {
                t.back();
            }
            return false;
        } else if (o1 instanceof Tank && o2 instanceof Wall) {
            return collide(o2, o1);
        }
        return true;
    }
}
