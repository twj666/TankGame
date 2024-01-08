package com.joker.tank.game.gameobject.tank.general;

import com.joker.tank.game.enums.Group;
import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.manager.PropertyMgr;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.strategy.fire.general.DefaultFireStrategy;
import com.joker.tank.game.utils.ImageUtil;

import java.awt.*;

/**
 * @author 燧枫
 * @date 2022/12/13 14:54
*/
public class GreenTank extends Tank {

    public GreenTank(int x, int y, Group group, GameModel gm) {
        super(x, y, group, gm);
        this.width = ResourceMgr.tank_2_D.getWidth();
        this.height = ResourceMgr.tank_2_D.getHeight();
        this.rectangle = new Rectangle(x - width / 2, y - height / 2, width, height);
        this.health = PropertyMgr.getInt("greenTankHealth");
        this.maxHealth = PropertyMgr.getInt("greenTankHealth");
        this.speed = PropertyMgr.getInt("greenTankSpeed");
        this.srcSpeed = this.speed;
        this.maxFireStrategy = PropertyMgr.getInt("greenTankFireStrategyMax");
        this.armor = PropertyMgr.getInt("greenTankArmor");
        this.pene = PropertyMgr.getInt("greenTankPene");
        this.canFireCnt = PropertyMgr.getInt("greenCanFireCnt");
        // 初始随机移动方向
        randomDir();
        // 添加默认子弹
        addFireStrategy(new DefaultFireStrategy(20));
    }

    // 打印坦克
    @Override
    public void paint(Graphics2D g) {
        if (!living) gm.remove(this);

        // 先移动至下一步
        move(g);

        canFire--;

        paintHealthChange(g);

        // 判断是否隐身
        if (isStealth) {
            ImageUtil.printImage(g, ResourceMgr.explode_2[1], x, y);
            isStealth = false;
            return;
        }

        switch (dir) {
            case LEFT:
                ImageUtil.printImage(g, ResourceMgr.tank_2_L, x, y);
                break;
            case UP:
                ImageUtil.printImage(g, ResourceMgr.tank_2_U, x, y);
                break;
            case RIGHT:
                ImageUtil.printImage(g, ResourceMgr.tank_2_R, x, y);
                break;
            case DOWN:
                ImageUtil.printImage(g, ResourceMgr.tank_2_D, x, y);
                break;
        }

        paintHealth(g);

        paintNowFireStrategy(g);
    }
}
