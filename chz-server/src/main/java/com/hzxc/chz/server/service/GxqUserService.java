package com.hzxc.chz.server.service;

import com.hzxc.chz.entity.GxqUser;

/**
 * create by chz on 2017/11/9
 */
public interface GxqUserService {
    GxqUser getUserById (int id);
    GxqUser getUserByMobile (String mobile);
    void saveUser(GxqUser user);

    /**
     * 刷新用户信息
     * @param userId
     */
    void flushUser(GxqUser userId);

    int getShortMsgCount (int userId);
}
