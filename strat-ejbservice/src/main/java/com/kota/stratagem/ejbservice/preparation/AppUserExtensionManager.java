package com.kota.stratagem.ejbservice.preparation;

import java.util.Collections;
import java.util.List;

import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserAssignmentCreationDateComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.NotificationCreationDateComparator;
import com.kota.stratagem.ejbservice.interceptor.Certified;
import com.kota.stratagem.ejbservice.qualifier.AppUserOriented;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;

@AppUserOriented
public class AppUserExtensionManager extends AbstractDTOExtensionManager {

	AppUserRepresentor representor;
	List<AppUserRepresentor> representors;

	@Override
	@Certified(AppUserRepresentor.class)
	public Object prepare(Object representor) {
		this.representor = (AppUserRepresentor) representor;
		return super.prepare(representor);
	}

	@Override
	@Certified(AppUserRepresentor.class)
	public Object prepareForOwner(Object representor) {
		this.representor = (AppUserRepresentor) representor;
		return super.prepareForOwner(representor);
	}

	@Override
	@Certified(AppUserRepresentor.class)
	public List<?> prepareBatch(List<?> representors) {
		this.representors = (List<AppUserRepresentor>) representors;
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
		Collections.sort(this.representor.getNotifications(), new NotificationCreationDateComparator());
		Collections.sort(this.representor.getObjectives(), new AppUserAssignmentCreationDateComparator());
		Collections.sort(this.representor.getProjects(), new AppUserAssignmentCreationDateComparator());
		Collections.sort(this.representor.getSubmodules(), new AppUserAssignmentCreationDateComparator());
		Collections.sort(this.representor.getTasks(), new AppUserAssignmentCreationDateComparator());
	}

	@Override
	protected void sortJointCollection() {
		Collections.sort(this.representors, new AppUserNameComparator());
	}

}
