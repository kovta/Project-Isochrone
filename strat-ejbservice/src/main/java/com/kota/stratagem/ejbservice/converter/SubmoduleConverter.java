package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.persistence.entity.Submodule;

@Local
public interface SubmoduleConverter {

	SubmoduleRepresentor toElementary(Submodule submodule);

	SubmoduleRepresentor toDispatchable(Submodule submodule);

	SubmoduleRepresentor toDependencyExtended(Submodule submodule);

	SubmoduleRepresentor toSimplified(Submodule submodule);

	SubmoduleRepresentor toSubComplete(Submodule submodule);

	SubmoduleRepresentor toComplete(Submodule submodule);

	Set<SubmoduleRepresentor> toElementary(Set<Submodule> submodules);

	Set<SubmoduleRepresentor> toDispatchable(Set<Submodule> submodules);

	Set<SubmoduleRepresentor> toSimplified(Set<Submodule> submodules);

	Set<SubmoduleRepresentor> toComplete(Set<Submodule> submodules);

}
