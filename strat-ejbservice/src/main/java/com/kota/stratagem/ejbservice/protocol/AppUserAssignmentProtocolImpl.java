package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
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

	@EJB
	private SessionContextAccessor sessionContextAccessor;

	private void saveAssignments(String[] recipients, Long subject, String object) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create AppUser" + object + "Assignment (" + recipients.length + " recipients, Objective: " + subject + ")");
			}
			final String operator = this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
			for (final String recipient : recipients) {
				switch (object) {
					case Constants.OBJECTIVE_DATA_NAME:
						this.assignmentService.createObjectiveAssignment(this.appUserService.readElementary(operator).getId(),
								this.appUserService.readElementary(recipient).getId(), subject);
						break;
					case Constants.PROJECT_DATA_NAME:
						this.assignmentService.createProjectAssignment(this.appUserService.readElementary(operator).getId(),
								this.appUserService.readElementary(recipient).getId(), subject);
						break;
					case Constants.SUBMODULE_DATA_NAME:
						this.assignmentService.createSubmoduleAssignment(this.appUserService.readElementary(operator).getId(),
								this.appUserService.readElementary(recipient).getId(), subject);
						break;
					case Constants.TASK_DATA_NAME:
						this.assignmentService.createTaskAssignment(this.appUserService.readElementary(operator).getId(),
								this.appUserService.readElementary(recipient).getId(), subject);
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
					this.assignmentService.deleteObjectiveAssignment(id);
					break;
				case Constants.PROJECT_DATA_NAME:
					this.assignmentService.deleteProjectAssignment(id);
					break;
				case Constants.SUBMODULE_DATA_NAME:
					this.assignmentService.deleteSubmoduleAssignment(id);
					break;
				case Constants.TASK_DATA_NAME:
					this.assignmentService.deleteTaskAssignment(id);
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
