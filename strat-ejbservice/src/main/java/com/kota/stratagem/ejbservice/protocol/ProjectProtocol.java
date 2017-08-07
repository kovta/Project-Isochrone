package com.kota.stratagem.ejbservice.protocol;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectCriteria;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ProjectStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;

@Local
public interface ProjectProtocol {

	ProjectRepresentor getProject(Long id) throws AdaptorException;

	List<ProjectRepresentor> getAllProjects(ProjectCriteria criteria) throws AdaptorException;

	List<ObjectiveRepresentor> getObjectiveProjectClusters() throws ServiceException;

	ProjectRepresentor saveProject(Long id, String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean confidential,
			String operator, Long objective) throws AdaptorException;

	void removeProject(Long id) throws AdaptorException;

}