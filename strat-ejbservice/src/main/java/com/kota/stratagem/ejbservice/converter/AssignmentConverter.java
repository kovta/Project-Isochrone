package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamObjectiveAssignmentRepresentor;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.TeamObjectiveAssignment;

@Local
public interface AssignmentConverter {

	AppUserObjectiveAssignmentRepresentor to(AppUserObjectiveAssignment assignment);

	TeamObjectiveAssignmentRepresentor to(TeamObjectiveAssignment assignment);

	Set<AppUserObjectiveAssignmentRepresentor> toUserObjectiveAssignmentSet(Set<AppUserObjectiveAssignment> assignments);

	Set<TeamObjectiveAssignmentRepresentor> toTeamObjectiveAssignmentSet(Set<TeamObjectiveAssignment> assignments);

}
