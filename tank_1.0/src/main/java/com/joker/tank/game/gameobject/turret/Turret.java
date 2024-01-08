package com.joker.tank.game.gameobject.turret;

import com.joker.tank.game.enums.Group;

import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;

import java.awt.*;

public class Turret extends GameObject {

    private Group group;

    public Turret(int x, int y, Group group) {
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.turret_1.getWidth();
        this.height = ResourceMgr.turret_1.getHeight();
        this.rectangle = new Rectangle(x - width / 2, y - height / 2, width, height);
        this.group = group;
    }

    @Override
    public void paint(Graphics2D g) {
        ImageUtil.printImage(g, ResourceMgr.turret_1, x, y);
    }
}
