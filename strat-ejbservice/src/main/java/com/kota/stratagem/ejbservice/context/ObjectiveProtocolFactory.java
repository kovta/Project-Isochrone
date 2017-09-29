package com.kota.stratagem.ejbservice.context;

import javax.enterprise.context.ApplicationScoped;

import com.kota.stratagem.ejbservice.domain.ConductType;
import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocol;
import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocolImpl;
import com.kota.stratagem.ejbservice.qualifier.ProtocolConduct;

@Deprecated
@ApplicationScoped
public class ObjectiveProtocolFactory {

	// @Produces
	// @ProtocolConduct(ConductType.DISPATCHED)
	// public ObjectiveProtocol getDispatchedObjectiveProtocol() {
	// return new ObjectiveProtocolDispatchDecorator();
	// }

	// @Produces
	@ProtocolConduct(ConductType.ISOLATED)
	public ObjectiveProtocol getIsolateObjectiveProtocol() {
		return new ObjectiveProtocolImpl();
	}

}
