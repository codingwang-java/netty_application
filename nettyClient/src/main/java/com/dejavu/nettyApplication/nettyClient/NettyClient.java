package com.dejavu.nettyApplication.nettyClient;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class NettyClient {
    private final String host;
    private final int port;
    private static ChannelHandlerContext ctx;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host,port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect().sync();
        channelFuture.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClient("127.0.0.1",9527).start();
    }
}
