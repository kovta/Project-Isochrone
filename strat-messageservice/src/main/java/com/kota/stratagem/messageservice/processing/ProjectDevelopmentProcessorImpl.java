package com.kota.stratagem.messageservice.processing;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;

import com.kota.stratagem.messageservice.qualifier.ProjectOriented;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.AppUserProjectAssignment;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.util.Constants;

@ProjectOriented
public class ProjectDevelopmentProcessorImpl extends AbstractDevelopmentProcessor implements DevelopmentProcessor {

	@EJB
	protected ObjectiveService objectiveService;

	@EJB
	protected ProjectService projectService;

	@Override
	public void processCreation(String message) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.CREATION_SELECTOR);
		final Set<AppUser> recipients = new HashSet<>();
		recipients.add(this.appUserService
				.readElementary(this.objectiveService.readElementary(Long.parseLong(attributes.get(Constants.CREATOR_ID_DATA_NAME))).getCreator().getId()));
		for (final AppUserObjectiveAssignment assignment : this.objectiveService
				.readWithAssignments(Long.parseLong(attributes.get(Constants.OBJECTIVE_ID_ATTRIBUTE_DATA_NAME))).getAssignedUsers()) {
			recipients.add(this.appUserService.readElementary(assignment.getRecipient().getId()));
		}
		recipients.remove(this.appUserService.readElementary(Long.parseLong(attributes.get(Constants.CREATOR_ID_DATA_NAME))));
		this.handleCreationProperties(attributes, Constants.PROJECT_DATA_NAME, recipients);
	}

	@Override
	public void processAssignment(String message) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.ASSIGNMENT_SELECTOR);
		this.handleAssignmentProperties(attributes, Constants.PROJECT_DATA_NAME, attributes.get(Constants.PROJECT_NAME_ATTRIBUTE_DATA_NAME));
	}

	@Override
	public void processDissociation(String message) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.DISSOCIATION_SELECTOR);
		this.handleDissociationProperties(attributes, Constants.PROJECT_DATA_NAME, attributes.get(Constants.PROJECT_NAME_ATTRIBUTE_DATA_NAME));
	}

	@Override
	public void processModification(String originMessage, String resultMessage) throws PersistenceServiceException {
		final Map<String, String> origin_attributes = this.processMessageContent(originMessage, Constants.UPDATE_SELECTOR);
		final Map<String, String> result_attributes = this.processMessageContent(resultMessage, Constants.UPDATE_SELECTOR);
		final Set<AppUser> recipients = new HashSet<>();
		for (final AppUserProjectAssignment assignment : this.projectService.readWithAssignments(Long.parseLong(result_attributes.get(Constants.ID_DATA_NAME)))
				.getAssignedUsers()) {
			recipients.add(this.appUserService.readElementary(assignment.getRecipient().getId()));
		}
		if (!origin_attributes.get(Constants.CREATOR_ID_DATA_NAME).equals(result_attributes.get(Constants.MODIFIER_ID_DATA_NAME))) {
			recipients.add(this.appUserService.readElementary(Long.parseLong(result_attributes.get(Constants.MODIFIER_ID_DATA_NAME))));
		}
		this.handleModificationProperties(origin_attributes, result_attributes, Constants.PROJECT_DATA_NAME, recipients);
	}

	@Override
	public void processDeletion(String message, String operator) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.DELETION_SELECTOR);
		final Set<AppUser> recipients = new HashSet<>();
		recipients.add(this.appUserService
				.readElementary(this.objectiveService.readElementary(Long.parseLong(attributes.get(Constants.CREATOR_ID_DATA_NAME))).getCreator().getId()));
		recipients.remove(this.appUserService.readElementary(operator));
		this.handleDeletionProperties(attributes, Constants.PROJECT_DATA_NAME, operator, recipients);
	}

}
