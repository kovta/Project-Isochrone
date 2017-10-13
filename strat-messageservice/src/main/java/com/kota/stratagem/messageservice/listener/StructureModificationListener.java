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

import com.kota.stratagem.messageservice.exception.InvalidStructureTypeException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.util.Constants;

@MessageDriven(name = "StructureModificationListener", activationConfig = { //
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "stratagem-modification-notification-queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
		//
})
public class StructureModificationListener extends AbstractDevelopmentMessageRouter implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(StructureModificationListener.class);

	@PostConstruct
	public void initialize() {
		LOGGER.info("Structure Modification Listener initialized");
	}

	@Override
	public void onMessage(final Message message) {
		try {
			if (this.certified(message, Constants.UPDATE_SELECTOR)) {
				final TextMessage textMessage = (TextMessage) message;
				final String content = textMessage.getText();
				final String[] partitions = content.split(Pattern.quote(Constants.PAYLOAD_SEPARATOR));
				final String structureSelector = partitions[0];
				switch (structureSelector) {
					case Constants.OBJECTIVE_REPRESENTOR_DATA_LABEL:
						this.objectiveProcessor.processModification(partitions[1], partitions[3]);
						break;
					case Constants.PROJECT_REPRESENTOR_DATA_LABEL:
						this.projectProcessor.processModification(partitions[1], partitions[3]);
						break;
					case Constants.SUBMODULE_REPRESENTOR_DATA_LABEL:
						this.submoduleProcessor.processModification(partitions[1], partitions[3]);
						break;
					case Constants.TASK_REPRESENTOR_DATA_LABEL:
						this.taskProcessor.processModification(partitions[1], partitions[3]);
						break;
					default:
						if(LOGGER.isDebugEnabled()) {
							LOGGER.debug("Invalid structureType: " + structureSelector + "!");
						}
						throw new InvalidStructureTypeException();
				}
			}
		} catch (final JMSException | PersistenceServiceException | NumberFormatException | InvalidStructureTypeException e) {
			LOGGER.error(e, e);
		}
	}

}
