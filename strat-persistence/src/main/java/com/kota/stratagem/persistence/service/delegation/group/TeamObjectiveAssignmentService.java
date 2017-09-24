package com.kota.stratagem.persistence.service.delegation.group;

import java.util.Date;

import javax.ejb.EJB;

import com.kota.stratagem.persistence.entity.AbstractTeamAssignment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.TeamObjectiveAssignment;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.qualifier.ObjectiveFocused;
import com.kota.stratagem.persistence.query.TeamObjectiveAssignmentQuery;
import com.kota.stratagem.persistence.service.ObjectiveService;

@Contained
@ObjectiveFocused
public class TeamObjectiveAssignmentService extends AbstractTeamAssignmentService {

	@EJB
	private ObjectiveService objectiveService;

	@Override
	public AbstractTeamAssignment create(Long entrustor, Long recipient, Long target) {
		final Objective targetObjective = this.objectiveService.readWithMonitoring(target);
		final TeamObjectiveAssignment assignment = new TeamObjectiveAssignment(targetObjective, new Date());
		this.persistAssignment(assignment, targetObjective, entrustor, recipient);
		return assignment;
	}

	@Override
	public AbstractTeamAssignment read(Long id) {
		return this.entityManager.createNamedQuery(TeamObjectiveAssignmentQuery.GET_BY_ID, TeamObjectiveAssignment.class)
				.setParameter(AssignmentParameter.ID, id).getSingleResult();
	}

	@Override
	public void delete(Long id) {
		this.entityManager.createNamedQuery(TeamObjectiveAssignmentQuery.REMOVE_BY_ID).setParameter(AssignmentParameter.ID, id).executeUpdate();
	}

}
