package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.kota.stratagem.persistence.context.PersistenceServiceConfiguration;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;
import com.kota.stratagem.persistence.interceptor.Contained;
import com.kota.stratagem.persistence.parameter.TeamParameter;
import com.kota.stratagem.persistence.query.TeamQuery;
import com.kota.stratagem.persistence.util.PersistenceApplicationError;

@Contained
@Stateless(mappedName = PersistenceServiceConfiguration.TEAM_SERVICE_SIGNATURE)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TeamServiceImpl implements TeamService {

	@Inject
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@Override
	public Team create(String name, String leader, String creator) {
		final AppUser operator = this.appUserService.readElementary(creator);
		final AppUser selectedLeader = this.appUserService.readElementary(leader);
		final Team team = new Team(name, selectedLeader, new Date(), new Date());
		if (operator.getId() == selectedLeader.getId()) {
			team.setCreator(selectedLeader);
			team.setModifier(selectedLeader);
		} else {
			team.setCreator(operator);
			team.setModifier(operator);
		}
		this.entityManager.merge(team);
		this.entityManager.flush();
		return team;
	}

	@Override
	public Team readElementary(Long id) {
		return this.entityManager.createNamedQuery(TeamQuery.GET_BY_ID, Team.class).setParameter(TeamParameter.ID, id).getSingleResult();
	}

	@Override
	public Team readWithLeader(Long id) {
		return this.entityManager.createNamedQuery(TeamQuery.GET_BY_ID_WITH_LEADER, Team.class).setParameter(TeamParameter.ID, id).getSingleResult();
	}

	@Override
	public Team readWithLeaderAndMembers(Long id) {
		return this.entityManager.createNamedQuery(TeamQuery.GET_BY_ID_WITH_LEADER_AND_MEMBERS, Team.class).setParameter(TeamParameter.ID, id)
				.getSingleResult();
	}

	@Override
	public Team readComplete(Long id) {
		return this.entityManager.createNamedQuery(TeamQuery.GET_BY_ID_COMPLETE, Team.class).setParameter(TeamParameter.ID, id).getSingleResult();
	}

	@Override
	public Set<Team> readAll() {
		return new HashSet<Team>(this.entityManager.createNamedQuery(TeamQuery.GET_ALL_TEAMS, Team.class).getResultList());
	}

	@Override
	public Team update(Long id, String name, String leader, String modifier) {
		final Team team = this.readElementary(id);
		final AppUser operator = this.appUserService.readElementary(modifier);
		final AppUser selectedLeader = this.appUserService.readElementary(leader);
		team.setName(name);
		if (team.getLeader() != selectedLeader) {
			team.setLeader(selectedLeader);
		}
		if (team.getLeader().getId() == operator.getId()) {
			team.setModifier(team.getLeader());
		} else {
			team.setModifier(operator);
		}
		team.setModificationDate(new Date());
		this.entityManager.merge(team);
		return this.readComplete(id);
	}

	@Override
	public void delete(Long id) throws CoherentPersistenceServiceException {
		if (this.exists(id)) {
			if (this.readComplete(id).getMembers().size() == 0) {
				this.entityManager.createNamedQuery(TeamQuery.REMOVE_BY_ID).setParameter(TeamParameter.ID, id).executeUpdate();
			} else {
				throw new CoherentPersistenceServiceException(PersistenceApplicationError.HAS_DEPENDENCY, "Team has undeleted dependency(s)", id.toString());
			}
		} else {
			throw new CoherentPersistenceServiceException(PersistenceApplicationError.NON_EXISTANT, "Team doesn't exist", id.toString());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exists(Long id) {
		return this.entityManager.createNamedQuery(TeamQuery.COUNT_BY_ID, Long.class).setParameter(TeamParameter.ID, id).getSingleResult() == 1;
	}

	@Override
	public void createMembership(Long id, String member, Long operator) {
		final Team team = this.readWithLeaderAndMembers(id);
		final AppUser selectedMember = this.appUserService.readElementary(member);
		final Set<AppUser> members = team.getMembers();
		members.add(selectedMember);
		team.setMembers(members);
		team.setModificationDate(new Date());
		this.entityManager.merge(team);
	}

	@Override
	public void deleteMembership(Long id, String member, Long operator) {
		final Team team = this.readWithLeaderAndMembers(id);
		final AppUser selectedMember = this.appUserService.readElementary(member);
		final Set<AppUser> members = team.getMembers();
		members.remove(selectedMember);
		team.setMembers(members);
		team.setModificationDate(new Date());
		this.entityManager.merge(team);
	}

}
