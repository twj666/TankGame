package com.joker.tank.game.strategy.fire;

import com.joker.tank.game.gameobject.tank.Tank;

import java.awt.*;

/**
 * @author 燧枫
 * @date 2022/12/17 9:17
*/
public interface FireStrategy {

    void fire(Tank tank);

    boolean addBulletNum(FireStrategy fireStrategy);

    void paintFireStrategy(Graphics2D g, Rectangle rectangle);

    int getBulletWidth();
}
