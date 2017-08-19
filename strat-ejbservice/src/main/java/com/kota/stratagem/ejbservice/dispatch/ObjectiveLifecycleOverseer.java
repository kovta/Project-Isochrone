package com.kota.stratagem.ejbservice.dispatch;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;

@Local
public interface ObjectiveLifecycleOverseer {

	void created(ObjectiveRepresentor representor);

	void assigned(AppUserObjectiveAssignmentRepresentor assignment);

	void modified(ObjectiveRepresentor origin, ObjectiveRepresentor result);

	void deleted(ObjectiveRepresentor representor);

}
