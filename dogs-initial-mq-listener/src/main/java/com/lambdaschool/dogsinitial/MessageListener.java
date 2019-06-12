package com.lambdaschool.dogsinitial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);


    @RabbitListener(queues = DogsinitialApplication.QUEUE_NAME_HIGH)
    public void receiveMessage(MessageDetail messageDetail) {
        logger.info("Received high priority message= " + messageDetail.toString());
    }

    @RabbitListener(queues = DogsinitialApplication.QUEUE_NAME_LOW)
    public void receiveLowPriorityMessage(MessageDetail messageDetail) {
        logger.info("Received low priority message= " + messageDetail.toString());
    }
}
