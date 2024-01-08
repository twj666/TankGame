package com.joker.tank.backend.service;

import com.joker.tank.backend.entity.dos.Log;

import java.util.List;

/**
 * @author 燧枫
 * @date 2022/12/29 10:18
*/
public interface LogService {

    Log addOneLog(String content);

    List<Log> getAllLog();
}
