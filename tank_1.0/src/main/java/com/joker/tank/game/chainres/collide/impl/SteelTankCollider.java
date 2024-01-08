package com.joker.tank.game.chainres.collide.impl;

import com.joker.tank.game.chainres.collide.Collider;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.map.Steel;
import com.joker.tank.game.gameobject.tank.Tank;

/**
* @author 燧枫
* @date 2022/12/13 14:51
*/
public class SteelTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Steel && o2 instanceof Tank) {
            Steel s = (Steel) o1;
            Tank t = (Tank) o2;
            if (s.getRectangle().intersects(t.getRectangle())) {
                t.back();
            }
            return false;
        } else if (o1 instanceof Tank && o2 instanceof Steel) {
            return collide(o2, o1);
        }
        return true;
    }
}
