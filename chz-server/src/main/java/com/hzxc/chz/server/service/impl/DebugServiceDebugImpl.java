package com.hzxc.chz.server.service.impl;

import com.hzxc.groupactivity.server.service.DebugService;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("DebugService")
@Profile(value =  {"local","dev","test",""})
public class DebugServiceDebugImpl implements DebugService{
    @Override
    public void debug(Logger logger, String var1, Object... var2) {
        logger.debug(var1, var2);
    }

    @Override
    public void debug(Logger logger, String var1) {
        logger.debug(var1);
    }
}
