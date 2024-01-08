package com.joker.tank.backend._frame;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 燧枫
 * @date 2022/12/21 20:18
*/
// 前后端交互VO
@Data
public class ResultMessage<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    // 成功标志
    private boolean success;

    // 消息
    private String message;

    // 结果对象
    private T result;
}
