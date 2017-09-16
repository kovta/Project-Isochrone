package com.kota.stratagem.ejbservice.comparison.congregated;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;

public class SubmoduleSummaryComparator implements Comparator<SubmoduleRepresentor> {

	@Override
	public int compare(SubmoduleRepresentor o1, SubmoduleRepresentor o2) {
		final int c = o1.getTasks().size() - o2.getTasks().size();
		if (c == 0) {
			return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
		}
		return c * -1;
	}

}
