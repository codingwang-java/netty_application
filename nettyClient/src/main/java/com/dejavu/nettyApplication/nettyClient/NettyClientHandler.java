package com.dejavu.nettyApplication.nettyClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import lombok.SneakyThrows;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {
    private NettyClient nettyClient;
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        System.out.println("收到message"+msg.toString());
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        System.out.println("Server said:" + new String(result1));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty Rocks", CharsetUtil.UTF_8));

    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println(new Date() + " 服务端连接不上");
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println(new Date() + " 客户端开始重连操作...");
                if (nettyClient != null) {
                    nettyClient.start();
                }
            }
        }, 60, TimeUnit.SECONDS);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
