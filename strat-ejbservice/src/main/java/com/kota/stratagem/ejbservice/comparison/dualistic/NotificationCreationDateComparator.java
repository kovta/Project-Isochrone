package com.kota.stratagem.ejbservice.comparison.dualistic;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.NotificationRepresentor;

public class NotificationCreationDateComparator implements Comparator<NotificationRepresentor> {

	@Override
	public int compare(NotificationRepresentor o1, NotificationRepresentor o2) {
		return o1.getCreationDate().compareTo(o2.getCreationDate());
	}

}
