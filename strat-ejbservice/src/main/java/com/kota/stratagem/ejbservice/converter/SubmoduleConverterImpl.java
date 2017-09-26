package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbservice.converter.delegation.AssignmentConverter;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.persistence.entity.AppUserSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.TeamSubmoduleAssignment;

@Stateless
public class SubmoduleConverterImpl extends AbstractMonitoredEntityConverter implements SubmoduleConverter {

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private TaskConverter taskConverter;

	@EJB
	private AppUserConverter appUserConverter;

	@EJB
	private AssignmentConverter assignmentConverter;

	@Override
	public SubmoduleRepresentor toElementary(Submodule submodule) {
		final SubmoduleRepresentor representor = submodule.getId() != null
				? new SubmoduleRepresentor(submodule.getId(), submodule.getName(), submodule.getDescription().trim(), submodule.getDeadline(),
						submodule.getProject() != null ? this.projectConverter.toElementary(submodule.getProject()) : null)
				: new SubmoduleRepresentor(submodule.getName(), submodule.getDescription().trim(), submodule.getDeadline(),
						submodule.getProject() != null ? this.projectConverter.toElementary(submodule.getProject()) : null);
		return representor;
	}

	@Override
	public SubmoduleRepresentor toDispatchable(Submodule submodule) {
		return this.inculdeMonitoringFields(this.toElementary(submodule), submodule);
	}

	@Override
	public SubmoduleRepresentor toSimplified(Submodule submodule) {
		final SubmoduleRepresentor representor = this.toElementary(submodule);
		if (submodule.getTasks() != null) {
			for (final Task task : submodule.getTasks()) {
				representor.addTask(this.taskConverter.toElementary(task));
			}
		}
		return representor;
	}

	@Override
	public SubmoduleRepresentor toComplete(Submodule submodule) {
		final SubmoduleRepresentor representor = this.toSimplified(submodule);
		if (submodule.getAssignedTeams() != null) {
			for (final TeamSubmoduleAssignment team : submodule.getAssignedTeams()) {
				representor.addTeam(this.assignmentConverter.to(team));
			}
		}
		if (submodule.getAssignedUsers() != null) {
			for (final AppUserSubmoduleAssignment user : submodule.getAssignedUsers()) {
				representor.addUser(this.assignmentConverter.to(user));
			}
		}
		return this.inculdeMonitoringFields(representor, submodule);
	}

	@Override
	public Set<SubmoduleRepresentor> toElementary(Set<Submodule> submodules) {
		final Set<SubmoduleRepresentor> representors = new HashSet<SubmoduleRepresentor>();
		for (final Submodule submodule : submodules) {
			representors.add(this.toElementary(submodule));
		}
		return representors;
	}

	@Override
	public Set<SubmoduleRepresentor> toSimplified(Set<Submodule> submodules) {
		final Set<SubmoduleRepresentor> representors = new HashSet<SubmoduleRepresentor>();
		for (final Submodule submodule : submodules) {
			representors.add(this.toSimplified(submodule));
		}
		return representors;
	}

	@Override
	public Set<SubmoduleRepresentor> toComplete(Set<Submodule> submodules) {
		final Set<SubmoduleRepresentor> representors = new HashSet<SubmoduleRepresentor>();
		for (final Submodule submodule : submodules) {
			representors.add(this.toComplete(submodule));
		}
		return representors;
	}

}
