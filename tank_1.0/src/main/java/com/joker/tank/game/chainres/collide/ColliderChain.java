package com.joker.tank.game.chainres.collide;

import com.joker.tank.game.chainres.collide.impl.*;
import com.joker.tank.game.gameobject.GameObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
* @author 燧枫
* @date 2022/12/13 14:50
*/
public class ColliderChain implements Collider {

    private ArrayList<Collider> colliders = new ArrayList<>();

    ArrayList<GameObject> gameObjects = null;

    // 初始化责任链
    public ColliderChain(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
        add(new BulletTankCollider(gameObjects));
        add(new SteelBulletCollider(gameObjects));
        add(new WallBulletCollider(gameObjects));
        add(new CrystalTankCollider());
        add(new GrassTankCollider());
        add(new TankTankCollider());
        add(new SteelTankCollider());
        add(new WallTankCollider());
        add(new WaterTankCollider());
        add(new MedkitTankCollider());
        add(new LoveTankCollider());
    }

    public void add(Collider c) {
        colliders.add(c);
    }

    // 遍历责任链
    public boolean collide(GameObject o1, GameObject o2) {
        Iterator<Collider> iterator = colliders.iterator();
        while (iterator.hasNext()) {
            Collider next = iterator.next();
            if (!next.collide(o1, o2)) return false;
        }
        return true;
    }
}
