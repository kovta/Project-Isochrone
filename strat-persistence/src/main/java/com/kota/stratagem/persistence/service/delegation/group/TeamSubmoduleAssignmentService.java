package com.kota.stratagem.persistence.service.delegation.group;

import java.util.Date;

import javax.ejb.EJB;

import com.kota.stratagem.persistence.entity.AbstractTeamAssignment;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.TeamSubmoduleAssignment;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.qualifier.SubmoduleFocused;
import com.kota.stratagem.persistence.query.TeamSubmoduleAssignmentQuery;
import com.kota.stratagem.persistence.service.SubmoduleService;

@Contained
@SubmoduleFocused
public class TeamSubmoduleAssignmentService extends AbstractTeamAssignmentService {

	@EJB
	private SubmoduleService submoduleService;

	@Override
	public AbstractTeamAssignment create(Long entrustor, Long recipient, Long target) {
		final Submodule targetSubmodule = this.submoduleService.readWithMonitoring(target);
		final TeamSubmoduleAssignment assignment = new TeamSubmoduleAssignment(targetSubmodule, new Date());
		this.persistAssignment(assignment, targetSubmodule, entrustor, recipient);
		return assignment;
	}

	@Override
	public AbstractTeamAssignment read(Long id) {
		return this.entityManager.createNamedQuery(TeamSubmoduleAssignmentQuery.GET_BY_ID, TeamSubmoduleAssignment.class)
				.setParameter(AssignmentParameter.ID, id).getSingleResult();
	}

	@Override
	public void delete(Long id) {
		this.entityManager.createNamedQuery(TeamSubmoduleAssignmentQuery.REMOVE_BY_ID).setParameter(AssignmentParameter.ID, id).executeUpdate();
	}

}
