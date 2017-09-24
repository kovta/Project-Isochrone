package com.kota.stratagem.ejbservice.comparison.dualistic;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;

public class TeamNameComparator implements Comparator<TeamRepresentor> {

	@Override
	public int compare(TeamRepresentor o1, TeamRepresentor o2) {
		return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
	}

}
