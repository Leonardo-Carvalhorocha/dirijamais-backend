package dirijamais.project.dirijamais.aplicacao.configuracoes;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.amqp.core.Queue;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String EXCHANGE = "whatsapp.exchange";
    public static final String QUEUE_SEND = "whatsapp.send.queue";
    public static final String QUEUE_DLQ = "whatsapp.send.dlq";
    public static final String ROUTING_SEND = "whatsapp.send";
    public static final String ROUTING_DLQ = "whatsapp.send.dlq";

    @Bean
    DirectExchange whatsappExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    Queue whatsappSendQueue() {
        return QueueBuilder.durable(QUEUE_SEND)
            .withArgument("x-dead-letter-exchange", EXCHANGE)
            .withArgument("x-dead-letter-routing-key", ROUTING_DLQ)
            .build();
    }

    @Bean
    Queue whatsappDLQ() {
        return QueueBuilder.durable(QUEUE_DLQ).build();
    }

    @Bean
    Binding sendQueueBinding() {
        return BindingBuilder
            .bind(whatsappSendQueue())
            .to(whatsappExchange())
            .with(ROUTING_SEND);
    }

    @Bean
    Binding dlqBinding() {
        return BindingBuilder
            .bind(whatsappDLQ())
            .to(whatsappExchange())
            .with(ROUTING_DLQ);
    }

      @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter converter
    ) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}
