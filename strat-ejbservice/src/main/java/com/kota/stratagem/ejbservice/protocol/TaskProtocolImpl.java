package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ImpedimentService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.service.TeamService;

@Stateless(mappedName = "ejb/taskProtocol")
public class TaskProtocolImpl implements TaskProtocol {

	private static final Logger LOGGER = Logger.getLogger(TaskProtocolImpl.class);

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectService projectSerivce;

	@EJB
	private TaskService taskService;

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ImpedimentService impedimentService;

	@EJB
	private TaskConverter converter;

	@Override
	public TaskRepresentor getTask(Long id) throws AdaptorException {
		try {
			final TaskRepresentor representor = this.converter.toComplete(this.taskService.readComplete(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Task (id: " + id + ") --> " + representor);
			}
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public List<TaskRepresentor> getAllTasks() throws AdaptorException {
		Set<TaskRepresentor> representors = new HashSet<>();
		try {
			representors = this.converter.toSimplified(this.taskService.readAll());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all Tasks --> " + representors.size() + " item(s)");
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
		return new ArrayList<TaskRepresentor>(representors);
	}

	@Override
	public TaskRepresentor saveTask(Long id, String name, String description, int priority, double completion, Date deadline, String operator, Long objective,
			Long project, Long submodule) throws AdaptorException {
		try {
			Task task = null;
			if ((id != null) && this.taskService.exists(id)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Update Task (id: " + id + ")");
				}
				task = this.taskService.update(id, name, description, priority, completion, deadline, this.appUserService.read(operator), objective, project,
						submodule);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Create Task (name: " + name + ")");
				}
				task = this.taskService.create(name, description, priority, completion, deadline, this.appUserService.read(operator), objective, project,
						submodule);
			}
			return this.converter.toComplete(task);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeTask(Long id) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remove Task (id: " + id + ")");
			}
			this.taskService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

}