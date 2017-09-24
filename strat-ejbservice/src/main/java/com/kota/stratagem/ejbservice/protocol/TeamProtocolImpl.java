package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.converter.TeamConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbservice.qualifier.TeamOriented;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.TeamService;

@Regulated
@Stateless
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
		return this.extensionManager.prepare(this.converter.toComplete(this.teamService.readComplete(id)));
	}

	@Override
	public List<TeamRepresentor> getAllTeams() throws AdaptorException {
		return this.extensionManager.prepareTeams(new ArrayList<TeamRepresentor>(this.converter.toSimplified(this.teamService.readAll())));
	}

	@Override
	public TeamRepresentor saveTeam(Long id, String name, String leader) throws AdaptorException {
		return this.extensionManager.prepare(this.converter.toComplete(((id != null) && this.teamService.exists(id))
				? this.teamService.update(id, name, leader, this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName())
				: this.teamService.create(name, leader, this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName())));
	}

	@Override
	public void removeTeam(Long id) throws AdaptorException {
		try {
			this.teamService.delete(id);
		} catch (final CoherentPersistenceServiceException e) {
			final ApplicationError error = ApplicationError.valueOf(e.getError().name());
			throw new AdaptorException(error, e.getLocalizedMessage(), e.getField());
		}
	}

	@Override
	public void saveTeamMemberships(Long id, String[] members) throws AdaptorException {
		for (final String member : members) {
			this.teamService.createMembership(id, member,
					this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId());
		}
	}

	@Override
	public void removeTeamMembership(Long id, String member) throws AdaptorException {
		this.teamService.deleteMembership(id, member,
				this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId());
	}

}
