package com.kota.stratagem.messageservice.processing;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;

import com.kota.stratagem.messageservice.qualifier.ObjectiveOriented;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.util.Constants;

@ObjectiveOriented
public class ObjectiveDevelopmentProcessorImpl extends AbstractDevelopmentProcessor implements DevelopmentProcessor {

	@EJB
	protected ObjectiveService objectiveService;

	@Override
	public void processCreation(String message) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.CREATION_SELECTOR);
		final Set<AppUser> recipients = this.appUserService.readAll();
		recipients.remove(this.appUserService.readElementary(Long.parseLong(attributes.get(Constants.CREATOR_ID_DATA_LABEL))));
		this.handleCreationProperties(attributes, Constants.OBJECTIVE_DATA_LABEL, recipients);
	}

	@Override
	public void processAssignment(String message) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.ASSIGNMENT_SELECTOR);
		this.handleAssignmentProperties(attributes, Constants.OBJECTIVE_DATA_LABEL, attributes.get(Constants.OBJECTIVE_NAME_ATTRIBUTE_DATA_LABEL));
	}

	@Override
	public void processDissociation(String message) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.DISSOCIATION_SELECTOR);
		this.handleDissociationProperties(attributes, Constants.OBJECTIVE_DATA_LABEL, attributes.get(Constants.OBJECTIVE_NAME_ATTRIBUTE_DATA_LABEL));
	}

	@Override
	public void processModification(String originMessage, String resultMessage) throws PersistenceServiceException {
		final Map<String, String> origin_attributes = this.processMessageContent(originMessage, Constants.UPDATE_SELECTOR);
		final Map<String, String> result_attributes = this.processMessageContent(resultMessage, Constants.UPDATE_SELECTOR);
		final Set<AppUser> recipients = new HashSet<>();
		for (final AppUserObjectiveAssignment assignment : this.objectiveService
				.readWithAssignments(Long.parseLong(result_attributes.get(Constants.ID_DATA_LABEL))).getAssignedUsers()) {
			recipients.add(this.appUserService.readElementary(assignment.getRecipient().getId()));
		}
		if (!origin_attributes.get(Constants.CREATOR_ID_DATA_LABEL).equals(result_attributes.get(Constants.MODIFIER_ID_DATA_LABEL))) {
			recipients.add(this.appUserService.readElementary(Long.parseLong(origin_attributes.get(Constants.CREATOR_ID_DATA_LABEL))));
		}
		this.handleModificationProperties(origin_attributes, result_attributes, Constants.OBJECTIVE_DATA_LABEL, recipients);
	}

	@Override
	public void processDeletion(String message, String operator) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.DELETION_SELECTOR);
		final Set<AppUser> recipients = this.appUserService.readAll();
		recipients.remove(this.appUserService.readElementary(operator));
		this.handleDeletionProperties(attributes, Constants.OBJECTIVE_DATA_LABEL, operator, recipients);
	}

}
