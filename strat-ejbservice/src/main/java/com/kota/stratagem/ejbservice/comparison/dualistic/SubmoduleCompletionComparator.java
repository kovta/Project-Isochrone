package com.kota.stratagem.ejbservice.comparison.dualistic;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;

public class SubmoduleCompletionComparator implements Comparator<SubmoduleRepresentor> {

	@Override
	public int compare(SubmoduleRepresentor o1, SubmoduleRepresentor o2) {
		final int c = (int) (-1 * (o1.getCompletion() - o2.getCompletion()));
		if (c == 0) {
			return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
		}
		return c;
	}

}
