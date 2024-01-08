package com.joker.tank;


import com.joker.tank.screen.TankFrame;

/**
* @author 燧枫
* @date 2022/12/13 14:50
*/
public class TankClientApplication {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();
        while (true) {
            Thread.sleep(15);
            tf.repaint();
        }
    }

}
