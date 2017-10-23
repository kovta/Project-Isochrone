package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.context.EJBServiceConfiguration;
import com.kota.stratagem.ejbservice.converter.AppUserConverter;
import com.kota.stratagem.ejbservice.converter.ObjectiveConverter;
import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.converter.SubmoduleConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbservice.qualifier.SubmoduleOriented;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.SubmoduleService;
import com.kota.stratagem.security.context.SessionContextAccessor;
import com.kota.stratagem.security.domain.RestrictionLevel;
import com.kota.stratagem.security.interceptor.Authorized;

@Regulated
@Stateless(mappedName = EJBServiceConfiguration.SUBMODULE_PROTOCOL_SIGNATURE)
public class SubmoduleProtocolImpl implements SubmoduleProtocol {

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectService projectService;

	@EJB
	private SubmoduleService submoduleService;

	@EJB
	private AppUserService appUserService;

	@Inject
	private ObjectiveConverter objectiveConverter;

	@Inject
	private ProjectConverter projectConverter;

	@Inject
	private SubmoduleConverter submoduleConverter;

	@Inject
	private AppUserConverter appUserConverter;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@Inject
	@SubmoduleOriented
	private DTOExtensionManager extensionManager;

	@Override
	public SubmoduleRepresentor getSubmodule(Long id) throws AdaptorException {
		return (SubmoduleRepresentor) this.extensionManager.prepare(this.submoduleConverter.toComplete(this.submoduleService.readComplete(id)));
	}

	@Override
	public List<SubmoduleRepresentor> getCompliantDependencyConfigurations(SubmoduleRepresentor submodule) throws AdaptorException {
		final List<SubmoduleRepresentor> configurations = new ArrayList<>();
		configurations.addAll(this.submoduleConverter.toElementary(this.projectService.readWithSubmodules(submodule.getProject().getId()).getSubmodules()));
		configurations.remove(submodule);
		for(final List<SubmoduleRepresentor> dependencyLevel : submodule.getDependencyChain()) {
			for(final SubmoduleRepresentor dependency : dependencyLevel) {
				configurations.remove(dependency);
			}
		}
		for(final List<SubmoduleRepresentor> dependantLevel : submodule.getDependantChain()) {
			for(final SubmoduleRepresentor dependant : dependantLevel) {
				configurations.remove(dependant);
			}
		}
		return (List<SubmoduleRepresentor>) this.extensionManager.prepareBatch(configurations);
	}

	@Override
	public List<SubmoduleRepresentor> getPossibleDestinations(TaskRepresentor task) throws AdaptorException {
		final List<SubmoduleRepresentor> destinations = new ArrayList<>();
		if(task.getSubmodule() != null) {
			AppUserRepresentor operator = this.appUserConverter.toElementary(this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()));
			for(Submodule submodule : this.projectService.readWithSubmodulesAndTasks(task.getSubmodule().getProject().getId()).getSubmodules()) {
				SubmoduleRepresentor representor = this.submoduleConverter.toDispatchable(this.submoduleService.readWithMonitoring(submodule.getId()));
				ProjectRepresentor parentProject = this.projectConverter.toDispatchable(this.projectService.readWithMonitoring(submodule.getProject().getId()));
				ObjectiveRepresentor parentObjective = this.objectiveConverter.toDispatchable(this.objectiveService.readWithMonitoring(parentProject.getObjective().getId()));
				if(representor.getAssignedUsers().contains(operator) || representor.getCreator().equals(operator) || parentProject.getCreator().equals(operator)
						|| parentObjective.getCreator().equals(operator)) {
					destinations.add(representor);
				}
			}
			if(!destinations.contains(task.getSubmodule())) {
				destinations.remove(task.getSubmodule());
			}
		}
		return destinations;
	}

	@Override
	public List<SubmoduleRepresentor> getAllSubmodules() throws AdaptorException {
		return new ArrayList<SubmoduleRepresentor>(this.submoduleConverter.toSimplified(this.submoduleService.readAll()));
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public SubmoduleRepresentor saveSubmodule(Long id, String name, String description, Date deadline, String operator, Long project) throws AdaptorException {
		return (SubmoduleRepresentor) this.extensionManager.prepare(this.submoduleConverter.toComplete(((id != null) && this.submoduleService.exists(id))
				? this.submoduleService.update(id, name, description, deadline, operator) : this.submoduleService.create(name, description, deadline, operator, project)));
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void removeSubmodule(Long id) throws AdaptorException {
		try {
			this.submoduleService.delete(id);
		} catch(final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void saveSubmoduleDependencies(Long source, Long[] dependencies) throws AdaptorException {
		for(final Long dependency : dependencies) {
			this.submoduleService.createDependency(dependency, source, this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId());
		}
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void saveSubmoduleDependants(Long source, Long[] dependants) throws AdaptorException {
		for(final Long dependant : dependants) {
			this.submoduleService.createDependency(source, dependant, this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId());
		}
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void removeSubmoduleDependency(Long dependency, Long dependant) throws AdaptorException {
		this.submoduleService.deleteDependency(dependency, dependant, this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId());
	}

}
