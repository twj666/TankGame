package com.joker.tank.game.gameobject.bullet.general;


import com.joker.tank.game.gameobject.bullet.Bullet;
import com.joker.tank.game.gameobject.explode.general.NuclearExplode;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.manager.PropertyMgr;


import java.awt.image.BufferedImage;

/**
 * @author 燧枫
 * @date 2022/12/13 14:53
*/
public class NuclearBullet extends Bullet {

    public NuclearBullet(Tank t, BufferedImage bufferedImage) {
        super(t, bufferedImage, t.getPene());
        this.speed = PropertyMgr.getInt("bulletSpeed_5");
        this.damage = PropertyMgr.getInt("bulletDamage_5");
    }


    @Override
    public void die() {
        this.living = false;
        this.explode = new NuclearExplode(this.x, this.y, gm);
        gm.add(explode);
    }
}
