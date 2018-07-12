package com.hzxc.chz.server.utils.nts;

// 记录调用方法的元信息的类
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 多线程共享
 */
@Component
@Sharable
public class ntsChannelAdapter extends ChannelHandlerAdapter {
    /**
     * 日志处理
     */
    private Logger logger = LoggerFactory.getLogger(ntsChannelAdapter.class);
    /**
     * 注入请求分排器
     */
    @Resource
    private ntsDispatcher dispatcher;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.debug((String) msg);
    }
}