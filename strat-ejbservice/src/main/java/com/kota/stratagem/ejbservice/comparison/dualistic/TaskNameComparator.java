package com.kota.stratagem.ejbservice.comparison.dualistic;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

public class TaskNameComparator implements Comparator<TaskRepresentor> {

	@Override
	public int compare(TaskRepresentor o1, TaskRepresentor o2) {
		return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
	}

}
