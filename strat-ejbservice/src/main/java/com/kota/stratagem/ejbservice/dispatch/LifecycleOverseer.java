package com.kota.stratagem.ejbservice.dispatch;

import javax.ejb.Local;

@Local
public interface LifecycleOverseer {

	void created(String representor);

	void assigned(String assignment);

	void dissociated(String assignment);

	void modified(String representors);

	void deleted(String representor);

	void configured(String representors);

	void deconfigured(String representors);

}
