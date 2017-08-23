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

import com.kota.stratagem.persistence.util.Constants;

@MessageDriven(name = "CreationListener", activationConfig = { //
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "stratagem-creation-notification-queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
		//
})
public class StructureCreationListener extends AbstractDevelopmentMessageRouter implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(StructureCreationListener.class);

	@PostConstruct
	public void initialize() {
		LOGGER.info("Creation Listener initialized");
	}

	@Override
	public void onMessage(final Message message) {
		try {
			if (this.handleMessage(message, Constants.CREATION_SELECTOR)) {
				final TextMessage textMessage = (TextMessage) message;
				final String content = textMessage.getText();
				final String[] partitions = content.split(Pattern.quote(Constants.PAYLOAD_SEPARATOR));
				final String structureSelector = partitions[0];
				switch (structureSelector) {
					case Constants.OBJECTIVE_DATA_NAME + Constants.REPRESENTOR_DATA_NAME:
						this.objectiveProcessor.processCreation(partitions[1]);
						break;
					case Constants.PROJECT_DATA_NAME + Constants.REPRESENTOR_DATA_NAME:
						this.projectProcessor.processCreation(partitions[1]);
						break;
					case Constants.SUBMODULE_DATA_NAME + Constants.REPRESENTOR_DATA_NAME:
						this.submoduleProcessor.processCreation(partitions[1]);
						break;
					case Constants.TASK_DATA_NAME + Constants.REPRESENTOR_DATA_NAME:
						this.taskProcessor.processCreation(partitions[1]);
						break;
					default:
						break;
				}
			}
		} catch (final JMSException | NumberFormatException e) {
			LOGGER.error(e, e);
		}
	}

}