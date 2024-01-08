package com.joker.tank.backend.controller;

import com.joker.tank.backend._frame.ResultMessage;
import com.joker.tank.backend.entity.dos.Log;
import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.backend.mapper.LogMapper;
import com.joker.tank.backend.service.LogService;
import com.joker.tank.backend.service.impl.LogServiceImpl;
import com.joker.tank.backend.utils.ResultUtil;

import java.util.List;

/**
 * @author 燧枫
 * @date 2022/12/29 10:19
*/
public class LogController {

    private LogService logService = new LogServiceImpl();

    /**
     * 添加一条日志
     * @param content
     * @return Log
     */
    public ResultMessage addOneLog(String content) {
        try {
            Log log = logService.addOneLog(content);
            return ResultUtil.data(log);
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }

    /**
     * 获取全部日志
     * @param content
     * @return Log的List
     */
    public ResultMessage getAllLog() {
        try {
            List<Log> logList = logService.getAllLog();
            return ResultUtil.data(logList);
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
    }
}
