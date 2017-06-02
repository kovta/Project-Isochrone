package com.kota.stratagem.ejbservice.converter;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.RemedyRepresentor;
import com.kota.stratagem.persistence.entity.Remedy;

@Stateless
public class RemedyConverterImpl implements RemedyConverter {

	@EJB
	private ImpedimentConverter impedimentConverter;

	@EJB
	private AppUserConverter appUserConverter;

	@Override
	public RemedyRepresentor to(Remedy remedy) {
		final RemedyRepresentor representor = remedy.getId() != null
				? new RemedyRepresentor(remedy.getId(), remedy.getDescription(), this.impedimentConverter.to(remedy.getImpediment()),
						remedy.getSubmissionDate(), this.appUserConverter.to(remedy.getProvider()))
				: new RemedyRepresentor(remedy.getDescription(), this.impedimentConverter.to(remedy.getImpediment()), remedy.getSubmissionDate(),
						this.appUserConverter.to(remedy.getProvider()));
		return representor;
	}

	@Override
	public List<RemedyRepresentor> to(List<Remedy> remedies) {
		final List<RemedyRepresentor> representors = new ArrayList<>();
		for (final Remedy remedy : remedies) {
			representors.add(this.to(remedy));
		}
		return representors;
	}

}
