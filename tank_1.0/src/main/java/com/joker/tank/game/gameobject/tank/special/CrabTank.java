package com.joker.tank.game.gameobject.tank.special;

import com.joker.tank.game.enums.Group;
import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.manager.PropertyMgr;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.strategy.fire.general.MidMissileFireStrategy;
import com.joker.tank.game.utils.ImageUtil;

import java.awt.*;

/**
 * @author 燧枫
 * @date 2022/12/13 16:28
*/
public class CrabTank extends Tank {

    public CrabTank(int x, int y, Group group, GameModel gm) {
        super(x, y, group, gm);
        this.width = ResourceMgr.tank_5_D.getWidth();
        this.height = ResourceMgr.tank_5_D.getHeight();
        this.rectangle = new Rectangle(x - width / 2, y - height / 2, width, height);
        this.health = PropertyMgr.getInt("crabTankHealth");
        this.maxHealth = PropertyMgr.getInt("crabTankHealth");
        this.speed = PropertyMgr.getInt("crabTankSpeed");
        this.srcSpeed = this.speed;
        this.maxFireStrategy = PropertyMgr.getInt("crabTankFireStrategyMax");
        this.armor = PropertyMgr.getInt("crabTankArmor");
        this.pene = PropertyMgr.getInt("crabTankPene");
        this.canFireCnt = PropertyMgr.getInt("crabCanFireCnt");
        // 初始随机移动方向
        randomDir();
        // 添加默认子弹
        addFireStrategy(new MidMissileFireStrategy(10));
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
                ImageUtil.printImage(g, ResourceMgr.tank_5_L, x, y);
                break;
            case UP:
                ImageUtil.printImage(g, ResourceMgr.tank_5_U, x, y);
                break;
            case RIGHT:
                ImageUtil.printImage(g, ResourceMgr.tank_5_R, x, y);
                break;
            case DOWN:
                ImageUtil.printImage(g, ResourceMgr.tank_5_D, x, y);
                break;
        }

        paintHealth(g);

        paintNowFireStrategy(g);
    }
}
