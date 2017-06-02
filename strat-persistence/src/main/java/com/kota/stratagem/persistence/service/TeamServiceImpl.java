package com.kota.stratagem.persistence.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.TeamParameter;
import com.kota.stratagem.persistence.query.TeamQuery;

@Stateless(mappedName = "ejb/teamService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TeamServiceImpl implements TeamService {

	private static final Logger LOGGER = Logger.getLogger(TeamServiceImpl.class);

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@Override
	public Team read(Long id) throws PersistenceServiceException {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Team by id (" + id + ")");
		}
		Team result = null;
		try {
			result = this.entityManager.createNamedQuery(TeamQuery.GET_BY_ID, Team.class).setParameter(TeamParameter.ID, id).getSingleResult();
		} catch(final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Team by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

}
