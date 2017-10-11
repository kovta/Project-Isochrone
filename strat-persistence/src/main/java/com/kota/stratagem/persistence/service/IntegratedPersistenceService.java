package com.kota.stratagem.persistence.service;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class IntegratedPersistenceService {

	@Inject
	protected EntityManager entityManager;

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
