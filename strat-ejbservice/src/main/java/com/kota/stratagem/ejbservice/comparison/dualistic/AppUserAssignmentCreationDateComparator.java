package com.kota.stratagem.ejbservice.comparison.dualistic;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.AbstractAppUserAssignmentRepresentor;

public class AppUserAssignmentCreationDateComparator implements Comparator<AbstractAppUserAssignmentRepresentor> {

	@Override
	public int compare(AbstractAppUserAssignmentRepresentor o1, AbstractAppUserAssignmentRepresentor o2) {
		return o1.getCreationDate().compareTo(o2.getCreationDate());
	}

}
