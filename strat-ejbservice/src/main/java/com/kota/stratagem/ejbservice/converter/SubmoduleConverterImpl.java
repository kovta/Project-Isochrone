package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;

@Stateless
public class SubmoduleConverterImpl implements SubmoduleConverter {

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private TaskConverter taskConverter;

	@EJB
	private TeamConverter teamConverter;

	@EJB
	private AppUserConverter appUserConverter;

	@Override
	public SubmoduleRepresentor to(Submodule submodule) {
		final SubmoduleRepresentor representor = this.toElementary(submodule);
		if (submodule.getTasks() != null) {
			for (final Task task : submodule.getTasks()) {
				representor.addTask(this.taskConverter.toElementary(task));
			}
		}
		// if (project.getAssignedTeams() != null) {
		// for (final Team team : project.getAssignedTeams()) {
		// representor.addTeam(this.teamConverter.to(team));
		// }
		// }
		// if (project.getAssignedUsers() != null) {
		// for (final AppUser user : project.getAssignedUsers()) {
		// representor.addUser(this.appUserConverter.to(user));
		// }
		// }
		return representor;
	}

	@Override
	public SubmoduleRepresentor toElementary(Submodule submodule) {
		final SubmoduleRepresentor representor = submodule.getId() != null
				? new SubmoduleRepresentor(submodule.getId(), submodule.getName(), submodule.getDescription(), submodule.getDeadline(),
						this.appUserConverter.toElementary(submodule.getCreator()), submodule.getCreationDate(),
						this.appUserConverter.toElementary(submodule.getModifier()), submodule.getModificationDate(),
						submodule.getProject() != null ? this.projectConverter.toElementary(submodule.getProject()) : null)
				: new SubmoduleRepresentor(submodule.getName(), submodule.getDescription(), submodule.getDeadline(),
						this.appUserConverter.toElementary(submodule.getCreator()), submodule.getCreationDate(),
						this.appUserConverter.toElementary(submodule.getModifier()), submodule.getModificationDate(),
						submodule.getProject() != null ? this.projectConverter.toElementary(submodule.getProject()) : null);
		return representor;
	}

	@Override
	public Set<SubmoduleRepresentor> to(Set<Submodule> submodules) {
		final Set<SubmoduleRepresentor> representors = new HashSet<SubmoduleRepresentor>();
		for (final Submodule submodule : submodules) {
			representors.add(this.to(submodule));
		}
		return representors;
	}

}
