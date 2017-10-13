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

@MessageDriven(name = "StructureAssignmentListener", activationConfig = { //
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "stratagem-assignment-notification-queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
		//
})
public class StructureAssignmentListener extends AbstractDevelopmentMessageRouter implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(StructureAssignmentListener.class);

	@PostConstruct
	public void initialize() {
		LOGGER.info("Structure Assignment Listener initialized");
	}

	@Override
	public void onMessage(final Message message) {
		try {
			if (this.certified(message, Constants.ASSIGNMENT_SELECTOR)) {
				final TextMessage textMessage = (TextMessage) message;
				final String content = textMessage.getText();
				final String[] partitions = content.split(Pattern.quote(Constants.PAYLOAD_SEPARATOR));
				final String structureSelector = partitions[0];
				switch (structureSelector) {
					case Constants.APP_USER_OBJECTIVE_ASSIGNMENT_REPRESENTOR_DATA_LABEL:
						this.objectiveProcessor.processAssignment(partitions[1]);
						break;
					case Constants.APP_USER_PROJECT_ASSIGNMENT_REPRESENTOR_DATA_LABEL:
						this.projectProcessor.processAssignment(partitions[1]);
						break;
					case Constants.APP_USER_SUBMODULE_ASSIGNMENT_REPRESENTOR_DATA_LABEL:
						this.submoduleProcessor.processAssignment(partitions[1]);
						break;
					case Constants.APP_USER_TASK_ASSIGNMENT_REPRESENTOR_DATA_LABEL:
						this.taskProcessor.processAssignment(partitions[1]);
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
