package com.kota.stratagem.ejbservice.comparison.dualistic;

import java.util.Comparator;

import com.kota.stratagem.ejbserviceclient.domain.AbstractTeamAssignmentRepresentor;

public class TeamAssignmentRecipientNameComparator implements Comparator<AbstractTeamAssignmentRepresentor> {

	@Override
	public int compare(AbstractTeamAssignmentRepresentor o1, AbstractTeamAssignmentRepresentor o2) {
		return o1.getRecipient().getName().toLowerCase().compareTo(o2.getRecipient().getName().toLowerCase());
	}

}
