package com.example.devicesspring.config;

    import org.springframework.amqp.core.Binding;
    import org.springframework.amqp.core.BindingBuilder;
    import org.springframework.amqp.core.DirectExchange;
    import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue userDeletedQueue() {
        return new Queue("user_deleted", false);  // Non-durable queue
    }

    @Bean
    public Queue deviceCreatedQueue() {
        return new Queue("device_created", false);
    }

    @Bean
    public Queue deviceUpdatedQueue() {
        return new Queue("device_updated", false);
    }

    @Bean
    public Queue deviceDeletedQueue() {
        return new Queue("device_deleted", false);
    }


    @Bean
    public DirectExchange deviceExchange() {
        return new DirectExchange("deviceExchange"); // Define a direct exchange
    }


    @Bean
    public Binding bindingDeviceCreatedQueue(Queue deviceCreatedQueue, DirectExchange deviceExchange) {
        return BindingBuilder.bind(deviceCreatedQueue).to(deviceExchange).with("device_created"); // Bind queue to exchange with the routing key
    }

    @Bean
    public Binding bindingDeviceUpdatedQueue(Queue deviceUpdatedQueue, DirectExchange deviceExchange) {
        return BindingBuilder.bind(deviceUpdatedQueue).to(deviceExchange).with("device_updated");
    }

    @Bean
    public Binding bindingDeviceDeletedQueue(Queue deviceDeletedQueue, DirectExchange deviceExchange) {
        return BindingBuilder.bind(deviceDeletedQueue).to(deviceExchange).with("device_deleted");
    }

    @Bean
    public Queue mapedDeviceQueue() {
        return new Queue("maped_device", false);
    }

    @Bean
    public Queue mapedDeviceDeletedQueue() {
        return new Queue("maped_device_deleted", false);
    }


    @Bean
    public Binding bindingMapedDeviceQueue(Queue mapedDeviceQueue, DirectExchange deviceExchange) {
        return BindingBuilder.bind(mapedDeviceQueue).to(deviceExchange).with("maped_device");
    }

    @Bean
    public Binding bindingMapedDeviceDeletedQueue(Queue mapedDeviceDeletedQueue, DirectExchange deviceExchange) {
        return BindingBuilder.bind(mapedDeviceDeletedQueue).to(deviceExchange).with("maped_device_deleted");
    }
}

