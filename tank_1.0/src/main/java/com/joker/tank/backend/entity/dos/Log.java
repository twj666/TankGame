package com.joker.tank.backend.entity.dos;

import com.joker.tank.backend._frame.Annontation.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 燧枫
 * @date 2022/12/21 19:59
 */
// 日志信息类
@Data
@Table(TableName = "tk_log")
public class Log implements Serializable {

    /**
     * 日志编号id
     */
    private int id;

    /**
     * 删除标记
     */
    private int deleteFlag;

    /**
     * 日志内容
     */
    private String content;
}
