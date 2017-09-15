package com.kota.stratagem.messageservice.processing;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;

import com.kota.stratagem.messageservice.qualifier.TaskOriented;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.AppUserProjectAssignment;
import com.kota.stratagem.persistence.entity.AppUserSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.AppUserTaskAssignment;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.SubmoduleService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.util.Constants;

@TaskOriented
public class TaskDevelopmentProcessorImpl extends AbstractDevelopmentProcessor implements DependencyExtendedDevelopmentProcessor {

	@EJB
	protected ObjectiveService objectiveService;

	@EJB
	protected ProjectService projectService;

	@EJB
	protected SubmoduleService submoduleService;

	@EJB
	protected TaskService taskService;

	@Override
	public void processCreation(String message) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.CREATION_SELECTOR);
		final Set<AppUser> recipients = new HashSet<>();
		if (!attributes.get(Constants.OBJECTIVE_ID_ATTRIBUTE_DATA_LABEL).equals(Constants.NULL_ATTRIBUTE)) {
			recipients.add(this.appUserService.readElementary(
					this.objectiveService.readElementary(Long.parseLong(attributes.get(Constants.CREATOR_ID_DATA_LABEL))).getCreator().getId()));
			for (final AppUserObjectiveAssignment assignment : this.objectiveService
					.readWithAssignments(Long.parseLong(attributes.get(Constants.OBJECTIVE_ID_ATTRIBUTE_DATA_LABEL))).getAssignedUsers()) {
				recipients.add(this.appUserService.readElementary(assignment.getRecipient().getId()));
			}
		} else if (!attributes.get(Constants.PROJECT_ID_ATTRIBUTE_DATA_LABEL).equals(Constants.NULL_ATTRIBUTE)) {
			recipients.add(this.appUserService
					.readElementary(this.projectService.readElementary(Long.parseLong(attributes.get(Constants.CREATOR_ID_DATA_LABEL))).getCreator().getId()));
			for (final AppUserProjectAssignment assignment : this.projectService
					.readWithAssignments(Long.parseLong(attributes.get(Constants.PROJECT_ID_ATTRIBUTE_DATA_LABEL))).getAssignedUsers()) {
				recipients.add(this.appUserService.readElementary(assignment.getRecipient().getId()));
			}
		} else if (!attributes.get(Constants.SUBMODULE_ID_ATTRIBUTE_DATA_LABEL).equals(Constants.NULL_ATTRIBUTE)) {
			recipients.add(this.appUserService.readElementary(
					this.submoduleService.readElementary(Long.parseLong(attributes.get(Constants.CREATOR_ID_DATA_LABEL))).getCreator().getId()));
			for (final AppUserSubmoduleAssignment assignment : this.submoduleService
					.readWithAssignments(Long.parseLong(attributes.get(Constants.SUBMODULE_ID_ATTRIBUTE_DATA_LABEL))).getAssignedUsers()) {
				recipients.add(this.appUserService.readElementary(assignment.getRecipient().getId()));
			}
		}
		recipients.remove(this.appUserService.readElementary(Long.parseLong(attributes.get(Constants.CREATOR_ID_DATA_LABEL))));
		this.handleCreationProperties(attributes, Constants.TASK_DATA_LABEL, recipients);
	}

	@Override
	public void processAssignment(String message) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.ASSIGNMENT_SELECTOR);
		this.handleAssignmentProperties(attributes, Constants.TASK_DATA_LABEL, attributes.get(Constants.TASK_NAME_ATTRIBUTE_DATA_LABEL));
	}

	@Override
	public void processDissociation(String message) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.DISSOCIATION_SELECTOR);
		this.handleDissociationProperties(attributes, Constants.TASK_DATA_LABEL, attributes.get(Constants.TASK_NAME_ATTRIBUTE_DATA_LABEL));
	}

	@Override
	public void processModification(String originMessage, String resultMessage) throws PersistenceServiceException {
		final Map<String, String> origin_attributes = this.processMessageContent(originMessage, Constants.UPDATE_SELECTOR);
		final Map<String, String> result_attributes = this.processMessageContent(resultMessage, Constants.UPDATE_SELECTOR);
		final Set<AppUser> recipients = new HashSet<>();
		for (final AppUserTaskAssignment assignment : this.taskService.readWithAssignments(Long.parseLong(result_attributes.get(Constants.ID_DATA_LABEL)))
				.getAssignedUsers()) {
			recipients.add(this.appUserService.readElementary(assignment.getRecipient().getId()));
		}
		if (!origin_attributes.get(Constants.CREATOR_ID_DATA_LABEL).equals(result_attributes.get(Constants.MODIFIER_ID_DATA_LABEL))) {
			recipients.add(this.appUserService.readElementary(Long.parseLong(origin_attributes.get(Constants.CREATOR_ID_DATA_LABEL))));
		}
		this.handleModificationProperties(origin_attributes, result_attributes, Constants.TASK_DATA_LABEL, recipients);
	}

	@Override
	public void processDeletion(String message, String operator) throws PersistenceServiceException {
		final Map<String, String> attributes = this.processMessageContent(message, Constants.DELETION_SELECTOR);
		final Set<AppUser> recipients = new HashSet<>();
		recipients.add(this.appUserService.readElementary(Long.parseLong(attributes.get(Constants.CREATOR_ID_DATA_LABEL))));
		recipients.remove(this.appUserService.readElementary(operator));
		this.handleDeletionProperties(attributes, Constants.TASK_DATA_LABEL, operator, recipients);
	}

	@Override
	public void processConfiguration(String dependant, String dependency) throws PersistenceServiceException {
		final Map<String, String> origin_attributes = this.processMessageContent(dependant, Constants.UPDATE_SELECTOR);
		final Map<String, String> result_attributes = this.processMessageContent(dependency, Constants.UPDATE_SELECTOR);
		final Set<AppUser> recipients = new HashSet<>();
		for (final AppUserTaskAssignment assignment : this.taskService.readWithAssignments(Long.parseLong(result_attributes.get(Constants.ID_DATA_LABEL)))
				.getAssignedUsers()) {
			recipients.add(this.appUserService.readElementary(assignment.getRecipient().getId()));
		}
		if (!origin_attributes.get(Constants.CREATOR_ID_DATA_LABEL).equals(result_attributes.get(Constants.MODIFIER_ID_DATA_LABEL))) {
			recipients.add(this.appUserService.readElementary(Long.parseLong(origin_attributes.get(Constants.CREATOR_ID_DATA_LABEL))));
		}
		this.handleDependencyConfigurationProperties(origin_attributes, result_attributes, Constants.TASK_DATA_LABEL, recipients);
	}

	@Override
	public void processDeconfiguration(String dependant, String dependency) throws PersistenceServiceException {
		final Map<String, String> origin_attributes = this.processMessageContent(dependant, Constants.UPDATE_SELECTOR);
		final Map<String, String> result_attributes = this.processMessageContent(dependency, Constants.UPDATE_SELECTOR);
		final Set<AppUser> recipients = new HashSet<>();
		for (final AppUserTaskAssignment assignment : this.taskService.readWithAssignments(Long.parseLong(result_attributes.get(Constants.ID_DATA_LABEL)))
				.getAssignedUsers()) {
			recipients.add(this.appUserService.readElementary(assignment.getRecipient().getId()));
		}
		if (!origin_attributes.get(Constants.CREATOR_ID_DATA_LABEL).equals(result_attributes.get(Constants.MODIFIER_ID_DATA_LABEL))) {
			recipients.add(this.appUserService.readElementary(Long.parseLong(origin_attributes.get(Constants.CREATOR_ID_DATA_LABEL))));
		}
		this.handleDependencyDeconfigurationProperties(origin_attributes, result_attributes, Constants.TASK_DATA_LABEL, recipients);
	}

}
