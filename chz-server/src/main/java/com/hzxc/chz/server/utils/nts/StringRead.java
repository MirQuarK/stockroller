package com.hzxc.chz.server.utils.nts;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;
import io.netty.channel.ChannelHandler.Sharable;

@Component
@Sharable
public class StringRead extends SimpleChannelInboundHandler<String> {
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        ByteBuf buf = Unpooled.buffer(s.getBytes().length);
        buf.writeBytes(s.getBytes());
        channelHandlerContext.writeAndFlush(buf);
        channelHandlerContext.close();
        return;
    }
}
