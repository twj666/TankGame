package com.joker.tank.game.strategy.tankai;

import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.gameobject.tank.Tank;

/**
 * @author 燧枫
 * @date 2022/12/14 0:39
*/
public interface TankAiStrategy {

    void tankAiFire(GameModel gm, Tank tank);

    void tankAiMove(GameModel gm, Tank tank);

    void tankAiChangeFire(GameModel gm, Tank tank);
}
