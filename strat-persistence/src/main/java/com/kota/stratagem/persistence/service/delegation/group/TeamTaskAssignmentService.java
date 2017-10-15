package com.kota.stratagem.persistence.service.delegation.group;

import java.util.Date;

import javax.ejb.EJB;

import com.kota.stratagem.persistence.entity.AbstractTeamAssignment;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.entity.TeamTaskAssignment;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.qualifier.TaskFocused;
import com.kota.stratagem.persistence.query.TeamTaskAssignmentQuery;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.TaskService;
import com.kota.stratagem.persistence.service.TeamService;

@Contained
@TaskFocused
public class TeamTaskAssignmentService extends AbstractTeamAssignmentService {

	@EJB
	private TaskService taskService;

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserService appUserService;

	@Override
	public AbstractTeamAssignment create(Long entrustor, Long recipient, Long target) {
		final Task targetTask = this.taskService.readWithMonitoring(target);
		final TeamTaskAssignment assignment = new TeamTaskAssignment(targetTask, new Date());
		if(targetTask.getCreator().getId() == entrustor) {
			assignment.setEntrustor(targetTask.getCreator());
		} else if(targetTask.getModifier().getId() == entrustor) {
			assignment.setEntrustor(targetTask.getModifier());
		} else {
			assignment.setEntrustor(this.appUserService.readElementary(entrustor));
		}
		final Team assignedTeam = this.teamService.readElementary(recipient);
		assignment.setRecipient(assignedTeam);
		this.entityManager.merge(assignment);
		this.entityManager.flush();
		// this.persistAssignment(assignment, targetTask, entrustor, recipient);
		return assignment;
	}

	@Override
	public AbstractTeamAssignment read(Long id) {
		return this.entityManager.createNamedQuery(TeamTaskAssignmentQuery.GET_BY_ID, TeamTaskAssignment.class).setParameter(AssignmentParameter.ID, id).getSingleResult();
	}

	@Override
	public void delete(Long id) {
		this.entityManager.createNamedQuery(TeamTaskAssignmentQuery.REMOVE_BY_ID).setParameter(AssignmentParameter.ID, id).executeUpdate();
	}

}