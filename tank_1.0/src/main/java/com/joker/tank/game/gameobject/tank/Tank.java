package com.joker.tank.game.gameobject.tank;


import com.joker.tank.screen.TankFrame;
import com.joker.tank.game.enums.Dir;
import com.joker.tank.game.enums.Group;
import com.joker.tank.game.gameobject.GameObject;
import com.joker.tank.game.gameobject.explode.general.TankExplode;
import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.manager.PropertyMgr;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.strategy.fire.FireStrategy;
import com.joker.tank.game.strategy.tankai.TankAiStrategy;
import com.joker.tank.game.strategy.tankai.impl.DefaultTankAiStrategy;
import com.joker.tank.game.utils.ImageUtil;
import lombok.Data;

import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 燧枫
 * @date 2022/12/13 14:49
 */
@Data
public abstract class Tank extends GameObject {

    private SecureRandom random = new SecureRandom();

    private int preX;

    private int preY;

    protected int speed;
    // 初始速度
    protected int srcSpeed;

    protected int health;

    protected int maxHealth;

    protected int width;

    protected int height;

    protected Dir dir = Dir.DOWN;

    protected boolean moving = true;

    protected  GameModel gm;

    protected boolean living = true;

    protected Group group = Group.BAD;

    protected Rectangle rectangle = null;

    // 是否处于隐身
    protected boolean isStealth = false;
    // 生命值改变特效
    private Queue<Integer> healthChange = new LinkedList<>();
    // 子弹集合
    private ArrayList<FireStrategy> fireStrategies = new ArrayList<>();
    // 当前子弹类型
    private int nowFireStrategy = 0;
    // 能携带的子弹种类限制
    protected int maxFireStrategy;
    // 护甲
    protected int armor;
    // 护甲穿透
    protected int pene;
    // 是否能开火了
    protected int canFire;
    protected int canFireCnt;
    // 坦克ai的策略
    protected TankAiStrategy tankAiStrategy = new DefaultTankAiStrategy();
    // 坦克停止计数
    protected int movingCnt = 0;
    // 坦克中毒计数
    protected int poisonCnt = 0;
    // 坦克破解计数
    protected int peneCnt = 0;

    public Tank(int x, int y, Group group, GameModel gm) {
        this.x = x;
        this.y = y;
        this.group = group;
        this.gm = gm;
    }

    // 受到伤害
    public void getHurt(int enemyDamage, int enemyPene) {
        int nowArmor = this.armor;
        // 如果有破解效果，减少护甲
        if (peneCnt > 0) nowArmor = nowArmor / 4;
        // 计算剩余的护甲值
        nowArmor = nowArmor - enemyPene;
        // 计算将造成的伤害
        int realDamage = (int) (enemyDamage * 100 / (nowArmor + 100));
        // 造成伤害, 并判断会不会死
        this.health -= realDamage;
        addHealthChange(-realDamage);
        if (health <= 0) die();
    }

    // 新增子弹
    public boolean addFireStrategy(FireStrategy fireStrategy) {
        boolean flag = false;
        // 先判断是否已拥有当前子弹类型,拥有就直接增加子弹数量
        for (int i = 0; i < fireStrategies.size(); i++) {
            flag = fireStrategies.get(i).addBulletNum(fireStrategy);
            if (flag == true) return true;
        }
        // 再判断是否能加入新的子弹
        if (flag == false) {
            if (fireStrategies.size() < maxFireStrategy) {
                fireStrategies.add(fireStrategy);
                return true;
            }
        }
        return false;
    }

    // 切换子弹
    public void changeFireStrategy() {
        nowFireStrategy++;
        // 循环
        if (nowFireStrategy >= fireStrategies.size()) nowFireStrategy = 0;
    }

    // 丢弃子弹
    public void subFireStrategy(FireStrategy fireStrategy) {
        fireStrategies.remove(fireStrategy);
        // 自动切换至下一种子弹
        if (nowFireStrategy >= fireStrategies.size()) nowFireStrategy = 0;
    }

    // 开火
    public void fire() {
        // 如果冷却没好, 返回
        if (canFire >= 0) return;
        canFire = canFireCnt;
        // 如果没有子弹, 返回
        if (fireStrategies.size() == 0) return;
        fireStrategies.get(nowFireStrategy).fire(this);
    }

    // 打印坦克
    public abstract void paint(Graphics2D g);

    // 打印当前子弹
    protected void paintNowFireStrategy(Graphics2D g) {
        // 如果没有子弹，返回
        if (fireStrategies.size() == 0) return;
        fireStrategies.get(nowFireStrategy).paintFireStrategy(g, this.rectangle);
    }

