package com.kota.stratagem.messageservice.listener;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.kota.stratagem.messageservice.processing.ObjectiveDevelopmentProcessor;
import com.kota.stratagem.messageservice.processing.ProjectDevelopmentProcessor;
import com.kota.stratagem.messageservice.processing.SubmoduleDevelopmentProcessor;
import com.kota.stratagem.messageservice.processing.TaskDevelopmentProcessor;

public class AbstractDevelopmentMessageRouter {

	private static final Logger LOGGER = Logger.getLogger(AbstractDevelopmentMessageRouter.class);

	@EJB
	protected ObjectiveDevelopmentProcessor objectiveProcessor;

	@EJB
	protected ProjectDevelopmentProcessor projectProcessor;

	@EJB
	protected SubmoduleDevelopmentProcessor submoduleProcessor;

	@EJB
	protected TaskDevelopmentProcessor taskProcessor;

	public boolean handleMessage(final Message message, final String operationSelector) throws JMSException {
		if (LOGGER.isDebugEnabled()) {
			final Queue destination = (Queue) message.getJMSDestination();
			final String queueName = destination.getQueueName();
			LOGGER.debug("New JMS message arrived into " + queueName + " queue (correlation id: " + message.getJMSCorrelationID() + ", operation: "
					+ operationSelector + ")");
		}
		if (message instanceof TextMessage) {
			final TextMessage textMessage = (TextMessage) message;
			final String content = textMessage.getText();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Received message: " + content);
			}
			return true;
		} else {
			LOGGER.error("Unproccessable message type recieved (" + message.getClass() + ")");
			return false;
		}
	}

}
