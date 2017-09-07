package com.kota.stratagem.ejbservice.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.kota.stratagem.ejbserviceclient.domain.RemedyRepresentor;
import com.kota.stratagem.persistence.entity.Remedy;

public class RemedyConverterImpl implements RemedyConverter {

	@Inject
	private ImpedimentConverter impedimentConverter;

	@Inject
	private AppUserConverter appUserConverter;

	@Override
	public RemedyRepresentor to(Remedy remedy) {
		final RemedyRepresentor representor = remedy.getId() != null
				? new RemedyRepresentor(remedy.getId(), remedy.getDescription().trim(), this.impedimentConverter.to(remedy.getImpediment()),
						remedy.getSubmissionDate(), this.appUserConverter.toElementary(remedy.getProvider()),
						this.appUserConverter.toElementary(remedy.getCreator()), remedy.getCreationDate(),
						this.appUserConverter.toElementary(remedy.getModifier()), remedy.getModificationDate())
				: new RemedyRepresentor(remedy.getDescription().trim(), this.impedimentConverter.to(remedy.getImpediment()), remedy.getSubmissionDate(),
						this.appUserConverter.toElementary(remedy.getProvider()), this.appUserConverter.toElementary(remedy.getCreator()),
						remedy.getCreationDate(), this.appUserConverter.toElementary(remedy.getModifier()), remedy.getModificationDate());
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
