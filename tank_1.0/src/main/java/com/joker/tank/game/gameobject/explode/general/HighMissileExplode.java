package com.joker.tank.game.gameobject.explode.general;

import com.joker.tank.game.gameobject.explode.Explode;
import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;

import java.awt.*;

/**
 * @author 燧枫
 * @date 2022/12/13 14:53
*/
public class HighMissileExplode extends Explode {

    public HighMissileExplode(int x, int y, GameModel gm) {
        super(x, y, gm);
        this.explode_x = ResourceMgr.explode_5;
        this.width = explode_x[0].getWidth();
        this.height = explode_x[0].getHeight();
        rectangle = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    @Override
    public void paint(Graphics2D g) {
        if (!living) {
            gm.remove(this);
            return;
        }
        if (cnt == 0) {
            cnt = 5;
            ImageUtil.printImage(g, explode_x[step++], x, y);
        }
        cnt--;
        if (step == explode_x.length) living = false;
    }

}
