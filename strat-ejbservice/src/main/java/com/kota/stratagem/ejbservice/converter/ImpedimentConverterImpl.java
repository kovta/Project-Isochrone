package com.kota.stratagem.ejbservice.converter;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.ImpedimentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ImpedimentStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.PriorityRepresentor;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Remedy;

@Stateless
public class ImpedimentConverterImpl implements ImpedimentConverter {

	@EJB
	private AppUserConverter appUserConverter;

	@EJB
	private RemedyConverter remedyConverter;

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private TaskConverter taskConverter;

	@Override
	public ImpedimentRepresentor to(Impediment impediment) {
		final ImpedimentRepresentor representor = this.toElementary(impediment);
		// Elementary separation required for conversion loop integrity
		if (impediment.getRemedies() != null) {
			for (final Remedy remedy : impediment.getRemedies()) {
				representor.addRemedy(this.remedyConverter.to(remedy));
			}
		}
		return representor;
	}

	@Override
	public ImpedimentRepresentor toElementary(Impediment impediment) {
		final PriorityRepresentor priority = PriorityRepresentor.valueOf(impediment.getPriority().toString());
		final ImpedimentStatusRepresentor status = ImpedimentStatusRepresentor.valueOf(impediment.getStatus().toString());
		final ImpedimentRepresentor representor = impediment.getId() != null
				? new ImpedimentRepresentor(impediment.getId(), impediment.getName(), impediment.getDescription(), priority, status, impediment.getReportDate(),
						this.appUserConverter.toElementary(impediment.getReporter()),
						impediment.getProcessor() != null ? this.appUserConverter.toElementary(impediment.getProcessor()) : null,
						this.appUserConverter.toElementary(impediment.getCreator()), impediment.getCreationDate(),
						this.appUserConverter.toElementary(impediment.getModifier()), impediment.getModificationDate())
				: new ImpedimentRepresentor(impediment.getName(), impediment.getDescription(), priority, status, impediment.getReportDate(),
						this.appUserConverter.toElementary(impediment.getReporter()),
						impediment.getProcessor() != null ? this.appUserConverter.toElementary(impediment.getProcessor()) : null,
						this.appUserConverter.toElementary(impediment.getCreator()), impediment.getCreationDate(),
						this.appUserConverter.toElementary(impediment.getModifier()), impediment.getModificationDate());
		if (impediment.getProject() != null) {
			representor.setProject(this.projectConverter.toElementary(impediment.getProject()));
		}
		if (impediment.getTask() != null) {
			representor.setTask(this.taskConverter.toElementary(impediment.getTask()));
		}
		return representor;
	}

	@Override
	public List<ImpedimentRepresentor> to(List<Impediment> impediments) {
		final List<ImpedimentRepresentor> representors = new ArrayList<>();
		for (final Impediment impediment : impediments) {
			representors.add(this.to(impediment));
		}
		return representors;
	}

}
