package com.joker.tank.game.gameobject.map;

import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;
import lombok.Data;

import java.awt.*;

/**
 * @author 燧枫
 * @date 2022/12/13 14:53
*/
@Data
public class Love extends GameObject {

    private Rectangle rectangle = null;

    private boolean living = true;

    private GameModel gm;

    public Love(int x, int y, GameModel gm) {
        this.x = x;
        this.y = y;
        this.gm = gm;
        this.width = ResourceMgr.map_love.getWidth();
        this.height = ResourceMgr.map_love.getHeight();
        rectangle = new Rectangle(x - width / 2, y - this.height / 2, width, height);
    }

    public void die() {
        this.living = false;
    }

    @Override
    public void paint(Graphics2D g) {
        if (!living) gm.remove(this);
        ImageUtil.printImage(g, ResourceMgr.map_love, x, y);
    }

}
