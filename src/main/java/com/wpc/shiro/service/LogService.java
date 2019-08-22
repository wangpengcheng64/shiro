package com.wpc.shiro.service;

import com.wpc.shiro.bean.Log;
import com.wpc.shiro.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 王鹏程
 * @Date: 2019/2/28 0028
 * @Time: 下午 10:46
 */
@Service
public class LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 保存日志
     * @param log
     */
    public void save(Log log) {
        logMapper.insertSelective(log);
    }
}
