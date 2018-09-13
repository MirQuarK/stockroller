package com.hzxc.chz.server.service;

import org.slf4j.Logger;

public interface DebugService {
    void debug (Logger logger, String var1, Object... var2);
    void debug (Logger logger, String var1);
}
