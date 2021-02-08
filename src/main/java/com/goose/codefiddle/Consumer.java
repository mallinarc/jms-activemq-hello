package com.goose.codefiddle;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private static String NO_GREETING = "no greeting";

    private Connection connection;
    // private Session session;
    private MessageConsumer messageConsumer;

    public void create(String destinationName) throws JMSException {
        //create connection factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

        // create connection
        connection = connectionFactory.createConnection();

        //create session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // create destination from which message will be received
        Destination destination = session.createQueue(destinationName);

        //create message consumer for receiving messages
        messageConsumer = session.createConsumer(destination);

        // start connection
        connection.start();

    }

    public void close() throws JMSException {
        connection.close();
    }

    public String getGreeting(int timeOut) throws JMSException {

        String greeting = NO_GREETING;

        //read message
        Message message = messageConsumer.receive(timeOut);

        // check if a message was received
        if(null != message){
            TextMessage textMessage = (TextMessage) message;

            // retreive messsage content
            String text = textMessage.getText();
            logger.debug(".......consumer received message with text:{}", text);

            //create greeting
            greeting = "Hello" + " " + text;

        }else{
            logger.debug("consumer received no message.");
        }
        logger.info("Greeting={}", greeting);
        return greeting;
    }   


}
