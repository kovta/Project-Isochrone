package com.kota.stratagem.ejbservice.converter;

import java.util.List;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.NotificationRepresentor;
import com.kota.stratagem.persistence.entity.Notification;

@Local
public interface NotificationConverter {

	NotificationRepresentor to(Notification notification);

	List<NotificationRepresentor> to(List<Notification> notifications);

}
