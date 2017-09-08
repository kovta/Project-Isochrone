package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.AssignmentConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserAssignmentService;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.util.Constants;

@Stateless(mappedName = "ejb/appUserAssignmentProtocol")
public class AppUserAssignmentProtocolImpl implements AppUserAssignmentProtocol {

	private static final Logger LOGGER = Logger.getLogger(AppUserAssignmentProtocolImpl.class);

	@EJB
	private AppUserAssignmentService assignmentService;

	@EJB
	private AppUserService appUserService;

	@Inject
	private AssignmentConverter converter;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@EJB
	private LifecycleOverseer overseer;

	private void saveAssignments(String[] recipients, Long subject, String object) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create AppUser" + object + "Assignment (" + recipients.length + " recipients, " + object + ": " + subject + ")");
			}
			final String operator = this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
			for (final String recipient : recipients) {
				switch (object) {
					case Constants.OBJECTIVE_DATA_NAME:
						this.overseer.assigned(
								this.converter.to(this.assignmentService.createObjectiveAssignment(this.appUserService.readElementary(operator).getId(),
										this.appUserService.readElementary(recipient).getId(), subject)).toTextMessage());
						break;
					case Constants.PROJECT_DATA_NAME:
						this.overseer
								.assigned(this.converter.to(this.assignmentService.createProjectAssignment(this.appUserService.readElementary(operator).getId(),
										this.appUserService.readElementary(recipient).getId(), subject)).toTextMessage());
						break;
					case Constants.SUBMODULE_DATA_NAME:
						this.overseer.assigned(
								this.converter.to(this.assignmentService.createSubmoduleAssignment(this.appUserService.readElementary(operator).getId(),
										this.appUserService.readElementary(recipient).getId(), subject)).toTextMessage());
						break;
					case Constants.TASK_DATA_NAME:
						this.overseer
								.assigned(this.converter.to(this.assignmentService.createTaskAssignment(this.appUserService.readElementary(operator).getId(),
										this.appUserService.readElementary(recipient).getId(), subject)).toTextMessage());
						break;
					default:
						break;
				}
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	private void removeAssignment(Long id, String object) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remove AppUser" + object + "Assignment (id: " + id + ")");
			}
			switch (object) {
				case Constants.OBJECTIVE_DATA_NAME:
					final String msg_a = this.converter.to(this.assignmentService.readObjectiveAssignment(id)).toTextMessage();
					this.assignmentService.deleteObjectiveAssignment(id);
					this.overseer.dissociated(msg_a);
					break;
				case Constants.PROJECT_DATA_NAME:
					final String msg_b = this.converter.to(this.assignmentService.readProjectAssignment(id)).toTextMessage();
					this.assignmentService.deleteProjectAssignment(id);
					this.overseer.dissociated(msg_b);
					break;
				case Constants.SUBMODULE_DATA_NAME:
					final String msg_c = this.converter.to(this.assignmentService.readSubmoduleAssignment(id)).toTextMessage();
					this.assignmentService.deleteSubmoduleAssignment(id);
					this.overseer.dissociated(msg_c);
					break;
				case Constants.TASK_DATA_NAME:
					final String msg_d = this.converter.to(this.assignmentService.readTaskAssignment(id)).toTextMessage();
					this.assignmentService.deleteTaskAssignment(id);
					this.overseer.dissociated(msg_d);
					break;
				default:
					break;
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void saveObjectiveAssignments(String[] recipients, Long objective) throws AdaptorException {
		this.saveAssignments(recipients, objective, Constants.OBJECTIVE_DATA_NAME);
	}

	@Override
	public void saveProjectAssignments(String[] recipients, Long project) throws AdaptorException {
		this.saveAssignments(recipients, project, Constants.PROJECT_DATA_NAME);
	}

	@Override
	public void saveSubmoduleAssignments(String[] recipients, Long submodule) throws AdaptorException {
		this.saveAssignments(recipients, submodule, Constants.SUBMODULE_DATA_NAME);
	}

	@Override
	public void saveTaskAssignments(String[] recipients, Long task) throws AdaptorException {
		this.saveAssignments(recipients, task, Constants.TASK_DATA_NAME);
	}

	@Override
	public void removeObjectiveAssignment(Long id) throws AdaptorException {
		this.removeAssignment(id, Constants.OBJECTIVE_DATA_NAME);
	}

	@Override
	public void removeProjectAssignment(Long id) throws AdaptorException {
		this.removeAssignment(id, Constants.PROJECT_DATA_NAME);
	}

	@Override
	public void removeSubmoduleAssignment(Long id) throws AdaptorException {
		this.removeAssignment(id, Constants.SUBMODULE_DATA_NAME);
	}

	@Override
	public void removeTaskAssignment(Long id) throws AdaptorException {
		this.removeAssignment(id, Constants.TASK_DATA_NAME);
	}

}
