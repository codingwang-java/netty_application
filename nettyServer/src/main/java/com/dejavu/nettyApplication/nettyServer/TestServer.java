package com.dejavu.nettyApplication.nettyServer;

public class TestServer {
    private static NettyServer nettyServer;
    public static void main(String[] args) throws InterruptedException {
        nettyServer = new NettyServer(9527);
        nettyServer.start();
        nettyServer.sendMessage();
    }
}
