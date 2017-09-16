package com.kota.stratagem.ejbservice.comparison.congregated;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;

public class ProjectSummaryComparator implements Comparator<ProjectRepresentor> {

	@Override
	public int compare(ProjectRepresentor o1, ProjectRepresentor o2) {
		final int c1 = o1.getSubmodules().size() - o2.getSubmodules().size();
		if (c1 == 0) {
			final int c2 = o1.getTasks().size() - o2.getTasks().size();
			if (c2 == 0) {
				return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
			}
			return c2 * -1;
		}
		return c1 * -1;
	}

}
