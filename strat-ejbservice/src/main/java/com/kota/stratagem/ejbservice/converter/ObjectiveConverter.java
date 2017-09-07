package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.persistence.entity.Objective;

public interface ObjectiveConverter {

	ObjectiveRepresentor toElementary(Objective objective);

	ObjectiveRepresentor toSimplified(Objective objective);

	ObjectiveRepresentor toComplete(Objective objective);

	Set<ObjectiveRepresentor> toElementary(Set<Objective> objectives);

	Set<ObjectiveRepresentor> toSimplified(Set<Objective> objectives);

	Set<ObjectiveRepresentor> toComplete(Set<Objective> objectives);

}
