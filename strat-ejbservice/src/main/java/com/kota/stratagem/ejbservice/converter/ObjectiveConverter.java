package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.persistence.entity.Objective;

@Local
public interface ObjectiveConverter {

	ObjectiveRepresentor to(Objective objective);

	ObjectiveRepresentor toElementary(Objective objective);

	Set<ObjectiveRepresentor> to(Set<Objective> objectives);

}
