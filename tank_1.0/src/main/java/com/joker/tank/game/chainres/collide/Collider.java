package com.joker.tank.game.chainres.collide;

import com.joker.tank.game.gameobject.GameObject;

/**
* @author 燧枫
* @date 2022/12/13 14:50
*/
public interface Collider {

    boolean collide(GameObject o1, GameObject o2);
}
