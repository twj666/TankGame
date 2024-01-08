package com.joker.tank.game.gameobject.map;

import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

/**
 * @author 燧枫
 * @date 2022/12/13 20:35
*/
@Data
public class Grass extends GameObject {

    private Rectangle rectangle = null;

    private SecureRandom random = new SecureRandom();

    BufferedImage bufferedImage = null;

    public Grass(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.map_grass[0].getWidth();
        this.height = ResourceMgr.map_grass[0].getHeight();
        rectangle = new Rectangle(x - width / 2, y - this.height / 2, width, height);
        bufferedImage = ResourceMgr.map_grass[random.nextInt(9)];
    }

    @Override
    public void paint(Graphics2D g) {
        ImageUtil.printImage(g, bufferedImage, x, y);
    }

}
