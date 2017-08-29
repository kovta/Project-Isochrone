package com.kota.stratagem.messageservice.listener;

import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.util.Constants;

@MessageDriven(name = "StructureDissociationListener", activationConfig = { //
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "stratagem-dissociation-notification-queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
		//
})
public class StructureDissociationListener extends AbstractDevelopmentMessageRouter implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(StructureDissociationListener.class);

	@PostConstruct
	public void initialize() {
		LOGGER.info("Structure Dissociation Listener initialized");
	}

	@Override
	public void onMessage(Message message) {
		try {
			if (this.certified(message, Constants.DISSOCIATION_SELECTOR)) {
				final TextMessage textMessage = (TextMessage) message;
				final String content = textMessage.getText();
				final String[] partitions = content.split(Pattern.quote(Constants.PAYLOAD_SEPARATOR));
				final String structureSelector = partitions[0];
				switch (structureSelector) {
					case Constants.APP_USER_OBJECTIVE_ASSIGNMENT_REPRESENTOR_DATA_NAME:
						this.objectiveProcessor.processDissociation(partitions[1]);
						break;
					case Constants.APP_USER_PROJECT_ASSIGNMENT_REPRESENTOR_DATA_NAME:
						this.projectProcessor.processDissociation(partitions[1]);
						break;
					case Constants.APP_USER_SUBMODULE_ASSIGNMENT_REPRESENTOR_DATA_NAME:
						this.submoduleProcessor.processDissociation(partitions[1]);
						break;
					case Constants.APP_USER_TASK_ASSIGNMENT_REPRESENTOR_DATA_NAME:
						this.taskProcessor.processDissociation(partitions[1]);
						break;
					default:
						LOGGER.info("Invalid Structure type received in queue");
						break;
				}
			}
		} catch (final JMSException | PersistenceServiceException | NumberFormatException e) {
			LOGGER.error(e, e);
		}
	}

}
