package com.kota.stratagem.persistence.context;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerFactory {

	@Produces
	@PersistenceContext(unitName = PersistenceServiceConfiguration.PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

}
