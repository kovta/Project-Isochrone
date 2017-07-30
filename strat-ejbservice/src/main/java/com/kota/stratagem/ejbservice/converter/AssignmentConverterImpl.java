package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamObjectiveAssignmentRepresentor;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.TeamObjectiveAssignment;

@Stateless
public class AssignmentConverterImpl implements AssignmentConverter {

	@EJB
	private AppUserConverter appUserConverter;

	@EJB
	private TeamConverter teamConverter;

	@EJB
	private ObjectiveConverter objectiveConverter;

	@Override
	public AppUserObjectiveAssignmentRepresentor to(AppUserObjectiveAssignment assignment) {
		final AppUserObjectiveAssignmentRepresentor representor = assignment.getId() != null
				? new AppUserObjectiveAssignmentRepresentor(assignment.getId(), this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.appUserConverter.toElementary(assignment.getRecipient()), this.objectiveConverter.toElementary(assignment.getObjective()),
						assignment.getCreationDate())
				: new AppUserObjectiveAssignmentRepresentor(this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.appUserConverter.toElementary(assignment.getRecipient()), this.objectiveConverter.toElementary(assignment.getObjective()),
						assignment.getCreationDate());
		return representor;
	}

	@Override
	public Set<AppUserObjectiveAssignmentRepresentor> toUserObjectiveAssignmentSet(Set<AppUserObjectiveAssignment> assignments) {
		final Set<AppUserObjectiveAssignmentRepresentor> representors = new HashSet<>();
		for (final AppUserObjectiveAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

	@Override
	public TeamObjectiveAssignmentRepresentor to(TeamObjectiveAssignment assignment) {
		final TeamObjectiveAssignmentRepresentor representor = assignment.getId() != null
				? new TeamObjectiveAssignmentRepresentor(assignment.getId(), this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.teamConverter.toElementary(assignment.getRecipient()), this.objectiveConverter.toElementary(assignment.getObjective()),
						assignment.getCreationDate())
				: new TeamObjectiveAssignmentRepresentor(this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.teamConverter.toElementary(assignment.getRecipient()), this.objectiveConverter.toElementary(assignment.getObjective()),
						assignment.getCreationDate());
		return representor;
	}

	@Override
	public Set<TeamObjectiveAssignmentRepresentor> toTeamObjectiveAssignmentSet(Set<TeamObjectiveAssignment> assignments) {
		final Set<TeamObjectiveAssignmentRepresentor> representors = new HashSet<>();
		for (final TeamObjectiveAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

}
