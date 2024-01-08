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
public class RedTank extends Tank {

    public RedTank(int x, int y, Group group, GameModel gm) {
        super(x, y, group, gm);
        this.width = ResourceMgr.tank_1_D.getWidth();
        this.height = ResourceMgr.tank_1_D.getHeight();
        this.rectangle = new Rectangle(x - width / 2, y - height / 2, width, height);
        this.health = PropertyMgr.getInt("redTankHealth");
        this.maxHealth = PropertyMgr.getInt("redTankHealth");
        this.speed = PropertyMgr.getInt("redTankSpeed");
        this.srcSpeed = this.speed;
        this.maxFireStrategy = PropertyMgr.getInt("redTankFireStrategyMax");
        this.armor = PropertyMgr.getInt("redTankArmor");
        this.pene = PropertyMgr.getInt("redTankPene");
        this.canFireCnt = PropertyMgr.getInt("redCanFireCnt");
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
                ImageUtil.printImage(g, ResourceMgr.tank_1_L, x, y);
                break;
            case UP:
                ImageUtil.printImage(g, ResourceMgr.tank_1_U, x, y);
                break;
            case RIGHT:
                ImageUtil.printImage(g, ResourceMgr.tank_1_R, x, y);
                break;
            case DOWN:
                ImageUtil.printImage(g, ResourceMgr.tank_1_D, x, y);
                break;
        }

        paintHealth(g);

        paintNowFireStrategy(g);

    }
}
