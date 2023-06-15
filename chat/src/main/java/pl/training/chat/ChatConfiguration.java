package pl.training.chat;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableScheduling
@EnableAsync
@EnableWebSocketMessageBroker
@Configuration
public class ChatConfiguration implements WebSocketMessageBrokerConfigurer, AsyncConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/ws");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .addInterceptors(new WebSockerHandshakeInterceptor())
                .withSockJS();
    }

    /*@Override
    public Executor getAsyncExecutor() {
        return Executors.newFixedThreadPool(10);
    }*/

    @Bean
    public Executor mailExecutor() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public TaskScheduler customScheduler() {
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }

}

