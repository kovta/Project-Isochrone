package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.comparison.dualistic.TeamNameComparator;
import com.kota.stratagem.ejbservice.context.EJBServiceConfiguration;
import com.kota.stratagem.ejbservice.converter.TeamConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbservice.qualifier.TeamOriented;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.AbstractTeamAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.TeamService;
import com.kota.stratagem.security.context.SessionContextAccessor;
import com.kota.stratagem.security.domain.RestrictionLevel;
import com.kota.stratagem.security.interceptor.Authorized;

@Regulated
@Stateless(mappedName = EJBServiceConfiguration.TEAM_PROTOCOL_SIGNATURE)
public class TeamProtocolImpl implements TeamProtocol {

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserService appUserService;

	@Inject
	private TeamConverter converter;

	@Inject
	private SessionContextAccessor sessionContextAccessor;

	@Inject
	@TeamOriented
	private DTOExtensionManager extensionManager;

	@Override
	public TeamRepresentor getTeam(Long id) throws AdaptorException {
		return (TeamRepresentor) this.extensionManager.prepare(this.converter.toComplete(this.teamService.readComplete(id)));
	}

	@Override
	public List<TeamRepresentor> getAllTeams() throws AdaptorException {
		return (List<TeamRepresentor>) this.extensionManager
				.prepareBatch(new ArrayList<TeamRepresentor>(this.converter.toSimplified(this.teamService.readAll())));
	}

	@Override
	public List<TeamRepresentor> getAssignableTeams(ObjectiveRepresentor objective) throws AdaptorException {
		return this.retrieveObjectRelatedTeamList(objective.getAssignedTeams());
	}

	@Override
	public List<TeamRepresentor> getAssignableTeams(ProjectRepresentor project) throws AdaptorException {
		return this.retrieveObjectRelatedTeamList(project.getAssignedTeams());
	}

	@Override
	public List<TeamRepresentor> getAssignableTeams(SubmoduleRepresentor submodule) throws AdaptorException {
		return this.retrieveObjectRelatedTeamList(submodule.getAssignedTeams());
	}

	@Override
	public List<TeamRepresentor> getAssignableTeams(TaskRepresentor task) throws AdaptorException {
		return this.retrieveObjectRelatedTeamList(task.getAssignedTeams());
	}

	private <T extends AbstractTeamAssignmentRepresentor> List<TeamRepresentor> retrieveObjectRelatedTeamList(List<T> assignments) {
		final List<TeamRepresentor> teamList = new ArrayList<>();
		final List<TeamRepresentor> allTeams = (List<TeamRepresentor>) this.extensionManager
				.prepareBatch(new ArrayList<TeamRepresentor>(this.converter.toSubComplete(this.teamService.readAll())));
		final String operator = this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName();
		for (final TeamRepresentor team : allTeams) {
			boolean subordinateLeader = false, contains = false;
			for (final RoleRepresentor subordinateRole : RoleRepresentor.valueOf(this.appUserService.readElementary(operator).getRole().toString())
					.getSubordinateRoles()) {
				if (subordinateRole.toString().equals(team.getLeader().getRole().toString())) {
					subordinateLeader = true;
				}
			}
			for (final AbstractTeamAssignmentRepresentor assignment : assignments) {
				if (team.equals(assignment.getRecipient())) {
					contains = true;
				}
			}
			if ((subordinateLeader || team.getLeader().getName().equals(operator) || team.getCreator().getName().equals(operator)) && !contains) {
				teamList.add(team);
			}
		}
		Collections.sort(teamList, new TeamNameComparator());
		return teamList;
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public TeamRepresentor saveTeam(Long id, String name, String leader) throws AdaptorException {
		return (TeamRepresentor) this.extensionManager.prepare(this.converter.toComplete(((id != null) && this.teamService.exists(id))
				? this.teamService.update(id, name, leader, this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName())
				: this.teamService.create(name, leader, this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName())));
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_MANAGER_LEVEL)
	public void removeTeam(Long id) throws AdaptorException {
		try {
			this.teamService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void saveTeamMemberships(Long id, String[] members) throws AdaptorException {
		for (final String member : members) {
			this.teamService.createMembership(id, member,
					this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId());
		}
	}

	@Override
	@Authorized(RestrictionLevel.GENERAL_USER_LEVEL)
	public void removeTeamMembership(Long id, String member) throws AdaptorException {
		this.teamService.deleteMembership(id, member,
				this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId());
	}

}
