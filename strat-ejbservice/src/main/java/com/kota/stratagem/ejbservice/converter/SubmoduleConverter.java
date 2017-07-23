package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.persistence.entity.Submodule;

@Local
public interface SubmoduleConverter {

	SubmoduleRepresentor to(Submodule submodule);

	SubmoduleRepresentor toElementary(Submodule submodule);

	Set<SubmoduleRepresentor> to(Set<Submodule> submodules);

}
