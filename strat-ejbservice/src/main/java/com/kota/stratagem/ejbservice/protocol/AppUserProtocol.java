package com.kota.stratagem.ejbservice.protocol;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ImpedimentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;

@Local
public interface AppUserProtocol {

	AppUserRepresentor getAppUser(Long id) throws AdaptorException;

	AppUserRepresentor getAppUser(String username) throws AdaptorException;

	List<List<AppUserRepresentor>> getAssignableAppUserClusters(ObjectiveRepresentor objective) throws AdaptorException;

	List<AppUserRepresentor> getAllAppUsers() throws AdaptorException;

	AppUserRepresentor saveAppUser(Long id, String name, String passwordHash, String email, RoleRepresentor role, AppUserRepresentor operator,
			Set<ObjectiveRepresentor> objectives, Set<ProjectRepresentor> projects, Set<SubmoduleRepresentor> submodules, Set<TaskRepresentor> tasks,
			Set<ImpedimentRepresentor> reportedImpediments, Set<ImpedimentRepresentor> processedImpediments, Set<TeamRepresentor> supervisedTeams,
			Set<TeamRepresentor> teamMemberships) throws AdaptorException;

	void removeAppUser(Long id) throws AdaptorException;

}
