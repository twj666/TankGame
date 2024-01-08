package com.joker.tank.game.gameobject.tank.general;

import com.joker.tank.game.enums.Group;
import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.manager.PropertyMgr;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.strategy.fire.special.BlueBeamFireStrategy;
import com.joker.tank.game.strategy.fire.special.GreenBeamFireStrategy;
import com.joker.tank.game.strategy.fire.special.RedBeamFireStrategy;
import com.joker.tank.game.utils.ImageUtil;

import java.awt.*;

/**
 * @author 燧枫
 * @date 2022/12/13 15:41
*/
public class FlyTank extends Tank {

    public FlyTank(int x, int y, Group group, GameModel gm) {
        super(x, y, group, gm);
        this.width = ResourceMgr.tank_3_D.getWidth();
        this.height = ResourceMgr.tank_3_D.getHeight();
        this.rectangle = new Rectangle(x - width / 2, y - height / 2, width, height);
        this.health = PropertyMgr.getInt("flyTankHealth");
        this.maxHealth = PropertyMgr.getInt("flyTankHealth");
        this.speed = PropertyMgr.getInt("flyTankSpeed");
        this.srcSpeed = this.speed;
        this.maxFireStrategy = PropertyMgr.getInt("flyTankFireStrategyMax");
        this.armor = PropertyMgr.getInt("flyTankArmor");
        this.pene = PropertyMgr.getInt("flyTankPene");
        this.canFireCnt = PropertyMgr.getInt("flyCanFireCnt");
        // 初始随机移动方向
        randomDir();
        // 添加默认子弹
        addFireStrategy(new GreenBeamFireStrategy(3));
        addFireStrategy(new RedBeamFireStrategy(3));
        addFireStrategy(new BlueBeamFireStrategy(3));

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
                ImageUtil.printImage(g, ResourceMgr.tank_3_L, x, y);
                break;
            case UP:
                ImageUtil.printImage(g, ResourceMgr.tank_3_U, x, y);
                break;
            case RIGHT:
                ImageUtil.printImage(g, ResourceMgr.tank_3_R, x, y);
                break;
            case DOWN:
                ImageUtil.printImage(g, ResourceMgr.tank_3_D, x, y);
                break;
        }

        paintHealth(g);

        paintNowFireStrategy(g);
    }
}
