package com.kota.stratagem.persistence.service.delegation.group;

import java.util.Date;

import javax.ejb.EJB;

import com.kota.stratagem.persistence.entity.AbstractTeamAssignment;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.TeamProjectAssignment;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.qualifier.ProjectFocused;
import com.kota.stratagem.persistence.query.TeamProjectAssignmentQuery;
import com.kota.stratagem.persistence.service.ProjectService;

@Contained
@ProjectFocused
public class TeamProjectAssignmentService extends AbstractTeamAssignmentService {

	@EJB
	private ProjectService projectService;

	@Override
	public AbstractTeamAssignment create(Long entrustor, Long recipient, Long target) {
		final Project targetProject = this.projectService.readWithMonitoring(target);
		final TeamProjectAssignment assignment = new TeamProjectAssignment(targetProject, new Date());
		this.persistAssignment(assignment, targetProject, entrustor, recipient);
		return assignment;
	}

	@Override
	public AbstractTeamAssignment read(Long id) {
		return this.entityManager.createNamedQuery(TeamProjectAssignmentQuery.GET_BY_ID, TeamProjectAssignment.class).setParameter(AssignmentParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public void delete(Long id) {
		this.entityManager.createNamedQuery(TeamProjectAssignmentQuery.REMOVE_BY_ID).setParameter(AssignmentParameter.ID, id).executeUpdate();
	}

}
