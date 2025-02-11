package br.com.fiap.clipshot.core.infraestructure.queue;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class QueueConfiguration {

    private FailedVideoConsumer failedVideoConsumer;

    public QueueConfiguration(FailedVideoConsumer failedVideoConsumer) {
        this.failedVideoConsumer = failedVideoConsumer;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageListenerAdapter messageListener(MessageConverter messageConverter) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(failedVideoConsumer);
        messageListenerAdapter.setMessageConverter(messageConverter);
        return messageListenerAdapter;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
                                                             MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(messageListenerAdapter);
        container.setQueueNames("notification-queue");
        container.setConcurrentConsumers(5);
        container.setMaxConcurrentConsumers(10);
//        container.setErrorHandler(new VideoErrorHandler(videoTaskGateway, notificationGateway));
        return container;
    }

    @Bean
    public VideoQueueProducer notificationProducer(RabbitTemplate rabbitTemplate) {
        return new VideoQueueProducer(rabbitTemplate);
    }
}