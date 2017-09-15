package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.persistence.entity.Objective;

@Local
public interface ObjectiveConverter {

	ObjectiveRepresentor toElementary(Objective objective);

	ObjectiveRepresentor toDispatchable(Objective objective);

	ObjectiveRepresentor toSimplified(Objective objective);

	ObjectiveRepresentor toComplete(Objective objective);

	Set<ObjectiveRepresentor> toElementary(Set<Objective> objectives);

	Set<ObjectiveRepresentor> toSimplified(Set<Objective> objectives);

	Set<ObjectiveRepresentor> toComplete(Set<Objective> objectives);

}
