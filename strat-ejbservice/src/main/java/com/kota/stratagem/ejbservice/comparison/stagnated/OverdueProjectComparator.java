package com.kota.stratagem.ejbservice.comparison.stagnated;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;

public class OverdueProjectComparator implements Comparator<ProjectRepresentor> {

	@Override
	public int compare(ProjectRepresentor o1, ProjectRepresentor o2) {
		final int c = o1.getDeadline().compareTo(o2.getDeadline());
		if (c == 0) {
			return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
		}
		return c;
	}

}
