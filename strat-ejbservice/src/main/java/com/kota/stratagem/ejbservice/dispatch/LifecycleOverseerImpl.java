package com.kota.stratagem.ejbservice.dispatch;

import javax.ejb.Stateless;
import javax.naming.NamingException;

@Stateless(mappedName = "ejb/lifecycleOverseer")
public class LifecycleOverseerImpl extends AbstractLifecycleMessageProducer implements LifecycleOverseer {

	public LifecycleOverseerImpl() throws NamingException {
		super();
	}

	@Override
	public void created(String representor) {
		this.sendCreationTextMessage(representor);
	}

	@Override
	public void assigned(String assignment) {
		this.sendAssignmentTextMessage(assignment);
	}

	@Override
	public void dissociated(String assignment) {
		this.sendDissociationTextMessage(assignment);
	}

	@Override
	public void modified(String representors) {
		this.sendModificationTextMessage(representors);
	}

	@Override
	public void deleted(String representor) {
		this.sendDeletionTextMessage(representor);
	}

	@Override
	public void configured(String representors) {
		this.sendConfigurationTextMessage(representors);
	}

	@Override
	public void deconfigured(String representors) {
		this.sendDeconfigurationTextMessage(representors);
	}

}
