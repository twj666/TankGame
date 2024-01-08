package com.joker.tank.game.strategy.flush.impl;

import com.joker.tank.game.enums.Group;
import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.map.*;
import com.joker.tank.game.gameobject.tank.Tank;
import com.joker.tank.game.gameobject.tank.general.FlyTank;
import com.joker.tank.game.gameobject.tank.general.GreenTank;
import com.joker.tank.game.gameobject.tank.general.HeavyTank;
import com.joker.tank.game.gameobject.tank.general.RedTank;
import com.joker.tank.game.gameobject.tank.special.CrabTank;
import com.joker.tank.game.manager.PropertyMgr;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.strategy.fire.FireStrategy;
import com.joker.tank.game.strategy.fire.general.*;
import com.joker.tank.game.strategy.fire.special.BlueBeamFireStrategy;
import com.joker.tank.game.strategy.fire.special.GreenBeamFireStrategy;
import com.joker.tank.game.strategy.fire.special.RedBeamFireStrategy;
import com.joker.tank.game.strategy.flush.FlushStrategy;
import com.joker.tank.game.utils.GameMapUtil;

import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * @author 燧枫
 * @date 2022/12/13 14:55
 */
public class DefaultFlashStrategy implements FlushStrategy {

    private SecureRandom random;

    int[] randXY;

    // 指定生成坦克的概率
    static ArrayList randomTankList;
    static ArrayList randomCrystalList;

    static {
        randomTankList = new ArrayList();
        for (int i = 1; i <= 4; i++) {
            for (int j = 0; j < 100; j++) {
                randomTankList.add(i);
            }
        }
        for (int j = 0; j < 30; j++) {
            randomTankList.add(5);
        }

        randomCrystalList = new ArrayList();
        for (int i = 0; i < 400; i++) {
            randomCrystalList.add(0);
        }
        for (int i = 0; i < 200; i++) {
            randomCrystalList.add(1);
        }
        for (int i = 0; i < 100; i++) {
            randomCrystalList.add(2);
        }
    }

    @Override
    public void flush(GameModel gm) {
        random = new SecureRandom();
        // 先取得需要生成物品的list
        ArrayList<Tank> tankList = new ArrayList<>();
        ArrayList<PortalsOne> portalsOneList = new ArrayList<>();
        ArrayList<Medkit> medkitList = new ArrayList<>();
        ArrayList<Love> loveList = new ArrayList<>();
        ArrayList<Crystal> crystalList = new ArrayList<>();
        for (GameObject o : gm.gameObjects) {
            if (o instanceof Tank) tankList.add((Tank) o);
            if (o instanceof PortalsOne) portalsOneList.add((PortalsOne) o);
            if (o instanceof Medkit) medkitList.add((Medkit) o);
            if (o instanceof Love) loveList.add((Love) o);
            if (o instanceof Crystal) crystalList.add((Crystal) o);
        }
        // 随机生成坦克
        if (tankList.size() < PropertyMgr.getInt("defaultFlashMaxTank")) {
            PortalsOne p = (PortalsOne) portalsOneList.get(random.nextInt(portalsOneList.size()));
            if (random.nextInt(2000) > (1999 - (PropertyMgr.getInt("defaultFlashMaxTank") - tankList.size()))) {
                boolean isAddTank = true;
                for (Tank t : tankList) {
                    if (t.getRectangle().intersects(p.getRectangle())) {
                        isAddTank = false;
                    }
                }
                if (isAddTank) {
                    putRandomTank(gm, p.getX(), p.getY(), Group.BAD);
                }
            }
        }
        // 随机生成医疗箱
        if (medkitList.size() < PropertyMgr.getInt("defaultFlashMaxMedkit")) {
            if (random.nextInt(3000) > 2999 - (PropertyMgr.getInt("defaultFlashMaxMedkit") - medkitList.size())) {
                randXY = getRandomXY(gm);
                gm.gameObjects.add(new Medkit(randXY[0], randXY[1], gm));
            }
        }
        // 随机生成爱心
        if (loveList.size() < PropertyMgr.getInt("defaultFlashMaxLove")) {
            if (random.nextInt(3000) > 2999 - (PropertyMgr.getInt("defaultFlashMaxLove") - loveList.size())) {
                randXY = getRandomXY(gm);
                gm.gameObjects.add(new Love(randXY[0], randXY[1], gm));
            }
        }
        // 随机生成水晶
        if (loveList.size() < PropertyMgr.getInt("defaultFlashMaxCrystal")) {
            if (random.nextInt(3000) > 2999 - (PropertyMgr.getInt("defaultFlashMaxCrystal") - crystalList.size())) {
                randXY = getRandomXY(gm);
                putRandomCrystal(gm, randXY[0], randXY[1]);
            }
        }
    }

