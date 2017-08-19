package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.util.ApplicationError;
import com.kota.stratagem.persistence.entity.Notification;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.NotificationService;

@Stateless(mappedName = "ejb/notificationProtocol")
public class NotificationProtocolImpl implements NotificationProtocol {

	private static final Logger LOGGER = Logger.getLogger(NotificationProtocolImpl.class);

	@EJB
	private NotificationService notificationService;

	@EJB
	private AppUserService appUserService;

	@Override
	public void saveNotification(Long inducer, String message, Long[] recipients) throws AdaptorException {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create Notification (for: " + recipients.length + " recipients)");
			}
			final Notification notification = this.notificationService.create(inducer, message);
			for (final Long recipient : recipients) {
				this.appUserService.addNotification(recipient, notification);
			}
		} catch (final PersistenceServiceException e) {
			LOGGER.error(e, e);
			throw new AdaptorException(ApplicationError.UNEXPECTED, e.getLocalizedMessage());
		}

	}

}
