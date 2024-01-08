package com.joker.tank.backend.mapper;

import com.joker.tank.backend._frame.mapper.mysql.BaseMapperMysql;
import com.joker.tank.backend._frame.mapper.mysql.Query;
import com.joker.tank.backend.entity.dos.Log;
import com.joker.tank.backend.entity.dos.User;

import java.util.List;

/**
 * @author 燧枫
 * @date 2022/12/29 10:15
*/
public class LogMapper extends BaseMapperMysql<Log> {

    // 获取全部日志
    public List<Log> selectAllByDeleteFlag() {
        List<Log> logList = selectAll(new Query().eqOr("deleteFlag", 0));
        return logList;
    }
}