    // 返回一个随机的空白处的X,Y
    public int[] getRandomXY(GameModel gm) {
        int x = 0, y = 0;
        boolean flag = true;
        while (flag) {
            x = random.nextInt(GameMapUtil.map1[0].length) * 20;
            y = random.nextInt(GameMapUtil.map1.length) * 20;
            Rectangle rectangle = new Rectangle(x - 20, y - 20, 40, 40);
            boolean isOk = true;
            for (GameObject o : gm.gameObjects) {
                if (o instanceof Grass) continue;
                if (rectangle.intersects(o.getRectangle())) {
                    isOk = false;
                }
            }
            if (isOk) flag = false;
        }
        return new int[]{x, y};
    }

    // 随机生成一个水晶
    private void putRandomCrystal(GameModel gm, int x, int y) {
        FireStrategy fireStrategy = null;
        int cnt = (int) randomCrystalList.get(random.nextInt(randomCrystalList.size()));
        switch (cnt) {
            case 0:
                gm.add(new Crystal(x, y, gm, ResourceMgr.map_crystal[0], getBlueCrystal()));
                break;
            case 1:
                gm.add(new Crystal(x, y, gm, ResourceMgr.map_crystal[1], getRedCrystal()));
                break;
            case 2:
                gm.add(new Crystal(x, y, gm, ResourceMgr.map_crystal[2], getColorCrystal()));
                break;
        }
    }

    // 随机返回一个蓝色品质的弹药
    private FireStrategy getBlueCrystal() {
        FireStrategy fireStrategy = null;
        int cnt = (int) random.nextInt(6);
        switch (cnt) {
            case 0:
                fireStrategy = new DefaultFireStrategy(8 + random.nextInt(9));
                break;
            case 1:
                fireStrategy = new PriMissileFireStrategy(4 + random.nextInt(5));
                break;
            case 2:
                fireStrategy = new MidMissileFireStrategy(2 + random.nextInt(3));
                break;
            case 3:
                fireStrategy = new BlueBeamFireStrategy(1 + random.nextInt(2));
                break;
            case 4:
                fireStrategy = new RedBeamFireStrategy(1 + random.nextInt(2));
                break;
            case 5:
                fireStrategy = new GreenBeamFireStrategy(1 + random.nextInt(2));
                break;
        }
        return fireStrategy;
    }

    // 随机返回一个红色品质的弹药
    private FireStrategy getRedCrystal() {
        FireStrategy fireStrategy = null;
        int cnt = (int) random.nextInt(7);
        switch (cnt) {
            case 0:
                fireStrategy = new DefaultFireStrategy(16 + random.nextInt(17));
                break;
            case 1:
                fireStrategy = new PriMissileFireStrategy(8 + random.nextInt(9));
                break;
            case 2:
                fireStrategy = new MidMissileFireStrategy(4 + random.nextInt(5));
                break;
            case 3:
                fireStrategy = new BlueBeamFireStrategy(2 + random.nextInt(3));
                break;
            case 4:
                fireStrategy = new RedBeamFireStrategy(2 + random.nextInt(3));
                break;
            case 5:
                fireStrategy = new GreenBeamFireStrategy(2 + random.nextInt(3));
                break;
            case 6:
                fireStrategy = new HighMissleFireStrategy(1 + random.nextInt(2));
                break;
        }
        return fireStrategy;
    }

    // 随机返回一个彩色品质的弹药
    private FireStrategy getColorCrystal() {
        FireStrategy fireStrategy = null;
        int cnt = (int) random.nextInt(6);
        switch (cnt) {
            case 0:
                fireStrategy = new MidMissileFireStrategy(8 + random.nextInt(9));
                break;
            case 1:
                fireStrategy = new BlueBeamFireStrategy(4 + random.nextInt(5));
                break;
            case 2:
                fireStrategy = new RedBeamFireStrategy(4 + random.nextInt(5));
                break;
            case 3:
                fireStrategy = new GreenBeamFireStrategy(4 + random.nextInt(5));
                break;
            case 4:
                fireStrategy = new HighMissleFireStrategy(2 + random.nextInt(3));
                break;
            case 5:
                fireStrategy = new NuclearFireStrategy(1 + random.nextInt(2));
                break;
        }
        return fireStrategy;
    }

    // 随机生成一个坦克
    private void putRandomTank(GameModel gm, int x, int y, Group group) {
        int cnt = (int) randomTankList.get(random.nextInt(randomTankList.size()));
        switch (cnt) {
            case 1:
                gm.add(new RedTank(x, y, group, gm));
                break;
            case 2:
                gm.add(new GreenTank(x, y, group, gm));
                break;
            case 3:
                gm.add(new FlyTank(x, y, group, gm));
                break;
            case 4:
                gm.add(new HeavyTank(x, y, group, gm));
                break;
            case 5:
                gm.add(new CrabTank(x, y, group, gm));
                break;
            default:
                break;
        }
    }
}
