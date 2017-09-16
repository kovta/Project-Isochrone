package com.kota.stratagem.ejbservice.comparison.congregated;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;

public class ObjectiveClusterComparator implements Comparator<ObjectiveRepresentor> {

	@Override
	public int compare(ObjectiveRepresentor o1, ObjectiveRepresentor o2) {
		final int c = o1.getPriority() - o2.getPriority();
		if (c == 0) {
			return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
		}
		return c;
	}

}
