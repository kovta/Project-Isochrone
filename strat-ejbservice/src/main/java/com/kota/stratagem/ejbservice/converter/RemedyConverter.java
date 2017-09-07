package com.kota.stratagem.ejbservice.converter;

import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.RemedyRepresentor;
import com.kota.stratagem.persistence.entity.Remedy;

public interface RemedyConverter {

	RemedyRepresentor to(Remedy remedy);

	List<RemedyRepresentor> to(List<Remedy> remedies);

}
