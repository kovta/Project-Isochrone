package com.kota.stratagem.ejbservice.comparison.dualistic;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;

public class ProjectNameComparator implements Comparator<ProjectRepresentor> {

	@Override
	public int compare(ProjectRepresentor o1, ProjectRepresentor o2) {
		return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
	}

}
