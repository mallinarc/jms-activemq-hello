package com.goose.codefiddle;

import static org.junit.Assert.assertEquals;

import javax.jms.JMSException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerTest {
    private static final Logger logger = LoggerFactory.getLogger(ProducerTest.class);
    private static Producer producer;
    private static Consumer consumer;

    @BeforeClass
    public static void setUpBefore() throws JMSException {
        producer = new Producer();
        consumer = new Consumer();

        producer.create("HelloWorld.q");
        consumer.create("HelloWorld.q");
    }

    @AfterClass
    public static void tearDownClass() throws JMSException {
        producer.close();
        consumer.close();
    }

  @Test
  public void testGetGreeting() {
    try {
      producer.sendName("John", "Doe");

      String greeting = consumer.getGreeting(1000);
      assertEquals("Hello John Doe", greeting);

    } catch (JMSException e) {
      logger.error("a JMS Exception occurred"+ e);
    }
  }

  @Test
  public void testNoGreeting() {
    try {
      String greeting = consumer.getGreeting(1000);
      assertEquals("no greeting", greeting);

    } catch (JMSException e) {
      logger.error("a JMS Exception occurred"+e);
    }
  }
}
