package com.goose.codefiddle;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public void create(String destinationName) throws JMSException {
        //creaate connection factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

        //create connection
        connection = connectionFactory.createConnection();

        //create session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //create destination to which message is sent
        Destination destination = session.createQueue(destinationName);

        // create a message producer for sending messages
        messageProducer = session.createProducer(destination);        
        
    }

    /*
    * @Description: close connection
    */
    public void close() throws JMSException {
        connection.close();
    }

    /**
     * @throws JMSException
     * @Description: prepare message
     */
    public void sendName(String fName, String lName) throws JMSException {
        String name = fName + " " + lName;

        // create a JMS Text message
        TextMessage txtMsg = session.createTextMessage(name);

        //send the message
        messageProducer.send(txtMsg);
        
        logger.debug("producer sent message.");
    }

}
