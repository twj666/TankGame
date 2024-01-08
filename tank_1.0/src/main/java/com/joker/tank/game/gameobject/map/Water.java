package com.joker.tank.game.gameobject.map;

import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author 燧枫
 * @date 2022/12/13 14:54
*/
@Data
public class Water extends GameObject {

    private Rectangle rectangle = null;

    private ArrayList helpList;

    private boolean isRow;

    public Water(ArrayList helpList, boolean isRow) {
        this.helpList = helpList;
        this.isRow = isRow;
        this.width = ResourceMgr.map_water.getWidth();
        this.height = ResourceMgr.map_water.getHeight();
        if (isRow) {
            rectangle = new Rectangle((int) helpList.get(1) - width / 2
                    , (int) helpList.get(0) - height / 2,
                    (helpList.size() - 1) * width, height);
        } else {
            rectangle = new Rectangle((int) helpList.get(0) - width / 2
                    , (int) helpList.get(1) - height / 2,
                    width, (helpList.size() - 1) * height);
        }
    }

    @Override
    public void paint(Graphics2D g) {
        ImageUtil.printImage(g, ResourceMgr.map_water, x, y);
        int pre0 = (int)helpList.get(0);
        if (isRow) {
            for (int i = 1; i < helpList.size(); i++) {
                ImageUtil.printImage(g, ResourceMgr.map_water, (int)helpList.get(i), pre0);
            }
        } else {
            for (int i = 1; i < helpList.size(); i++) {
                ImageUtil.printImage(g, ResourceMgr.map_water, pre0, (int)helpList.get(i));
            }
        }
    }
}
