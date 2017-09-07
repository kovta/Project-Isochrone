package com.kota.stratagem.ejbservice.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.kota.stratagem.ejbserviceclient.domain.NotificationRepresentor;
import com.kota.stratagem.persistence.entity.Notification;

public class NotificationConverterImpl implements NotificationConverter {

	@Inject
	private AppUserConverter appUserConverter;

	@Override
	public NotificationRepresentor to(Notification notification) {
		return new NotificationRepresentor(notification.getId(), notification.getMessage(), notification.getCreationDate(),
				this.appUserConverter.toElementary(notification.getInducer()));
	}

	@Override
	public List<NotificationRepresentor> to(List<Notification> notifications) {
		final List<NotificationRepresentor> representors = new ArrayList<>();
		for (final Notification notification : notifications) {
			representors.add(this.to(notification));
		}
		return representors;
	}

}
