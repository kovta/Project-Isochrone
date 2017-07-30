package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.converter.ObjectiveConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.ObjectiveProtocolRemote;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.service.TeamService;

@Stateless(mappedName = "ejb/objectiveProtocol")
public class ObjectiveProtocolImpl implements ObjectiveProtocol, ObjectiveProtocolRemote {

	private static final Logger LOGGER = Logger.getLogger(ObjectiveProtocolImpl.class);

	@EJB
	private ProjectService projectService;

	@EJB
	private TaskService taskService;

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ObjectiveConverter converter;

	@Override
	public ObjectiveRepresentor getObjective(Long id) throws ServiceException {
		try {
			final ObjectiveRepresentor representor = this.converter.toComplete(this.objectiveService.readComplete(id));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Get Objective (id: " + id + ") --> " + representor);
			}
			Collections.sort(representor.getProjects(), new Comparator<ProjectRepresentor>() {
				@Override
				public int compare(ProjectRepresentor obj_a, ProjectRepresentor obj_b) {
					return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
				}
			});
			Collections.sort(representor.getTasks(), new Comparator<TaskRepresentor>() {
				@Override
				public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
					final int c = (int) obj_a.getCompletion() - (int) obj_b.getCompletion();
					if (c == 0) {
						return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
					}
					return c * -1;
				}
			});
			return representor;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new ServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<ObjectiveRepresentor> getAllObjectives() throws AdaptorException {
		Set<ObjectiveRepresentor> representors = new HashSet<ObjectiveRepresentor>();
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fetch all Objectives : " + representors.size() + " objective(s)");
			}
			representors = this.converter.toSimplified(this.objectiveService.readAll());
			final List<ObjectiveRepresentor> objectives = new ArrayList<ObjectiveRepresentor>(representors);
			Collections.sort(objectives, new Comparator<ObjectiveRepresentor>() {
				@Override
				public int compare(ObjectiveRepresentor obj_a, ObjectiveRepresentor obj_b) {
					final int c1 = obj_a.getPriority() - obj_b.getPriority();
					if (c1 == 0) {
						final int c2 = obj_a.getProjects().size() - obj_b.getProjects().size();
						if (c2 == 0) {
							final int c3 = obj_a.getTasks().size() - obj_b.getTasks().size();
							if (c3 == 0) {
								return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
							}
							return c3 * -1;
						}
						return c2 * -1;
					}
					return c1;
				}
			});
			return objectives;
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public ObjectiveRepresentor saveObjective(Long id, String name, String description, int priority, ObjectiveStatusRepresentor status, Date deadline,
			Boolean confidentiality, String operator) throws AdaptorException {
		try {
			Objective objective = null;
			final ObjectiveStatus objectiveStatus = ObjectiveStatus.valueOf(status.name());
			if ((id != null) && this.objectiveService.exists(id)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Update Objective (id: " + id + ")");
				}
				objective = this.objectiveService.update(id, name, description, priority, objectiveStatus, deadline, confidentiality,
						this.appUserService.read(operator));
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Create Objective (" + name + ")");
				}
				objective = this.objectiveService.create(name, description, priority, objectiveStatus, deadline, confidentiality,
						this.appUserService.read(operator));
			}
			return this.converter.toComplete(objective);
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

	@Override
	public void removeObjective(Long id) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remove Objective (id: " + id + ")");
			}
			this.objectiveService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}
	}

}
