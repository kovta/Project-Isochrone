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
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.ObjectiveConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.ejbserviceclient.protocol.ObjectiveProtocolRemote;
import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.util.Constants;

@Regulated
@Stateless(mappedName = "ejb/objectiveProtocol")
public class ObjectiveProtocolImpl implements ObjectiveProtocol, ObjectiveProtocolRemote {

	@EJB
	private AppUserService appUserService;

	@EJB
	private ObjectiveService objectiveService;

	@Inject
	private ObjectiveConverter converter;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@EJB
	private LifecycleOverseer overseer;

	@Override
	public ObjectiveRepresentor getObjective(Long id) throws ServiceException {
		final ObjectiveRepresentor representor = this.converter.toComplete(this.objectiveService.readComplete(id));
		Collections.sort(representor.getOverdueProjects(), new Comparator<ProjectRepresentor>() {
			@Override
			public int compare(ProjectRepresentor obj_a, ProjectRepresentor obj_b) {
				final int c = obj_a.getDeadline().compareTo(obj_b.getDeadline());
				if (c == 0) {
					return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
				}
				return c;
			}
		});
		Collections.sort(representor.getOngoingProjects(), new Comparator<ProjectRepresentor>() {
			@Override
			public int compare(ProjectRepresentor obj_a, ProjectRepresentor obj_b) {
				return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
			}
		});
		Collections.sort(representor.getCompletedProjects(), new Comparator<ProjectRepresentor>() {
			@Override
			public int compare(ProjectRepresentor obj_a, ProjectRepresentor obj_b) {
				return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
			}
		});
		Collections.sort(representor.getOverdueTasks(), new Comparator<TaskRepresentor>() {
			@Override
			public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
				final int c = obj_a.getDeadline().compareTo(obj_b.getDeadline());
				if (c == 0) {
					return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
				}
				return c;
			}
		});
		Collections.sort(representor.getOngoingTasks(), new Comparator<TaskRepresentor>() {
			@Override
			public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
				return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
			}
		});
		Collections.sort(representor.getCompletedTasks(), new Comparator<TaskRepresentor>() {
			@Override
			public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
				return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
			}
		});
		Collections.sort(representor.getAssignedUsers(), new Comparator<AppUserObjectiveAssignmentRepresentor>() {
			@Override
			public int compare(AppUserObjectiveAssignmentRepresentor obj_a, AppUserObjectiveAssignmentRepresentor obj_b) {
				return obj_a.getRecipient().getName().toLowerCase().compareTo(obj_b.getRecipient().getName().toLowerCase());
			}
		});
		return representor;
	}

	@Override
	public List<ObjectiveRepresentor> getAllObjectives() throws AdaptorException {
		Set<ObjectiveRepresentor> representors = new HashSet<ObjectiveRepresentor>();
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
	}

	@Override
	public ObjectiveRepresentor saveObjective(Long id, String name, String description, int priority, ObjectiveStatusRepresentor status, Date deadline,
			Boolean confidentiality, String operator) throws AdaptorException {
		final ObjectiveStatus objectiveStatus = ObjectiveStatus.valueOf(status.name());
		ObjectiveRepresentor origin = null;
		if (id != null) {
			origin = this.converter.toElementary(this.objectiveService.readElementary(id));
		}
		final ObjectiveRepresentor representor = this.converter.toComplete(((id != null) && this.objectiveService.exists(id))
				? this.objectiveService.update(id, name, description, priority, objectiveStatus, deadline, confidentiality, operator)
				: this.objectiveService.create(name, description, priority, objectiveStatus, deadline, confidentiality, operator));
		if (id != null) {
			this.overseer.modified(origin.toTextMessage() + Constants.PAYLOAD_SEPARATOR + representor.toTextMessage());
		} else {
			this.overseer.created(representor.toTextMessage());
		}
		return representor;
	}

	@Override
	public void removeObjective(Long id) throws AdaptorException {
		try {
			final String message = this.converter.toElementary(this.objectiveService.readElementary(id)).toTextMessage() + Constants.PAYLOAD_SEPARATOR
					+ this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
			this.objectiveService.delete(id);
			this.overseer.deleted(message);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

}
