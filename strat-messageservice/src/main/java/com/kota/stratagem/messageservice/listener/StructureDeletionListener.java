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

@MessageDriven(name = "StructureDeletionListener", activationConfig = { //
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "stratagem-deletion-notification-queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
		//
})
public class StructureDeletionListener extends AbstractDevelopmentMessageRouter implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(StructureDeletionListener.class);

	@PostConstruct
	public void initialize() {
		LOGGER.info("Structure Deletion Listener initialized");
	}

	@Override
	public void onMessage(final Message message) {
		try {
			if (this.certified(message, Constants.DELETION_SELECTOR)) {
				final TextMessage textMessage = (TextMessage) message;
				final String content = textMessage.getText();
				final String[] partitions = content.split(Pattern.quote(Constants.PAYLOAD_SEPARATOR));
				final String structureSelector = partitions[0];
				switch (structureSelector) {
					case Constants.OBJECTIVE_REPRESENTOR_DATA_NAME:
						this.objectiveProcessor.processDeletion(partitions[1], partitions[2]);
						break;
					case Constants.PROJECT_REPRESENTOR_DATA_NAME:
						this.projectProcessor.processDeletion(partitions[1], partitions[2]);
						break;
					case Constants.SUBMODULE_REPRESENTOR_DATA_NAME:
						this.submoduleProcessor.processDeletion(partitions[1], partitions[2]);
						break;
					case Constants.TASK_REPRESENTOR_DATA_NAME:
						this.taskProcessor.processDeletion(partitions[1], partitions[2]);
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
