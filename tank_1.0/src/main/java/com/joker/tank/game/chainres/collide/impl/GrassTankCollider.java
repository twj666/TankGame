package com.joker.tank.game.chainres.collide.impl;

import com.joker.tank.game.chainres.collide.Collider;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.map.Grass;
import com.joker.tank.game.gameobject.tank.Tank;

/**
 * @author 燧枫
 * @date 2022/12/17 9:15
*/
public class GrassTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Grass && o2 instanceof Tank) {
            Grass g = (Grass) o1;
            Tank t = (Tank) o2;
            if (g.getRectangle().intersects(t.getRectangle())) {
                t.setStealth(true);
            }
            return false;
        } else if (o1 instanceof Tank && o2 instanceof Grass) {
            return collide(o2, o1);
        }
        return true;
    }
}
