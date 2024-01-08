package com.joker.tank.game.manager;

import java.io.IOException;
import java.util.Properties;

/**
 * @author 燧枫
 * @date 2022/12/13 14:54
*/
public class PropertyMgr {

    private PropertyMgr() {
    }

    static Properties properties = new Properties();

    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("tank.config"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getInt(String key) {
        if (properties == null) return 0;
        return Integer.parseInt((String) properties.get(key));
    }

    public static String getString(String key) {
        if (properties == null) return "0";
        return (String) properties.get(key);
    }

}
