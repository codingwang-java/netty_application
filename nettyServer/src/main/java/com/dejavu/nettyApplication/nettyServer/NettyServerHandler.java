package com.dejavu.nettyApplication.nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private NettyServer nettyServer;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("server received:" + msg.toString());
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        result.readBytes(result1);
        String resultStr = new String(result1);
        // 接收并打印客户端的信息
        System.out.println("Client said:" + resultStr);
        // 释放资源，这行很关键
        result.release();
        // 向客户端发送消息
        String response = "hello client!";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.writeAndFlush(encoded);
        String ss = "hello client!";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf dd = ctx.alloc().buffer(4 * ss.length());
        encoded.writeBytes(response.getBytes());
        ctx.writeAndFlush(dd);
        ChannelGroupManager.channelGroups.add(channel);
        System.out.println("add channel"+ChannelGroupManager.channelGroups.toString());

    }
}
