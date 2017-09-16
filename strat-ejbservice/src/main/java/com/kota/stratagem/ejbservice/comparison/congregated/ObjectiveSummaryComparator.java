package com.kota.stratagem.ejbservice.comparison.congregated;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;

public class ObjectiveSummaryComparator implements Comparator<ObjectiveRepresentor> {

	@Override
	public int compare(ObjectiveRepresentor o1, ObjectiveRepresentor o2) {
		final int c1 = o1.getPriority() - o2.getPriority();
		if (c1 == 0) {
			final int c2 = o1.getProjects().size() - o2.getProjects().size();
			if (c2 == 0) {
				final int c3 = o1.getTasks().size() - o2.getTasks().size();
				if (c3 == 0) {
					return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
				}
				return c3 * -1;
			}
			return c2 * -1;
		}
		return c1;
	}

}
