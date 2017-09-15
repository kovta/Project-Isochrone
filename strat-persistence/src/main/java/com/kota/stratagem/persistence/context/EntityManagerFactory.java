package com.kota.stratagem.persistence.context;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerFactory {

	@PersistenceContext(unitName = PersistenceServiceConfiguration.PERSISTENCE_UNIT_NAME)
	@Produces
	private EntityManager entityManager;

}
