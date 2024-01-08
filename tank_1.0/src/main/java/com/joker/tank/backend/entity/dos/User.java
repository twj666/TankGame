package com.joker.tank.backend.entity.dos;

import com.joker.tank.backend._frame.Annontation.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 燧枫
 * @date 2022/12/21 19:59
*/
// 用户类
@Data
@Table(TableName = "tk_user")
public class User implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 删除标记
     */
    private int deleteFlag;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private int sex;

    /**
     * 钻石数量
     */
    private int diamond;

    /**
     * 游戏总场次
     */
    private int gameNum;

}
