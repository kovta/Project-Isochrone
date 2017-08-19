package com.kota.stratagem.persistence.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Notification;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Stateless(mappedName = "ejb/notificationService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class NotificationServiceImpl implements NotificationService {

	private static final Logger LOGGER = Logger.getLogger(NotificationServiceImpl.class);

	@PersistenceContext(unitName = "strat-persistence-unit")
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@Override
	public Notification create(Long inducer, String message) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Notification (inducer: " + inducer + ", message: " + message + ")");
		}
		try {
			final AppUser operator = this.appUserService.readElementary(inducer);
			final Notification notification = new Notification(message, new Date(), operator);
			this.entityManager.persist(notification);
			this.entityManager.flush();
			return notification;
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting Notification (" + message + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void dismiss(Long id, String operator) throws PersistenceServiceException {
		// TODO Auto-generated method stub

	}

}
