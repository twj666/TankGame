package com.joker.tank.game.manager;

import com.joker.tank.game.utils.ImageUtil;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 燧枫
 * @date 2022/12/13 14:54
*/
// 图片管理类
public class ResourceMgr {

    private ResourceMgr() {
    }

    public static BufferedImage gameover;
    public static BufferedImage pause;

    public static BufferedImage daimond, gold;

    public static BufferedImage bg;

    public static BufferedImage menu_main;
    public static BufferedImage menu_user;
    public static BufferedImage menu_login;
    public static BufferedImage menu_register;

    public static BufferedImage font_mes;
    public static BufferedImage font_mess;
    public static BufferedImage font_revive;
    public static BufferedImage font_pause;
    public static BufferedImage font_back;
    public static BufferedImage font_house;
    public static BufferedImage font_add_daimond;
    public static BufferedImage font_single;
    public static BufferedImage font_friend;
    public static BufferedImage font_user;
    public static BufferedImage font_exit;
    public static BufferedImage[] font_letter = new BufferedImage[26];
    public static BufferedImage[] font_num = new BufferedImage[26];

    public static BufferedImage tank_1_L, tank_1_U, tank_1_R, tank_1_D;
    public static BufferedImage tank_2_L, tank_2_U, tank_2_R, tank_2_D;
    public static BufferedImage tank_3_L, tank_3_U, tank_3_R, tank_3_D;
    public static BufferedImage tank_4_L, tank_4_U, tank_4_R, tank_4_D;
    public static BufferedImage tank_5_L, tank_5_U, tank_5_R, tank_5_D;

    public static BufferedImage turret_1;

    public static BufferedImage bullet_1;
    public static BufferedImage bullet_2_L, bullet_2_U, bullet_2_R, bullet_2_D, bullet_2_X;
    public static BufferedImage bullet_3_L, bullet_3_U, bullet_3_R, bullet_3_D, bullet_3_X;
    public static BufferedImage bullet_4_L, bullet_4_U, bullet_4_R, bullet_4_D, bullet_4_X;
    public static BufferedImage bullet_5_L, bullet_5_U, bullet_5_R, bullet_5_D, bullet_5_X;
    public static BufferedImage bullet_6_L, bullet_6_U, bullet_6_R, bullet_6_D, bullet_6_X;
    public static BufferedImage bullet_7_L, bullet_7_U, bullet_7_R, bullet_7_D, bullet_7_X;
    public static BufferedImage bullet_8_L, bullet_8_U, bullet_8_R, bullet_8_D, bullet_8_X;

    public static BufferedImage[] explode_1 = new BufferedImage[7];
    public static BufferedImage[] explode_2 = new BufferedImage[3];
    public static BufferedImage[] explode_3 = new BufferedImage[60];
    public static BufferedImage[] explode_4 = new BufferedImage[9];
    public static BufferedImage[] explode_5 = new BufferedImage[16];
    public static BufferedImage[] explode_6 = new BufferedImage[20];
    public static BufferedImage[] explode_7 = new BufferedImage[9];
    public static BufferedImage[] explode_8 = new BufferedImage[9];
    public static BufferedImage[] explode_9 = new BufferedImage[9];

    public static BufferedImage map_steel;
    public static BufferedImage map_wall;
    public static BufferedImage map_water;
    public static BufferedImage[] map_grass = new BufferedImage[9];
    public static BufferedImage map_medkit;
    public static BufferedImage map_love;
    public static BufferedImage map_portals_1;
    public static BufferedImage[] map_crystal = new BufferedImage[3];
    public static BufferedImage freeze;
    public static BufferedImage poison;
    public static BufferedImage pene;
    public static BufferedImage map1, map2, map3;



