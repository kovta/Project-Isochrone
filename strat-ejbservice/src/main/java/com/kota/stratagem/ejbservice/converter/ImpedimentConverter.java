package com.kota.stratagem.ejbservice.converter;

import java.util.List;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.ImpedimentRepresentor;
import com.kota.stratagem.persistence.entity.Impediment;

@Local
public interface ImpedimentConverter {

	ImpedimentRepresentor to(Impediment impediment);

	List<ImpedimentRepresentor> to(List<Impediment> impediments);

}
