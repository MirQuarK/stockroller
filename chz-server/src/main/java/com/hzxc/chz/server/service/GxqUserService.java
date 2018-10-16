package com.hzxc.chz.server.service;

import com.hzxc.chz.entity.GxqUser;

/**
 * create by chz on 2017/11/9
 */
public interface GxqUserService {
    GxqUser getUserById (String prefix, int id);
    GxqUser getUserByMobile (String prefix, String mobile);
    void saveUser(String prefix, GxqUser user);

    /**
     * 刷新用户信息
     * @param userId
     */
    void flushUser(String prefix, GxqUser userId);

    int getShortMsgCount (int userId);
}
