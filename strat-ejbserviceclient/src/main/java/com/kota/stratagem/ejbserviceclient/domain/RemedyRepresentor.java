package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class RemedyRepresentor implements Serializable {

	private static final long serialVersionUID = -1021682612168904034L;

	private Long id;
	private final String description;
	private final ImpedimentRepresentor impediment;
	private final Date submissionDate;
	private final AppUserRepresentor provider;

	public RemedyRepresentor() {
		this(null, "", null, new Date(), null);
	}

	public RemedyRepresentor(Long id, String description, ImpedimentRepresentor impediment, Date submissionDate, AppUserRepresentor provider) {
		this.id = id;
		this.description = description;
		this.impediment = impediment;
		this.submissionDate = submissionDate;
		this.provider = provider;
	}

	public RemedyRepresentor(String description, ImpedimentRepresentor impediment, Date submissionDate, AppUserRepresentor provider) {
		this.description = description;
		this.impediment = impediment;
		this.submissionDate = submissionDate;
		this.provider = provider;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public ImpedimentRepresentor getImpediment() {
		return this.impediment;
	}

	public Date getSubmissionDate() {
		return this.submissionDate;
	}

	public AppUserRepresentor getProvider() {
		return this.provider;
	}

	@Override
	public String toString() {
		return "RemedyRepresentor [id=" + this.id + ", description=" + this.description + ", impediment=" + this.impediment + ", submissionDate="
				+ this.submissionDate + ", provider=" + this.provider + "]";
	}

}
