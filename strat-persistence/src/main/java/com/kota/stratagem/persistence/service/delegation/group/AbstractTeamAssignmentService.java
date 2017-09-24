package com.kota.stratagem.persistence.service.delegation.group;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.kota.stratagem.persistence.entity.AbstractMonitoredEntity;
import com.kota.stratagem.persistence.entity.AbstractTeamAssignment;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.TeamService;

public abstract class AbstractTeamAssignmentService implements TeamAssingmentService {

	@Inject
	protected EntityManager entityManager;

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserService appUserService;

	private <T extends AbstractMonitoredEntity> AppUser mergeOperators(Long subject, T object) {
		if (object.getCreator().getId() == subject) {
			return object.getCreator();
		} else if (object.getModifier().getId() == subject) {
			return object.getModifier();
		} else {
			return this.appUserService.readElementary(subject);
		}
	}

	protected <T extends AbstractMonitoredEntity, E extends AbstractTeamAssignment> void persistAssignment(E subject, T object, Long entrustor,
			Long recipient) {
		subject.setEntrustor(this.mergeOperators(entrustor, object));
		final Team assignedTeam = this.teamService.readElementary(recipient);
		subject.setRecipient(assignedTeam);
		this.entityManager.merge(subject);
		this.entityManager.flush();
	}

	protected void removeAssignment(Long id, String query, String object) {
		this.entityManager.createNamedQuery(query).setParameter(AssignmentParameter.ID, id).executeUpdate();
	}

}
