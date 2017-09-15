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

import org.apache.log4j.Logger;

import com.kota.stratagem.persistence.context.PersistenceServiceConfiguration;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Notification;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.interceptor.Contained;

@Contained
@Stateless(mappedName = PersistenceServiceConfiguration.NOTIFICATION_SERVICE_SIGNATURE)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class NotificationServiceImpl implements NotificationService {

	private static final Logger LOGGER = Logger.getLogger(NotificationServiceImpl.class);

	@Inject
	private EntityManager entityManager;

	@EJB
	private AppUserService appUserService;

	@Override
	public void create(Long inducer, String message, Long[] recipients) throws PersistenceServiceException {
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
	}

	@Override
	public void dismiss(Long id, String operator) throws PersistenceServiceException {
		// TODO Auto-generated method stub

	}

}
