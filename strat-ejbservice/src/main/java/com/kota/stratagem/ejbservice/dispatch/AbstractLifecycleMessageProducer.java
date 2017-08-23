package com.kota.stratagem.ejbservice.dispatch;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public abstract class AbstractLifecycleMessageProducer {

	private static final Logger LOGGER = Logger.getLogger(AbstractLifecycleMessageProducer.class);

	protected static final String CONNECTION_FACTORY = "ConnectionFactory";
	protected static final String STRAT_CREATION_CONSUMER_DESTINATION = "jms/queue/stratagem-creation-notification-queue";
	protected static final String STRAT_ASSIGNMENT_CONSUMER_DESTINATION = "jms/queue/stratagem-assignment-notification-queue";
	protected static final String STRAT_DISSOCIATION_CONSUMER_DESTINATION = "jms/queue/stratagem-dissociation-notification-queue";
	protected static final String STRAT_MODIFICATION_CONSUMER_DESTINATION = "jms/queue/stratagem-modification-notification-queue";
	protected static final String STRAT_DELETION_CONSUMER_DESTINATION = "jms/queue/stratagem-deletion-notification-queue";

	protected final Context context;

	private Connection connection;
	private Session session;

	private MessageProducer creationMessageProducer;
	private MessageProducer assignmentMessageProducer;
	private MessageProducer dissociationMessageProducer;
	private MessageProducer modificationMessageProducer;
	private MessageProducer deletionMessageProducer;

	public AbstractLifecycleMessageProducer() throws NamingException {
		super();
		this.context = new InitialContext();
	}

	public boolean sendCreationTextMessage(final String message) {
		return this.sendTextMessage(message, STRAT_CREATION_CONSUMER_DESTINATION, this.creationMessageProducer);
	}

	public boolean sendAssignmentTextMessage(final String message) {
		return this.sendTextMessage(message, STRAT_ASSIGNMENT_CONSUMER_DESTINATION, this.assignmentMessageProducer);
	}

	public boolean sendDissociationTextMessage(final String message) {
		return this.sendTextMessage(message, STRAT_DISSOCIATION_CONSUMER_DESTINATION, this.dissociationMessageProducer);
	}

	public boolean sendModificationTextMessage(final String message) {
		return this.sendTextMessage(message, STRAT_MODIFICATION_CONSUMER_DESTINATION, this.modificationMessageProducer);
	}

	public boolean sendDeletionTextMessage(final String message) {
		return this.sendTextMessage(message, STRAT_DELETION_CONSUMER_DESTINATION, this.deletionMessageProducer);
	}

	private boolean sendTextMessage(final String message, final String messageConsumerDestination, MessageProducer producer) {
		boolean result = false;
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Attempting to send message: " + message);
			}
			if (this.session == null) {
				this.openSession(messageConsumerDestination);
			}
			final Destination destination = (Destination) this.context.lookup(messageConsumerDestination);
			producer = this.session.createProducer(destination);
			final TextMessage textMessage = this.session.createTextMessage(message);
			producer.send(textMessage);
			producer.close();
			result = true;
		} catch (final JMSException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The server has not received the message: " + ex.getMessage());
				LOGGER.debug("Attempting to reconnect to server");
			}
			try {
				this.openSession(messageConsumerDestination);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Successfully reconnected to server");
				}
			} catch (NamingException | JMSException e) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Failed to reestablish connection with server");
				}
			}
		} catch (final Exception e) {
			LOGGER.error(e, e);
		}
		return result;
	}

	public <T extends Serializable> boolean sendObjectMessage(final T message, final String messageConsumerDestination) {
		boolean result = false;
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Attempting to send Object message");
			}
			if (this.session == null) {
				this.openSession(messageConsumerDestination);
			}
			final ObjectMessage objectMessage = this.session.createObjectMessage(message);
			this.creationMessageProducer.send(objectMessage);
			this.creationMessageProducer.close();
			result = true;
		} catch (final JMSException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The server has not received the message: " + ex.getMessage());
				LOGGER.debug("Attempting to reconnect to server");
			}
			try {
				this.openSession(messageConsumerDestination);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Successfully reconnected to server");
				}
			} catch (NamingException | JMSException e) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Failed to reestablish connection with server");
				}
			}
		} catch (final Exception e) {
			LOGGER.error(e, e);
		}
		return result;
	}

	private void openSession(String messageConsumerDestination) throws NamingException, JMSException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Attempting to acquire destination \"" + messageConsumerDestination + "\"");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Found destination \"" + messageConsumerDestination + "\" in JNDI");
		}
		if (this.connection == null) {
			this.connection = this.initializeConnectionFactory().createConnection();
			this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			this.connection.start();
		}
	}

	private ConnectionFactory initializeConnectionFactory() throws NamingException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("--------------------- Entering JMS Producer ---------------------");
			LOGGER.debug("Attempting to acquire connection factory \"" + CONNECTION_FACTORY + "\"");
		}
		final ConnectionFactory connectionFactory = (ConnectionFactory) this.context.lookup(CONNECTION_FACTORY);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Found connection factory \"" + CONNECTION_FACTORY + "\" in JNDI");
		}
		return connectionFactory;
	}

	@Override
	protected void finalize() throws Throwable {
		if (this.context != null) {
			this.connection.close();
			this.session.close();
			this.context.close();
		}
	}

}
