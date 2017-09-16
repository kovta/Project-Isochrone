package com.kota.stratagem.ejbservice.comparison.dualistic;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;

public class SubmoduleNameComparator implements Comparator<SubmoduleRepresentor> {

	@Override
	public int compare(SubmoduleRepresentor o1, SubmoduleRepresentor o2) {
		return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
	}

}
