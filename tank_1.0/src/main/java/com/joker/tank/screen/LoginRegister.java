package com.joker.tank.screen;

import com.joker.tank.backend._frame.ResultMessage;
import com.joker.tank.backend.controller.UserController;
import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.game.manager.ResourceMgr;
import com.joker.tank.game.utils.ImageUtil;
import com.joker.tank.netsystem.client.ClientService;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author 燧枫
 * @date 2022/12/21 19:53
 */
// 登录与注册界面
public class LoginRegister {

    UserController userController = new UserController();

    private TankFrame tf;

    private String username = "";
    private String password = "";
    private String rpassword = "";

    private int log_reg = 0;
    private int opsNum = 0;
    private int opsMax = 3;

    private String prompt = "";

    public LoginRegister(TankFrame tf) {
        this.tf = tf;
    }

    // 打印页面
    public void paint(Graphics2D g) {

        if (log_reg == 0) {
            ImageUtil.printImage(g, ResourceMgr.menu_login, 750, 420);
            printUsername(g, username, 900, 305);
            printUsername(g, password, 900, 355);
        } else {
            ImageUtil.printImage(g, ResourceMgr.menu_register, 750, 420);
            printUsername(g, username, 900, 305);
            printUsername(g, password, 900, 355);
            printUsername(g, rpassword, 932, 405);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("楷体", Font.PLAIN, 20));
        g.drawString(prompt, 800, 600);

        g.setColor(new Color(6, 243, 243));

        if (log_reg == 0) {
            switch (opsNum) {
                case 0:
                    g.drawRoundRect(830, 285, 300, 45, 10, 10);
                    break;
                case 1:
                    g.drawRoundRect(830, 335, 300, 45, 10, 10);
                    break;
                case 2:
                    g.drawRoundRect(830, 414, 145, 45, 10, 10);
                    break;
                case 3:
                    g.drawRoundRect(982, 414, 145, 45, 10, 10);
                    break;
                default:
                    break;
            }
        } else {
            switch (opsNum) {
                case 0:
                    g.drawRoundRect(830, 285, 300, 45, 10, 10);
                    break;
                case 1:
                    g.drawRoundRect(830, 335, 300, 45, 10, 10);
                    break;
                case 2:
                    g.drawRoundRect(830, 385, 300, 45, 10, 10);
                    break;
                case 3:
                    g.drawRoundRect(830, 464, 145, 45, 10, 10);
                    break;
                case 4:
                    g.drawRoundRect(982, 464, 145, 45, 10, 10);
                    break;
                default:
                    break;
            }
        }
    }

    // 登录
    public void login() {
        // 账号或密码为空
        if (username.length() == 0 || password.length() == 0) {
            prompt = "账号或密码为空，请重新输入";
            return;
        }
        // 调用登录接口
        ResultMessage loginMsg = userController.login(username, password);
        tf.clientService.login(username, password);
        // 登录不成功
        if (loginMsg.isSuccess() == false) {
            prompt = loginMsg.getMessage();
            return;
        }
        // 登录成功
        tf.setUser((User)loginMsg.getResult());
        tf.changeNowFrame(1);
    }

    // 注册
    public void register() {
        // 账号或密码为空
        if (username.length() == 0 || password.length() == 0 || rpassword.length() == 0) {
            prompt = "账号或密码为空，请重新输入";
            return;
        }
        // 两次输入的密码不一样
        if (!password.equals(rpassword)) {
            prompt = "两次输入的密码不一样，请重新输入";
            return;
        }
        // 调用注册接口
        ResultMessage registerMsg = userController.register(username, password);
        // 注册不成功
        if (registerMsg.isSuccess() == false) {
            prompt = registerMsg.getMessage();
            return;
        }
        // 注册成功
        prompt = "注册成功";
    }

    // 打印用户名
    private void printUsername(Graphics2D g, String username, int x, int y) {
        ArrayList<BufferedImage> bufferedImageList = new ArrayList<>();
        if (username.equals("")) return;
        char[] help = username.toCharArray();
        for (int i = 0; i < help.length; i++) {
            int chNum = (int) help[i];
            if (chNum >= 97 && chNum <= 122) {
                bufferedImageList.add(ResourceMgr.font_letter[chNum - 97]);
            } else if (chNum >= 48 && chNum <= 57) {
                bufferedImageList.add(ResourceMgr.font_num[chNum - 48]);
            }
        }
        for (int i = 0; i < bufferedImageList.size(); i++) {
            ImageUtil.printImage(g, bufferedImageList.get(i), x + 20 * i, y);
        }
    }

