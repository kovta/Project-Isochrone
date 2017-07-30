package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;

@Stateless
public class AssignmentConverterImpl implements AssignmentConverter {

	@EJB
	private AppUserConverter appUserConverter;

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
	public Set<AppUserObjectiveAssignmentRepresentor> to(Set<AppUserObjectiveAssignment> assignments) {
		final Set<AppUserObjectiveAssignmentRepresentor> representors = new HashSet<>();
		for (final AppUserObjectiveAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

}
