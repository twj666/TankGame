package com.joker.tank.game.gameobject.bullet.special;

import com.joker.tank.game.gameobject.bullet.Bullet;
import com.joker.tank.game.gameobject.explode.special.RedBeamExplode;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.manager.PropertyMgr;

import java.awt.image.BufferedImage;

/**
 * @author 燧枫
 * @date 2022/12/13 14:53
*/
public class RedBeamBullet extends Bullet {

    public RedBeamBullet(Tank t, BufferedImage bufferedImage) {
        super(t, bufferedImage, t.getPene());
        this.speed = PropertyMgr.getInt("bulletSpeed_7");
        this.damage = PropertyMgr.getInt("bulletDamage_7");
    }

    @Override
    public void die() {
        this.living = false;
        this.explode = new RedBeamExplode(this.x, this.y, gm);
        gm.add(explode);
    }
}
