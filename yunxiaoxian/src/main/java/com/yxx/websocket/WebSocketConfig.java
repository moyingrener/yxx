package com.yxx.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * 配置文件
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketPushHandler webSocketPushHandler;
    @Autowired
    private MyWebSocketInterceptor myWebSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //1.注册websocket
//        String websocket_url = "/websocket";    //设置websocket的地址
//        String sockjs_url = "/sockjs";           //设置sockjs的地址

        registry.addHandler(webSocketPushHandler, "/websocket").             //注册Handler
                addInterceptors(myWebSocketInterceptor).setAllowedOrigins("*");   //注册Interceptors

        registry.addHandler(webSocketPushHandler, "/sockjs")
                .addInterceptors(myWebSocketInterceptor).withSockJS();
    }

//    @Bean
//    public WebSocketHandler WebSocketPushHandler() {
//
//        return new WebSocketPushHandler();
//    }

}

