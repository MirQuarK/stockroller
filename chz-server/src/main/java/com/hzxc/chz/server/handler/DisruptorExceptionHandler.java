package com.hzxc.chz.server.handler;

import com.lmax.disruptor.ExceptionHandler;

/**
 * disruptor 异常处理的实现，目前未做任何处理
 */
public class DisruptorExceptionHandler implements ExceptionHandler<Object> {
    @Override
    public void handleEventException(Throwable th, long l, Object o) {
        th.printStackTrace();
        //TODO
    }

    @Override
    public void handleOnStartException(Throwable th) {
        th.printStackTrace();
        //TODO
    }

    @Override
    public void handleOnShutdownException(Throwable th) {
        th.printStackTrace();
        //TODO
    }
}
