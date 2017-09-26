package com.kota.stratagem.ejbservice.converter.delegation;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserProjectAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserSubmoduleAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserTaskAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamProjectAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamSubmoduleAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamTaskAssignmentRepresentor;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.AppUserProjectAssignment;
import com.kota.stratagem.persistence.entity.AppUserSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.AppUserTaskAssignment;
import com.kota.stratagem.persistence.entity.TeamObjectiveAssignment;
import com.kota.stratagem.persistence.entity.TeamProjectAssignment;
import com.kota.stratagem.persistence.entity.TeamSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.TeamTaskAssignment;

@Local
public interface AssignmentConverter {

	AppUserObjectiveAssignmentRepresentor to(AppUserObjectiveAssignment assignment);

	AppUserProjectAssignmentRepresentor to(AppUserProjectAssignment assignment);

	AppUserSubmoduleAssignmentRepresentor to(AppUserSubmoduleAssignment assignment);

	AppUserTaskAssignmentRepresentor to(AppUserTaskAssignment assignment);

	TeamObjectiveAssignmentRepresentor to(TeamObjectiveAssignment assignment);

	TeamProjectAssignmentRepresentor to(TeamProjectAssignment assignment);

	TeamSubmoduleAssignmentRepresentor to(TeamSubmoduleAssignment assignment);

	TeamTaskAssignmentRepresentor to(TeamTaskAssignment assignment);

	Set<AppUserObjectiveAssignmentRepresentor> toAppUserObjectiveAssignmentSet(Set<AppUserObjectiveAssignment> assignments);

	Set<AppUserProjectAssignmentRepresentor> toAppUserProjectAssignmentSet(Set<AppUserProjectAssignment> assignments);

	Set<AppUserSubmoduleAssignmentRepresentor> toAppUserSubmoduleAssignmentSet(Set<AppUserSubmoduleAssignment> assignments);

	Set<AppUserTaskAssignmentRepresentor> toAppUserTaskAssignmentSet(Set<AppUserTaskAssignment> assignments);

	Set<AppUserObjectiveAssignmentRepresentor> toAppUserObjectiveAssignmentSet(TeamObjectiveAssignmentRepresentor assignment);

	Set<AppUserProjectAssignmentRepresentor> toAppUserProjectAssignmentSet(TeamProjectAssignmentRepresentor assignment);

	Set<AppUserSubmoduleAssignmentRepresentor> toAppUserSubmoduleAssignmentSet(TeamSubmoduleAssignmentRepresentor assignment);

	Set<AppUserTaskAssignmentRepresentor> toAppUserTaskAssignmentSet(TeamTaskAssignmentRepresentor assignment);

	Set<TeamObjectiveAssignmentRepresentor> toTeamObjectiveAssignmentSet(Set<TeamObjectiveAssignment> assignments);

	Set<TeamProjectAssignmentRepresentor> toTeamProjectAssignmentSet(Set<TeamProjectAssignment> assignments);

	Set<TeamSubmoduleAssignmentRepresentor> toTeamSubmoduleAssignmentSet(Set<TeamSubmoduleAssignment> assignments);

	Set<TeamTaskAssignmentRepresentor> toTeamTaskAssignmentSet(Set<TeamTaskAssignment> assignments);

}