    // 打印生命条
    protected void paintHealth(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawRect(x - width / 2 - 1, y - width / 2 - 11, width + 1, 6);
        if (health > maxHealth * 0.66) {
            g.setColor(Color.GREEN);
        } else if (health > maxHealth * 0.33) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.RED);
        }
        int x1 = x - width / 2;
        int y1 = y - height / 2 - 9;
        int x2 = x - width / 2 + (int) width * health / maxHealth - 1;
        int y2 = y - height / 2 - 9;
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1 + 1, x2, y2 + 1);
        g.drawLine(x1, y1 + 2, x2, y2 + 2);
        g.drawLine(x1, y1 + 3, x2, y2 + 3);
        g.setColor(new Color(0, 255, 255));
        g.setFont(new Font("宋体", Font.PLAIN, 14));
        g.drawString(health + "", x1 + 1, y1 - 3);
    }

    // 打印改变的生命值
    public void paintHealthChange(Graphics2D g) {
        if (!healthChange.isEmpty()) {
            Color c = g.getColor();
            int hc = healthChange.remove();
            g.setFont(new Font("楷体", Font.BOLD, 20));
            if (hc != 0) {
                if (hc < 0) {
                    g.setColor(Color.RED);
                    g.drawString((-hc) + "↓", x + width, y);
                } else {
                    g.setColor(Color.GREEN);
                    g.drawString(hc + "↑", x + width, y);
                }
                g.setColor(c);
            }
        }
    }

    // 增加改变生命值的动画
    public void addHealthChange(int x) {
        for (int i = 0; i < 40; i++) {
            healthChange.add(x);
        }
        for (int i = 0; i < 10; i++) {
            healthChange.add(0);
        }
        if (healthChange.size() > 50) {
            for (int i = 0; i < 40; i++) {
                healthChange.remove();
            }
        }
    }

    // 改变移动方向
    public void changeDir(Dir dir) {
        int flag = 0;
        if ((this.dir == Dir.UP || this.dir == Dir.DOWN)) {
            if (dir == Dir.LEFT || dir == Dir.RIGHT) {
                flag = 1;
            }
        }
        if ((this.dir == Dir.LEFT || this.dir == Dir.RIGHT)) {
            if (dir == Dir.UP || dir == Dir.DOWN) {
                flag = 1;
            }
        }
        if (flag == 1) {
            int temp = this.width;
            this.width = this.height;
            this.height = temp;
        }
        setDir(dir);
    }

    // 回退到上一步
    public void back() {
        x = preX;
        y = preY;
    }

    // 移动
    protected void move(Graphics2D g) {

        // 检查特殊子弹
        // 坦克的停止计数大于零，就不移动，并将计数器减1，并画出冰蓝效果
        if (movingCnt > 0) {
            ImageUtil.printImage(g, ResourceMgr.freeze, x, y + height);
            movingCnt--;
            return;
        }

        // 坦克的中毒计数大于零，坦克间隔造成伤害，并画出中毒效果
        if (poisonCnt > 0) {
            ImageUtil.printImage(g, ResourceMgr.poison, x - width, y);
            if (poisonCnt % 100 == 0) {
                getHurt(PropertyMgr.getInt("poisonDamage"), 0);
            }
            poisonCnt--;
        }

        // 坦克的破甲计数大于零，坦克间隔造成伤害，并画出中毒效果
        if (peneCnt > 0) {
            ImageUtil.printImage(g, ResourceMgr.pene, x + width, y);
            peneCnt--;
        }

        // ai控制敌方坦克的移动,开火,切换武器
        if (this.group == Group.BAD) {
            tankAiStrategy.tankAiMove(gm, this);
            tankAiStrategy.tankAiFire(gm, this);
            tankAiStrategy.tankAiChangeFire(gm, this);
        }

        if (!moving) return;

        this.preX = x;
        this.preY = y;

        switch (dir) {
            case LEFT:
                x -= speed;
                break;
            case UP:
                y -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case DOWN:
                y += speed;
                break;
            default:
                break;
        }

        boundsCheck();
        rectangle.x = this.x - this.width / 2;
        rectangle.y = this.y - this.height / 2;
    }

    // 边界碰撞检测
    private void boundsCheck() {
        if (this.x < this.width) x = this.width;
        if (this.y < this.height) y = this.height;
        if (this.x > TankFrame.GAME_WIDTH - this.width) x = TankFrame.GAME_WIDTH - this.width;
        if (this.y > TankFrame.GAME_HIGHT - this.height) y = TankFrame.GAME_HIGHT - this.height;
    }

    // 随机改变坦克移动方向
    public void randomDir() {
        if (random.nextInt(1000) > 970) {
            changeDir(Dir.values()[random.nextInt(4)]);
        }
    }

    // 坦克死亡
    public void die() {
        this.living = false;
        gm.add(new TankExplode(this.x, this.y, gm));
    }
}