    static {
        try {

            gameover = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/gameover.png"));
            pause = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/pause.png"));

            daimond = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/daimond.png"));
            gold = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/gold.png"));

            bg = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bg.png"));

            menu_main = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/menu_main.png"));
            menu_user = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/menu_user.png"));
            menu_login = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/menu_login.png"));
            menu_register = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/menu_register.png"));

            font_mes = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_mes.png"));
            font_mess = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_mess.png"));
            font_revive = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_revive.png"));
            font_pause = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_pause.png"));
            font_house = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_house.png"));
            font_back = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_back.png"));
            font_add_daimond = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_add_daimond.png"));
            font_single = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_single.png"));
            font_friend = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_friend.png"));
            font_user = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_user.png"));
            font_exit = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_exit.png"));
            font_letter = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_letter.png")), 26, 1, 26);
            font_num = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/font_num.png")), 26, 1, 26);

            tank_1_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/tank_1_L.png"));
            tank_1_U = ImageUtil.rotateImage(tank_1_L, 90);
            tank_1_R = ImageUtil.rotateImage(tank_1_U, 90);
            tank_1_D = ImageUtil.rotateImage(tank_1_R, 90);

            tank_2_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/tank_2_L.png"));
            tank_2_U = ImageUtil.rotateImage(tank_2_L, 90);
            tank_2_R = ImageUtil.rotateImage(tank_2_U, 90);
            tank_2_D = ImageUtil.rotateImage(tank_2_R, 90);

            tank_3_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/tank_3_L.png"));
            tank_3_U = ImageUtil.rotateImage(tank_3_L, 90);
            tank_3_R = ImageUtil.rotateImage(tank_3_U, 90);
            tank_3_D = ImageUtil.rotateImage(tank_3_R, 90);

            tank_4_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/tank_4_L.png"));
            tank_4_U = ImageUtil.rotateImage(tank_4_L, 90);
            tank_4_R = ImageUtil.rotateImage(tank_4_U, 90);
            tank_4_D = ImageUtil.rotateImage(tank_4_R, 90);

            tank_5_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/tank_5_L.png"));
            tank_5_U = ImageUtil.rotateImage(tank_5_L, 90);
            tank_5_R = ImageUtil.rotateImage(tank_5_U, 90);
            tank_5_D = ImageUtil.rotateImage(tank_5_R, 90);

            turret_1 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/turret_1.png"));

            bullet_1 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_1.png"));

            bullet_2_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_2_L.png"));
            bullet_2_U = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_2_U.png"));
            bullet_2_R = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_2_R.png"));
            bullet_2_D = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_2_D.png"));
            bullet_2_X = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_2_X.png"));

            bullet_3_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_3_L.png"));
            bullet_3_U = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_3_U.png"));
            bullet_3_R = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_3_R.png"));
            bullet_3_D = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_3_D.png"));
            bullet_3_X = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_3_X.png"));

            bullet_4_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_4_L.png"));
            bullet_4_U = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_4_U.png"));
            bullet_4_R = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_4_R.png"));
            bullet_4_D = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_4_D.png"));
            bullet_4_X = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_4_X.png"));

            bullet_5_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_5_L.png"));
            bullet_5_U = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_5_U.png"));
            bullet_5_R = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_5_R.png"));
            bullet_5_D = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_5_D.png"));
            bullet_5_X = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_5_X.png"));

            bullet_6_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_6_L.png"));
            bullet_6_U = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_6_U.png"));
            bullet_6_R = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_6_R.png"));
            bullet_6_D = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_6_D.png"));
            bullet_6_X = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_6_X.png"));

            bullet_7_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_7_L.png"));
            bullet_7_U = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_7_U.png"));
            bullet_7_R = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_7_R.png"));
            bullet_7_D = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_7_D.png"));
            bullet_7_X = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_7_X.png"));


            bullet_8_L = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_8_L.png"));
            bullet_8_U = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_8_U.png"));
            bullet_8_R = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_8_R.png"));
            bullet_8_D = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_8_D.png"));
            bullet_8_X = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/bullet_8_X.png"));


            explode_1 = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/explode_1.png")), 7, 1, 7);


            for (int i = 0; i < 3; i++) {
                explode_2[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/explode_2_" + (i + 1) + ".png"));
            }

            explode_3 = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/explode_3.png")), 10, 6, 60);

            explode_4 = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/explode_4.png")), 9, 1, 9);

            explode_5 = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/explode_5.png")), 4, 4, 16);

            explode_6 = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/explode_6.png")), 4, 5, 20);

            explode_7 = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/explode_7.png")), 3, 3, 9);

            explode_8 = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/explode_8.png")), 3, 3, 9);

            explode_9 = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/explode_9.png")), 3, 3, 9);

            map_steel = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map_steel.png"));
            map_wall = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map_wall.png"));
            map_water = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map_water.png"));
            map_grass = ImageUtil.cropImage(ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map_grass.png")), 3, 3, 9);
            map_medkit = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map_medkit.png"));
            map_love = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map_love.png"));
            map_portals_1 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map_portals_1.png"));
            map_crystal[0] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map_crystal_blue.png"));
            map_crystal[1] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map_crystal_red.png"));
            map_crystal[2] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map_crystal_color.png"));
            freeze = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/freeze.png"));
            poison = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/poison.png"));
            pene = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/pene.png"));
            map1 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map1.png"));
            map2 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map2.png"));
            map3 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("image/map3.png"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
