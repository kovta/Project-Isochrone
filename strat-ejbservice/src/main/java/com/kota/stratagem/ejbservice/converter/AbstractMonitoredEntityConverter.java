package com.kota.stratagem.ejbservice.converter;

import javax.ejb.EJB;

import com.kota.stratagem.ejbserviceclient.domain.AbstractMonitoredRepresentor;
import com.kota.stratagem.persistence.entity.AbstractMonitoredEntity;

public abstract class AbstractMonitoredEntityConverter {

	@EJB
	private AppUserConverter appUserConverter;

	protected <T extends AbstractMonitoredEntity, E extends AbstractMonitoredRepresentor> E inculdeMonitoringFields(E target, T object) {
		target.setCreator(this.appUserConverter.toElementary(object.getCreator()));
		target.setCreationDate(object.getCreationDate());
		target.setModifier(this.appUserConverter.toElementary(object.getModifier()));
		target.setModificationDate(object.getModificationDate());
		return target;
	}

}
