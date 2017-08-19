package com.kota.stratagem.ejbservice.dispatch;

import javax.ejb.Stateless;
import javax.naming.NamingException;

import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.persistence.util.Constants;

@Stateless(mappedName = "ejb/objectiveOverseer")
public class ObjectiveLifecycleOverseerImpl extends AbstractLifecycleMessageProducer implements ObjectiveLifecycleOverseer {

	public ObjectiveLifecycleOverseerImpl() throws NamingException {
		super();
	}

	@Override
	public void created(ObjectiveRepresentor representor) {
		this.sendCreationTextMessage(representor.toTextMessage());
	}

	@Override
	public void assigned(AppUserObjectiveAssignmentRepresentor assignment) {
		this.sendAssignmentTextMessage(assignment.toTextMessage());
	}

	@Override
	public void modified(ObjectiveRepresentor origin, ObjectiveRepresentor result) {
		this.sendModificationTextMessage(origin.toTextMessage() + Constants.PAYLOAD_SEPARATOR + result.toTextMessage());
	}

	@Override
	public void deleted(ObjectiveRepresentor representor) {
		this.sendDeletionTextMessage(representor.toTextMessage());
	}

}
