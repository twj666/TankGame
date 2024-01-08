package com.joker.tank.game.chainres.collide.impl;

import com.joker.tank.game.chainres.collide.Collider;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.map.Crystal;
import com.joker.tank.game.gameobject.tank.Tank;

/**
 * @author 燧枫
 * @date 2022/12/17 9:14
*/
public class CrystalTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Crystal) {
            Tank t = (Tank) o1;
            Crystal c = (Crystal) o2;
            if (c.getRectangle().intersects(t.getRectangle())) {
               if (t.addFireStrategy(c.getFireStrategy())){
                   c.die();
               }
            }
            return false;
        } else if (o1 instanceof Crystal && o2 instanceof Tank) {
            return collide(o2, o1);
        }
        return true;
    }
}
