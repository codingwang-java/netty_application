package com.dejavu.nettyApplication.nettyServer;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelGroupManager {
    public static ConcurrentHashMap<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();
    public static ChannelGroup channelGroups = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}