package com.kota.stratagem.persistence.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.entity.Remedy;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.parameter.RemedyParameter;
import com.kota.stratagem.persistence.query.RemedyQuery;

@Stateless(mappedName = "ejb/remedyService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RemedyServiceImpl implements RemedyService {

	private static final Logger LOGGER = Logger.getLogger(RemedyServiceImpl.class);

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@Override
	public Remedy read(Long id) throws PersistenceServiceException {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Get Remedy by id (" + id + ")");
		}
		Remedy result = null;
		try {
			result = this.entityManager.createNamedQuery(RemedyQuery.GET_BY_ID, Remedy.class).setParameter(RemedyParameter.ID, id).getSingleResult();
		} catch(final Exception e) {
			throw new PersistenceServiceException("Unknown error when fetching Remedy by id (" + id + ")! " + e.getLocalizedMessage(), e);
		}
		return result;
	}

}
