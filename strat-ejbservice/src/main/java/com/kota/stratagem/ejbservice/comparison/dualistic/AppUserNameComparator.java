package com.kota.stratagem.ejbservice.comparison.dualistic;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;

public class AppUserNameComparator implements Comparator<AppUserRepresentor> {

	@Override
	public int compare(AppUserRepresentor o1, AppUserRepresentor o2) {
		return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
	}

}
