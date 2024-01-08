package com.joker.tank.screen;

import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.game.gamemodel.GameModel;
import com.joker.tank.game.manager.PropertyMgr;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.strategy.flush.impl.DefaultFlashStrategy;
import com.joker.tank.game.utils.ImageUtil;
import com.joker.tank.netsystem.client.ClientService;
import lombok.Data;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 燧枫
 * @date 2022/12/13 14:49
 */
// 游戏界面
@Data
public class TankFrame extends Frame {

    public ClientService clientService = new ClientService(this);

    public static final int GAME_WIDTH = PropertyMgr.getInt("gameWidth");

    public static final int GAME_HIGHT = PropertyMgr.getInt("gameHeight");

    // 当前用户
    User user = new User();

    // 登录注册界面
    LoginRegister lr = new LoginRegister(this);

    // 主菜单界面
    MainMenu mm = null;

    // 游戏选择界面
    GameChoice gc = new GameChoice(this);

    // 用户信息界面
    UserInfo ui = null;

    // 社交大厅界面
    MakeFriend mf = new MakeFriend(this);

    /**
     * 0 : 登录注册界面
     * 1 : 主界面
     * 2 : 单人模式
     * 3 : 社交大厅
     * 4 : 用户信息
     */
    private int nowFrame = 0;

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HIGHT);
        setResizable(false);
        setTitle("Tank war");
        setVisible(true);
        addKeyListener(new MykeyListener());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    Image offScreenImage = null;

    // 双缓冲优化
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_WIDTH);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(new Color(115, 109, 109));
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    // 打印界面
    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        switch (nowFrame) {
            case 0:
                lr.paint(g2);
                break;
            case 1:
                mm.paint(g2);
                break;
            case 2:
                gc.paint(g2);
                break;
            case 3:
                mf.paint(g2);
                break;
            case 4:
                ui.paint(g2);
                break;
        }
    }

    class MykeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch (nowFrame) {
                case 0:
                    lr.keyPressed(key);
                    break;
                case 1:
                    mm.keyPressed(key);
                    break;
                case 2:
                    gc.keyPressed(key);
                    break;
                case 3:
                    mf.keyPressed(key);
                    break;
                case 4:
                    ui.keyPressed(key);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            switch (nowFrame) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    gc.keyReleased(key);
                    break;
                default:
                    break;
            }
        }


    }

    // 改变当前界面
    public void changeNowFrame(int x) {
        switch (x) {
            case 0:
                lr = new LoginRegister(this);
                nowFrame = 0;
                break;
            case 1:
                mm = new MainMenu(this);
                nowFrame = 1;
                break;
            case 2:
                gc = new GameChoice(this);
                nowFrame = 2;
                break;
            case 3:
                mf = new MakeFriend(this);
                nowFrame = 3;
                break;
            case 4:
                ui = new UserInfo(this);
                nowFrame = 4;
                break;
            default:
                break;
        }
    }

}
