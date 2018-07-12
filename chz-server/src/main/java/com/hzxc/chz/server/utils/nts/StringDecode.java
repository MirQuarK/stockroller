package com.hzxc.chz.server.utils.nts;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.stereotype.Component;
import io.netty.channel.ChannelHandler.Sharable;

import java.util.List;

@Component
//@Sharable
public class StringDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        byte[] byteArray = new byte[byteBuf.capacity()];
        byteBuf.readBytes(byteArray);
        String result = new String(byteArray);


        return;
    }
}
