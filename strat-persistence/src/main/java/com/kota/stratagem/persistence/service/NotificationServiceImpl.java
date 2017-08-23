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
	public void create(Long inducer, String message, Long[] recipients) throws PersistenceServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Notification (inducer: " + inducer + ", message: " + message + ")");
		}
		try {
			if (recipients.length != 0) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Notification created with empty recipient list (inducer: " + inducer + ", message: " + message + ")");
				}
			}
			final AppUser operator = this.appUserService.readElementary(inducer);
			final Notification notification = new Notification(message, new Date(), operator);
			final Set<AppUser> recipientSet = new HashSet<AppUser>();
			for (final Long recipient : recipients) {
				recipientSet.add(this.appUserService.readElementary(recipient));
			}
			notification.setRecipients(recipientSet);
			this.entityManager.merge(notification);
			this.entityManager.flush();
			if (recipients.length == 0) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Notification created with empty recipient list as log entry (inducer: " + inducer + ", message: " + message + ")");
				}
			}
		} catch (final Exception e) {
			throw new PersistenceServiceException("Unknown error during persisting Notification (" + message + ")! " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void dismiss(Long id, String operator) throws PersistenceServiceException {
		// TODO Auto-generated method stub

	}

}
