package com.dejavu.nettyApplication.nettyServer;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
/**
 * description：
 *
 * @ClassName ChannelGroupManager
 * @Description netty管理
 * @Author DEJAVU
 * @Date 2021/2/2 19:28
 * @Version 1.0
 */
public class ChannelGroupManager {
    public static ConcurrentHashMap<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();
    public static ChannelGroup channelGroups = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}