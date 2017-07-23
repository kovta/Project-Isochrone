package com.kota.stratagem.ejbservice.protocol;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;

@Local
public interface SubmoduleProtocol {

	SubmoduleRepresentor getSubmodule(Long id) throws AdaptorException;

	List<SubmoduleRepresentor> getAllSubmodules() throws AdaptorException;

	SubmoduleRepresentor saveSubmodule(Long id, String name, String description, Date deadline, String operator, Set<TaskRepresentor> tasks,
			Set<TeamRepresentor> assignedTeams, Set<AppUserRepresentor> assignedUsers, Long project) throws AdaptorException;

	void removeSubmodule(Long id) throws AdaptorException;

}
