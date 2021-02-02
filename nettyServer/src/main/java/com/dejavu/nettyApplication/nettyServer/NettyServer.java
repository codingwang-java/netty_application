package com.dejavu.nettyApplication.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;
/**
 * description：
 *
 * @ClassName NettyServer
 * @Description TODO
 * @Author DEJAVU
 * @Date 2021/2/1 0:26
 * @Version 1.0
 */
public class NettyServer {
    private final int port;
    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private final EventLoopGroup group = new NioEventLoopGroup();
    final NettyServerHandler serverHandler = new NettyServerHandler();

    public NettyServer(int port) {
        this.port = port;
    }

    
    public void start() throws InterruptedException {
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        channelPipeline.addLast(new IdleStateHandler(300, 60, 0));
                        channelPipeline.addLast(serverHandler);
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture channelFuture = bootstrap.bind(port).sync();
        //channelFuture.channel().closeFuture().sync();
    }

    public void  sendMessage() throws InterruptedException {
        ChannelGroup channelGroup = ChannelGroupManager.channelGroups;
        while (true){
            if(channelGroup.size()>0) {
                Thread.sleep(2000);
                channelGroup.forEach(channel -> {
                    String response = "hello client!";
                    // 在当前场景下，发送的数据必须转换成ByteBuf数组
                    ByteBuf encoded = channel.alloc().buffer(4 * response.length());
                    encoded.writeBytes(response.getBytes());
                    System.out.println("send message" + response);
                    channel.writeAndFlush(encoded);
                });
            }
        }
    }
}
