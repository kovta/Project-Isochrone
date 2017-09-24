package com.kota.stratagem.ejbservice.preparation;

import java.util.Collections;
import java.util.List;

import com.kota.stratagem.ejbservice.comparison.dualistic.TeamAssignmentCreationDateComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TeamNameComparator;
import com.kota.stratagem.ejbservice.qualifier.TeamOriented;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;

@TeamOriented
public class TeamExtensionManager extends AbstractDTOExtensionManager {

	TeamRepresentor representor;
	List<TeamRepresentor> representors;

	@Override
	public TeamRepresentor prepare(TeamRepresentor representor) {
		this.representor = representor;
		return super.prepare(representor);
	}

	@Override
	public List<TeamRepresentor> prepareTeams(List<TeamRepresentor> representors) {
		this.representors = representors;
		return super.prepareTeams(representors);
	}

	@Override
	protected void addRepresentorSpecificProperties() {

	}

	@Override
	protected void sortSpecializedCollections() {

	}

	@Override
	protected void sortBaseCollections() {
		Collections.sort(this.representor.getObjectives(), new TeamAssignmentCreationDateComparator());
		Collections.sort(this.representor.getProjects(), new TeamAssignmentCreationDateComparator());
		Collections.sort(this.representor.getSubmodules(), new TeamAssignmentCreationDateComparator());
		Collections.sort(this.representor.getTasks(), new TeamAssignmentCreationDateComparator());
	}

	@Override
	protected void sortJointCollection() {
		Collections.sort(this.representors, new TeamNameComparator());
	}

}
