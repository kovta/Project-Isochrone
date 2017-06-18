package com.kota.stratagem.ejbservice.protocol;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ImpedimentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectCriteria;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;

@Local
public interface ProjectProtocol {

	ProjectRepresentor getProject(Long id) throws AdaptorException;

	List<ProjectRepresentor> getAllProjects(ProjectCriteria criteria) throws AdaptorException;

	ProjectRepresentor saveProject(Long id, String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean confidential,
			String operator, Set<TaskRepresentor> tasks, Set<TeamRepresentor> assignedTeams, Set<AppUserRepresentor> assignedUsers,
			Set<ImpedimentRepresentor> impediments, Long objective) throws AdaptorException;

	void removeProject(Long id) throws AdaptorException;

}