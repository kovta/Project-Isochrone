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
	private final AppUserRepresentor creator;
	private final Date creationDate;
	private final AppUserRepresentor modifier;
	private final Date modificationDate;

	public RemedyRepresentor() {
		this(null, "", null, new Date(), null, null, new Date(), null, new Date());
	}

	public RemedyRepresentor(Long id, String description, ImpedimentRepresentor impediment, Date submissionDate, AppUserRepresentor provider,
			AppUserRepresentor creator, Date creationDate, AppUserRepresentor modifier, Date modificationDate) {
		super();
		this.id = id;
		this.description = description;
		this.impediment = impediment;
		this.submissionDate = submissionDate;
		this.provider = provider;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
	}

	public RemedyRepresentor(String description, ImpedimentRepresentor impediment, Date submissionDate, AppUserRepresentor provider, AppUserRepresentor creator,
			Date creationDate, AppUserRepresentor modifier, Date modificationDate) {
		super();
		this.description = description;
		this.impediment = impediment;
		this.submissionDate = submissionDate;
		this.provider = provider;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
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

	public AppUserRepresentor getCreator() {
		return this.creator;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public AppUserRepresentor getModifier() {
		return this.modifier;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	@Override
	public String toString() {
		return "\nRemedyRepresentor [id=" + this.id + ", description=" + this.description + ", impediment=" + this.impediment + ", submissionDate="
				+ this.submissionDate + ", provider=" + this.provider + ", creator=" + this.creator + ", creationDate=" + this.creationDate + ", modifier="
				+ this.modifier + ", modificationDate=" + this.modificationDate + "]\n";
	}

}
