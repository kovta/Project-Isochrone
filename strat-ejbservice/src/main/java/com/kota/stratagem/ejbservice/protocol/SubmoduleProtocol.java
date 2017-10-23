package com.kota.stratagem.ejbservice.protocol;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

@Local
public interface SubmoduleProtocol {

	SubmoduleRepresentor getSubmodule(Long id) throws AdaptorException;

	List<SubmoduleRepresentor> getCompliantDependencyConfigurations(SubmoduleRepresentor submodule) throws AdaptorException;

	List<SubmoduleRepresentor> getPossibleDestinations(TaskRepresentor task) throws AdaptorException;

	List<SubmoduleRepresentor> getAllSubmodules() throws AdaptorException;

	SubmoduleRepresentor saveSubmodule(Long id, String name, String description, Date deadline, String operator, Long project) throws AdaptorException;

	void removeSubmodule(Long id) throws AdaptorException;

	void saveSubmoduleDependencies(Long source, Long[] dependencies) throws AdaptorException;

	void saveSubmoduleDependants(Long source, Long[] dependants) throws AdaptorException;

	void removeSubmoduleDependency(Long dependency, Long dependant) throws AdaptorException;

}
