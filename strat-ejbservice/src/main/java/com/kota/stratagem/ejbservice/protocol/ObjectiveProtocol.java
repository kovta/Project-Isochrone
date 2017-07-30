package com.kota.stratagem.ejbservice.protocol;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;

@Local
public interface ObjectiveProtocol {

	ObjectiveRepresentor getObjective(Long id) throws ServiceException;

	List<ObjectiveRepresentor> getAllObjectives() throws AdaptorException;

	ObjectiveRepresentor saveObjective(Long id, String name, String description, int priority, ObjectiveStatusRepresentor status, Date deadline,
			Boolean confidentiality, String operator, Set<ProjectRepresentor> projects, Set<TaskRepresentor> tasks) throws AdaptorException;

	void removeObjective(Long id) throws AdaptorException;

}