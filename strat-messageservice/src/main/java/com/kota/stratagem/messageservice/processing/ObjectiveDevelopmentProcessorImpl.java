package com.kota.stratagem.messageservice.processing;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.messageservice.assembly.MessageAssembler;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Notification;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.NotificationService;
import com.kota.stratagem.persistence.util.Constants;

@Stateless(mappedName = "ejb/objectiveProcessor")
public class ObjectiveDevelopmentProcessorImpl implements ObjectiveDevelopmentProcessor {

	private static final Logger LOGGER = Logger.getLogger(ObjectiveDevelopmentProcessorImpl.class);

	@EJB
	private NotificationService notificationService;

	@EJB
	private AppUserService appUserService;

	@Override
	public void processCreation(String message) {
		message = message.replace("[", "");
		message = message.replace("]", "");
		final String[] attributes = message.split(", ");
		String inducer = "", name = "", priority = "", deadline = "", confidentiality = "";
		for (final String attribute : attributes) {
			final String key = attribute.split("=")[0];
			final String value = attribute.split("=").length > 1 ? attribute.split("=")[1] != null ? attribute.split("=")[1] : "" : "";
			switch (key) {
				case "creator_id":
					inducer = value;
					break;
				case "name":
					name = value;
					break;
				case "priority":
					priority = value;
					break;
				case "deadline":
					deadline = value;
					break;
				case "confidentiality":
					confidentiality = value;
					break;
				default:
					break;
			}
		}
		try {
			final Notification notification = this.notificationService.create(Long.parseLong(inducer),
					MessageAssembler.buildCreationMessage(Constants.OBJECTIVE_DATA_NAME, name, priority, deadline, confidentiality));
			for (final AppUser user : this.appUserService.readAll()) {
				this.appUserService.readWithNotifications(user.getId()).addNotification(notification);
			}
		} catch (NumberFormatException | PersistenceServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void processAssignment(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processModification(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processDeletion(String message) {
		// TODO Auto-generated method stub

	}

}
