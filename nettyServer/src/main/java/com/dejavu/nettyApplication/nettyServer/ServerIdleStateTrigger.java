package com.dejavu.nettyApplication.nettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

public class ServerIdleStateTrigger extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state) {
                case READER_IDLE:
                    ctx.close();
                    System.out.println(new Date() + " 服务端长时间未收到消息，主动关闭连接!");
                    break;
                case WRITER_IDLE:
                    System.out.println(new Date() + " 发送消息【" + Message.PING.toString() + "】");
                    ctx.channel().writeAndFlush(Message.PING);
                    break;
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
