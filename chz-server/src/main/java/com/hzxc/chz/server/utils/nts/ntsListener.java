package com.hzxc.chz.server.utils.nts;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * 服务启动监听器
 *
 * @author 叶云轩
 */
@Component
public class ntsListener {
    /**
     * NettyServerListener 日志输出器
     *
     * @author 叶云轩 create by 2017/10/31 18:05
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ntsListener.class);
    /**
     * 创建bootstrap
     */
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    /**
     * BOSS
     */
    EventLoopGroup boss = new NioEventLoopGroup();
    /**
     * Worker
     */
    EventLoopGroup work = new NioEventLoopGroup();

    /**
     * 关闭服务器方法
     */
    @PreDestroy
    public void close() {
        LOGGER.info("关闭服务器....");
        //优雅......
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }

    /**
     * 开启及服务线程
     */
    @Async
    public void start() {
        // 从配置文件中(application.yml)获取服务端监听端口号
        int port = 11111;
        serverBootstrap.group(boss, work);
        serverBootstrap.channel(NioServerSocketChannel.class);
        try {
            //设置事件处理
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    LOGGER.info("new client netty");
                    ChannelPipeline pipeline = ch.pipeline();
//                    pipeline.addLast(new LengthFieldBasedFrameDecoder(1024*80, 0, 2, 0, 2));
//                    pipeline.addLast(new LengthFieldPrepender(2));
//                    pipeline.addLast(stringDecoder);
//                    pipeline.addLast(new MessageDecoder(), new MessageEncoder(), new NettyServerHandler());

//                    pipeline.addLast(new HttpRequestDecoder());
//                    ch.pipeline().addLast( new HttpObjectAggregator(65536));
//                    ch.pipeline().addLast( new HttpResponseEncoder());
//                    ch.pipeline().addLast(new ChunkedWriteHandler());
//                    ch.pipeline().addLast(new HttpServerHandler());

//                    pipeline.addLast(new StringDecode());

                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new MyServerHanlder());
                }
            });
            LOGGER.info("netty服务器在[{}]端口启动监听", port);
            ChannelFuture f = serverBootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
            return;
        } catch (InterruptedException e) {
            LOGGER.info("[出现异常] 释放资源");
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}