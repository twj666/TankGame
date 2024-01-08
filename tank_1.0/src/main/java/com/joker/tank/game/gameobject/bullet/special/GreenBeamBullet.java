package com.joker.tank.game.gameobject.bullet.special;


import com.joker.tank.game.gameobject.bullet.Bullet;
import com.joker.tank.game.gameobject.explode.special.GreenBeamExplode;

import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.manager.PropertyMgr;

import java.awt.image.BufferedImage;

/**
 * @author 燧枫
 * @date 2022/12/17 9:16
*/
public class GreenBeamBullet extends Bullet {

    public GreenBeamBullet(Tank t, BufferedImage bufferedImage) {
        super(t, bufferedImage, t.getPene());
        this.speed = PropertyMgr.getInt("bulletSpeed_8");
        this.damage = PropertyMgr.getInt("bulletDamage_8");
    }

    @Override
    public void die() {
        this.living = false;
        this.explode = new GreenBeamExplode(this.x, this.y, gm);
        gm.add(explode);
    }
}
