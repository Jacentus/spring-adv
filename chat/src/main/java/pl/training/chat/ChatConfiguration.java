package pl.training.chat;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.session.hazelcast.config.annotation.SpringSessionHazelcastInstance;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.session.hazelcast.SessionUpdateEntryProcessor;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableHazelcastHttpSession
@EnableCaching(order = 10_000)
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

    @Bean
    public ProducerFactory<String, ChatMessageDto> producerFactory() {
        var serializer = new JsonSerializer<ChatMessageDto>();
        serializer.setAddTypeInfo(true);
        var properties = new HashMap<String, Object>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new DefaultKafkaProducerFactory<>(properties, new StringSerializer(), serializer);
    }

    @Bean
    public ConsumerFactory<String, ChatMessageDto> consumerFactory() {
        var deserializer = new JsonDeserializer<ChatMessageDto>();
        deserializer.addTrustedPackages("pl.training.chat");
        var properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "chat");
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
    }

    @Bean
    public NewTopic mainTopic() {
        return TopicBuilder.name("messages").build();
    }

    @Bean
    public KafkaTemplate<String, ChatMessageDto> kafkaTemplate(ProducerFactory<String, ChatMessageDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ChatMessageDto>> kafkaListenerContainerFactory(ConsumerFactory<String, ChatMessageDto> consumerFactory) {
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, ChatMessageDto>();
        containerFactory.setConsumerFactory(consumerFactory);
        return containerFactory;
    }

    @Bean
    public ConnectionFactory connectionFactory() throws JMSException {
        var connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        var template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(true); // topic
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory trainingJmsContainerFactory(ConnectionFactory connectionFactory) {
        var container = new DefaultJmsListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        container.setConcurrency("1-10");
        container.setPubSubDomain(true); // topic
        return container;
    }

    /*@Bean
    public CacheManager cacheManager() {
        return new TransactionAwareCacheManagerProxy(new ConcurrentMapCacheManager("calculations"));
    }*/

    /*@Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        var config= RedisCacheConfiguration.defaultCacheConfig();
        config.usePrefix();
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }*/

    /*@Bean
    public HazelcastInstance hazelcastInstance() {
        var config = new Config();
        config.getNetworkConfig()
                .setPortAutoIncrement(true)
                .getJoin()
                .getMulticastConfig()
                .setMulticastPort(20_000)
                .setEnabled(true);
        return Hazelcast.newHazelcastInstance(config);
    }*/

    @Bean
    @SpringSessionHazelcastInstance
    public HazelcastInstance hazelcastInstance() {
        var config = new ClientConfig();
        config.setClusterName("training");
        config.getNetworkConfig()
                .addAddress("localhost:5701");

        config.getUserCodeDeploymentConfig()
                .setEnabled(true)
                .addClass(Session.class)
                .addClass(MapSession.class)
                .addClass(SessionUpdateEntryProcessor.class);
        return HazelcastClient.newHazelcastClient(config);
    }

    /*@Bean
    public CacheManager cacheManager(HazelcastInstance customHazelcastInstance) {
        //var config = new ClientConfig();
        return new HazelcastCacheManager(customHazelcastInstance);
    }*/

    // https://docs.hazelcast.com/tutorials/spring-session-hazelcast

}

