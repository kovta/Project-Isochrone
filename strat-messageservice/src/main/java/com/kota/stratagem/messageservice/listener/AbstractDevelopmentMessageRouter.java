package com.kota.stratagem.messageservice.listener;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.kota.stratagem.messageservice.processing.DependencyExtendedDevelopmentProcessor;
import com.kota.stratagem.messageservice.processing.DevelopmentProcessor;
import com.kota.stratagem.messageservice.qualifier.ObjectiveOriented;
import com.kota.stratagem.messageservice.qualifier.ProjectOriented;
import com.kota.stratagem.messageservice.qualifier.SubmoduleOriented;
import com.kota.stratagem.messageservice.qualifier.TaskOriented;

public class AbstractDevelopmentMessageRouter {

	private static final Logger LOGGER = Logger.getLogger(AbstractDevelopmentMessageRouter.class);

	@Inject
	@ObjectiveOriented
	protected DevelopmentProcessor objectiveProcessor;

	@Inject
	@ProjectOriented
	protected DevelopmentProcessor projectProcessor;

	@Inject
	@SubmoduleOriented
	protected DevelopmentProcessor submoduleProcessor;

	@Inject
	@TaskOriented
	protected DependencyExtendedDevelopmentProcessor taskProcessor;

	protected boolean certified(final Message message, final String operationSelector) throws JMSException {
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
