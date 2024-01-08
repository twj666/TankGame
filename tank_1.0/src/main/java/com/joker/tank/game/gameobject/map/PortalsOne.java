package com.joker.tank.game.gameobject.map;

import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;
import lombok.Data;

import java.awt.*;

/**
 * @author 燧枫
 * @date 2022/12/13 14:54
*/
@Data
public class PortalsOne extends GameObject {

    private Rectangle rectangle = null;

    public PortalsOne(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.map_portals_1.getWidth();
        this.height = ResourceMgr.map_portals_1.getHeight();
        rectangle = new Rectangle(x - width, y - height, width * 2, height * 2);
    }

    @Override
    public void paint(Graphics2D g) {
        ImageUtil.printImage(g, ResourceMgr.map_portals_1, x, y);
    }
}
