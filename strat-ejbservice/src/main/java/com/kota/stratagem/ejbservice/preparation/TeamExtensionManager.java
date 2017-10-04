package com.kota.stratagem.ejbservice.preparation;

import java.util.Collections;
import java.util.List;

import com.kota.stratagem.ejbservice.comparison.dualistic.TeamAssignmentCreationDateComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TeamNameComparator;
import com.kota.stratagem.ejbservice.interceptor.Certified;
import com.kota.stratagem.ejbservice.qualifier.TeamOriented;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;

@TeamOriented
public class TeamExtensionManager extends AbstractDTOExtensionManager {

	TeamRepresentor representor;
	List<TeamRepresentor> representors;

	@Override
	@Certified(TeamRepresentor.class)
	public Object prepare(Object representor) {
		this.representor = (TeamRepresentor) representor;
		return super.prepare(representor);
	}

	@Override
	@Certified(TeamRepresentor.class)
	public Object prepareForOwner(Object representor) {
		this.representor = (TeamRepresentor) representor;
		return super.prepareForOwner(representor);
	}

	@Override
	@Certified(TeamRepresentor.class)
	public List<?> prepareBatch(List<?> representors) {
		this.representors = (List<TeamRepresentor>) representors;
		return super.prepareBatch(representors);
	}

	@Override
	protected void addRepresentorSpecificProperties() {

	}

	@Override
	protected void addOwnerDependantProperties() {

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
