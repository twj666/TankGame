package com.joker.tank.game.gameobject.map;

import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.manager.PropertyMgr;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;
import lombok.Data;

import java.awt.*;

/**
 * @author 燧枫
 * @date 2022/12/13 14:54
*/
@Data
public class Wall extends GameObject {

    private int health = PropertyMgr.getInt("wallHealth");

    private Rectangle rectangle = null;

    private boolean living = true;

    private GameModel gm;

    public Wall(int x, int y, GameModel gm) {
        this.gm = gm;
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.map_wall.getWidth();
        this.height = ResourceMgr.map_wall.getHeight();
        rectangle = new Rectangle(x - width / 2, y - this.height / 2, width, height);
    }

    @Override
    public void paint(Graphics2D g) {
        if (!living) gm.remove(this);
        ImageUtil.printImage(g, ResourceMgr.map_wall, x, y);
    }

    public void die() {
        this.living = false;
    }

}
