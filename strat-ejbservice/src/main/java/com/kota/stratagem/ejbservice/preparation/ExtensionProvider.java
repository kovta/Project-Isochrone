package com.kota.stratagem.ejbservice.preparation;

import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbserviceclient.domain.AbstractProgressionRepresentor;

public interface ExtensionProvider {

	void provideEstimations(CPMResult result, AbstractProgressionRepresentor representor);

}
