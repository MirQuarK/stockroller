package com.hzxc.chz.server.utils.nts;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyServerHanlder extends ChannelHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel().localAddress().toString()+" channelActive");

        // 通知链接上客户端
        String str = "server connectted"+" "+new Date()+" "+ctx.channel().localAddress();
        ByteBuf buf = Unpooled.buffer(str.getBytes().length);
        buf.writeBytes(str.getBytes());
        ctx.writeAndFlush(buf);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel().localAddress().toString()+" channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        System.out.println(new Date()+" "+msg);

        //已经链接上客户端
        String str = "server receive ："+new Date()+" "+msg;
        ByteBuf buf = Unpooled.buffer(str.getBytes().length);
        buf.writeBytes(str.getBytes());
        ctx.writeAndFlush(buf);
    }

    /*
     * 在通道读取完成后会在这个方法里通知，做刷新操作
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        System.out.println("exception ：\r\n"+cause.getMessage());
    }
}