    // 按下键
    public void keyPressed(int key) {
        prompt = "";
        switch (key) {
            case KeyEvent.VK_UP:
                choiceUp();
                break;
            case KeyEvent.VK_DOWN:
                choiceDown();
                break;
            case KeyEvent.VK_ENTER:
                choiceMakeSure();
                break;
            case 8:
                letterNumDown();
                break;
            default:
                letterNumAppend(key);
                break;
        }
    }

    // 追加字符串
    public void letterNumAppend(int key) {
        if (key >= 65 && key <= 90) {
            key += 32;
            if (log_reg == 0) {
                if (opsNum == 0) {
                    if (username.length() == 10) return;
                    username += (char) key;
                } else if (opsNum == 1) {
                    if (password.length() == 10) return;
                    password += (char) key;
                }
            } else {
                if (opsNum == 0) {
                    if (username.length() == 10) return;
                    username += (char) key;
                } else if (opsNum == 1) {
                    if (password.length() == 10) return;
                    password += (char) key;
                } else if (opsNum == 2) {
                    if (rpassword.length() == 10) return;
                    rpassword += (char) key;
                }
            }
        } else if (key >= 48 && key <= 57) {
            if (log_reg == 0) {
                if (opsNum == 0) {
                    if (username.length() == 10) return;
                    username += (char) key;
                } else if (opsNum == 1) {
                    if (password.length() == 10) return;
                    password += (char) key;
                }
            } else {
                if (opsNum == 0) {
                    if (username.length() == 10) return;
                    username += (char) key;
                } else if (opsNum == 1) {
                    if (password.length() == 10) return;
                    password += (char) key;
                } else if (opsNum == 2) {
                    if (rpassword.length() == 10) return;
                    rpassword += (char) key;
                }
            }
        }
    }

    // 缩减字符串
    public void letterNumDown() {
        if (log_reg == 0) {
            if (opsNum == 0) {
                if (username.equals("")) return;
                username = username.substring(0, username.length() - 1);
            } else if (opsNum == 1) {
                if (password.equals("")) return;
                password = password.substring(0, password.length() - 1);
            }
        } else {
            if (opsNum == 0) {
                if (username.equals("")) return;
                username = username.substring(0, username.length() - 1);
            } else if (opsNum == 1) {
                if (password.equals("")) return;
                password = password.substring(0, password.length() - 1);
            } else if (opsNum == 2) {
                if (rpassword.equals("")) return;
                rpassword = rpassword.substring(0, rpassword.length() - 1);
            }
        }
    }

    // 按下enter键
    public void choiceMakeSure() {
        // 登录界面
        if (log_reg == 0) {
            switch (opsNum) {
                // 选择登录
                case 2:
                    login();
                    break;
                case 3:
                    opsNum = 0;
                    log_reg = 1;
                    username = "";
                    password = "";
                    rpassword = "";
                    break;
                default:
                    break;
            }
            // 注册界面
        } else {
            switch (opsNum) {
                case 3:
                    username = "";
                    password = "";
                    opsNum = 0;
                    log_reg = 0;
                    break;
                case 4:
                    register();
                    break;
                default:
                    break;
            }
        }
    }

    // 按上箭头
    public void choiceUp() {
        if (log_reg == 0) {
            if (opsNum == 0) {
                opsNum = opsMax;
            } else {
                opsNum--;
            }
        } else {
            if (opsNum == 0) {
                opsNum = opsMax + 1;
            } else {
                opsNum--;
            }
        }
    }

    // 按下箭头
    public void choiceDown() {
        if (log_reg == 0) {
            if (opsNum == opsMax) {
                opsNum = 0;
            } else {
                opsNum++;
            }
        } else {
            if (opsNum == opsMax + 1) {
                opsNum = 0;
            } else {
                opsNum++;
            }
        }
    }
}
