package com.joker.tank.game.gameobject;

import lombok.Data;

import java.awt.*;

/**
 * @author 燧枫
 * @date 2022/12/13 14:52
*/
@Data
public abstract class GameObject {

    protected int x;

    protected int y;

    protected int width;

    protected int height;

    protected Rectangle rectangle;

    public abstract void paint(Graphics2D g);
}
