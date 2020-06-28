package com.xuegao.netty_chat_room_server.myservice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * <br/> @PackageName：com.xuegao.netty_chat_room_server.myservice
 * <br/> @ClassName：MyWebSocketService
 * <br/> @Description：
 * <br/> @author：花名：xuegao
 * <br/> @date：2020/6/17 14:12
 */
public interface MyWebSocketService {

    void handleFrame(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame);

}