package com.joker.tank.game.gameobject.explode;

import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.gameobject.GameObject;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author 燧枫
 * @date 2022/12/13 14:53
*/
@Data
public abstract class Explode extends GameObject {

    protected BufferedImage[] explode_x;

    protected int step = 0;

    protected boolean living = true;

    protected GameModel gm = null;

    protected int cnt = 0;

    protected Rectangle rectangle = null;

    public Explode(int x, int y, GameModel gm) {
        this.x = x;
        this.y = y;
        this.gm = gm;
    }
}
