package com.joker.tank.backend.utils;

import com.joker.tank.backend._frame.ResultMessage;

/**
 * @author 燧枫
 * @date 2022/12/21 20:18
*/
// 返回结果工具类
public class ResultUtil<T> {

    // 抽象类，存放结果
    private final ResultMessage<T> resultMessage;

    // 构造方法，给响应结果默认值
    public ResultUtil() {
        resultMessage = new ResultMessage<>();
        resultMessage.setSuccess(true);
        resultMessage.setMessage("success");
    }

    /**
     * 抽象静态方法，返回结果集
     * @param t 范型
     * @param <T>  范型
     * @return 消息
     */
    public static <T> ResultMessage<T> data(T t) {
        return new ResultUtil<T>().setData(t);
    }

    /**
     * 返回成功
     * @return 消息
     */
    public static <T> ResultMessage<T> success(String msg) {
        return new ResultUtil<T>().setSuccessMsg(msg);
    }

    /**
     * 返回失败
     * @return 消息
     */
    public static <T> ResultMessage<T> error(String msg) {
        return new ResultUtil<T>().setErrorMsg(msg);
    }

    /**
     * 返回数据
     * @param t 范型
     * @return 消息
     */
    public ResultMessage<T> setData(T t) {
        this.resultMessage.setResult(t);
        return this.resultMessage;
    }

    /**
     * 返回成功消息
     * @return 返回成功消息
     */
    public ResultMessage<T> setSuccessMsg(String msg) {
        this.resultMessage.setSuccess(true);
        this.resultMessage.setMessage(msg);
        return this.resultMessage;
    }

    /**
     * 返回失败消息
     * @return 返回失败消息
     */
    public ResultMessage<T> setErrorMsg(String msg) {
        this.resultMessage.setSuccess(false);
        this.resultMessage.setMessage(msg);
        return this.resultMessage;

    }
}
