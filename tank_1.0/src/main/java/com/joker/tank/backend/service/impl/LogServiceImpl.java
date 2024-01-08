package com.joker.tank.backend.service.impl;

import com.joker.tank.backend.entity.dos.Log;
import com.joker.tank.backend.entity.dos.User;
import com.joker.tank.backend.mapper.LogMapper;
import com.joker.tank.backend.service.LogService;
import com.joker.tank.backend.utils.SnowFlakeUtil;

import java.util.List;

/**
 * @author 燧枫
 * @date 2022/12/29 10:19
*/
public class LogServiceImpl implements LogService {

    LogMapper logMapper = new LogMapper();

    /**
     * 添加一条日志
     * @param content
     * @return Log
     */
    @Override
    public Log addOneLog(String content) {
        // 先封装一个Log对象
        Log log = new Log();
        log.setContent(content);
        // 将此数据插入数据库中
        logMapper.insert(log);
        return log;
    }

    /**
     * 获取全部的日志
     * @return List<Log>
     */
    @Override
    public List<Log> getAllLog() {
        List<Log> logList = logMapper.selectAllByDeleteFlag();
        return logList;
    }
}
