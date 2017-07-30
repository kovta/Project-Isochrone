package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;

@Local
public interface AssignmentConverter {

	AppUserObjectiveAssignmentRepresentor to(AppUserObjectiveAssignment assignment);

	Set<AppUserObjectiveAssignmentRepresentor> to(Set<AppUserObjectiveAssignment> assignments);

}
