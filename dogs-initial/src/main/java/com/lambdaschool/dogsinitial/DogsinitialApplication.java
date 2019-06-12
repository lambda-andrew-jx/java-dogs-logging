package com.lambdaschool.dogsinitial;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collection;
import java.util.Iterator;

@EnableScheduling
@EnableWebMvc
@SpringBootApplication
public class DogsinitialApplication
{
    public static final String EXCHANGE_NAME = "LambaServer";
    public static final String QUEUE_NAME_LOW = "LowPriorityQueue";
    public static final String QUEUE_NAME_HIGH = "HighPriorityQueue";


    public static DogList ourDogList;
    public static void main(String[] args)
    {
        ourDogList = new DogList();
        ApplicationContext ctx = SpringApplication.run(DogsinitialApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

    @Bean
    public TopicExchange appExchange()
    {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public org.springframework.amqp.core.Queue appQueueLow()
    {
        return new org.springframework.amqp.core.Queue(QUEUE_NAME_LOW);
    }

    @Bean
    public Binding declareBindingLow()
    {
        return BindingBuilder.bind(appQueueLow()).to(appExchange()).with(QUEUE_NAME_LOW);
    }

    @Bean
    public org.springframework.amqp.core.Queue appQueueHigh()
    {
        return new Queue(QUEUE_NAME_HIGH);
    }

    @Bean
    public Binding declareBindingHigh()
    {
        return BindingBuilder.bind(appQueueHigh()).to(appExchange()).with(QUEUE_NAME_HIGH);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

}

