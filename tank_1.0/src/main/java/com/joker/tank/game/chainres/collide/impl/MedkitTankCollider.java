package com.joker.tank.game.chainres.collide.impl;

import com.joker.tank.game.chainres.collide.Collider;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.map.Medkit;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.manager.PropertyMgr;

/**
* @author 燧枫
* @date 2022/12/13 14:51
*/
public class MedkitTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Medkit && o2 instanceof Tank) {
            Medkit m = (Medkit) o1;
            Tank t = (Tank) o2;
            if (m.getRectangle().intersects(t.getRectangle())) {
                if (t.getHealth() < t.getMaxHealth()) {
                    int willAdd = t.getMaxHealth() - t.getHealth();
                    if (willAdd > PropertyMgr.getInt("medkitHealth")) willAdd = PropertyMgr.getInt("medkitHealth");
                    t.setHealth(t.getHealth() + willAdd);
                    t.addHealthChange(willAdd);
                    m.die();
                }
            }
            return false;
        } else if (o1 instanceof Tank && o2 instanceof Medkit) {
            return collide(o2, o1);
        }
        return true;
    }
}
