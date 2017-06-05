package com.kota.stratagem.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kota.stratagem.persistence.parameter.RemedyParameter;
import com.kota.stratagem.persistence.query.RemedyQuery;

@Entity
@Table(name = "remedies")
@NamedQueries(value = { //
		@NamedQuery(name = RemedyQuery.COUNT_BY_ID, query = "SELECT COUNT(r) FROM Remedy r WHERE r.id=:" + RemedyParameter.ID),
		@NamedQuery(name = RemedyQuery.GET_ALL_REMEDIES, query = "SELECT r FROM Remedy r ORDER BY r.submissionDate"),
		@NamedQuery(name = RemedyQuery.GET_BY_ID, query = "SELECT r FROM Remedy r WHERE r.id=:" + RemedyParameter.ID),
		@NamedQuery(name = RemedyQuery.REMOVE_BY_ID, query = "DELETE FROM Remedy r WHERE r.id=:" + RemedyParameter.ID)
		//
})
@SequenceGenerator(name = "remedyGenerator", sequenceName = "remedies_remedy_id_seq", allocationSize = 1)
public class Remedy implements Serializable {

	private static final long serialVersionUID = 3249113805246989076L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remedyGenerator")
	@Column(name = "remedy_id", nullable = false)
	private Long id;

	@Column(name = "remedy_description", nullable = false)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Impediment.class)
	@JoinColumn(name = "remedy_impediment_id", nullable = false)
	private Impediment impediment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "remedy_submission_date", nullable = false)
	private Date submissionDate;

	@Column(name = "remedy_provider", nullable = false)
	private AppUser provider;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "remedy_creator", nullable = false)
	private AppUser creator;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "remedy_creation_date", nullable = false)
	private Date creationDate;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "remedy_modifier", nullable = false)
	private AppUser modifier;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "remedy_modification_date", nullable = false)
	private Date modificationDate;

	public Remedy(Long id, String description, Impediment impediment, Date submissionDate, AppUser provider, AppUser creator, Date creationDate,
			AppUser modifier, Date modificationDate) {
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

	public Remedy(String description, Impediment impediment, Date submissionDate, AppUser provider, AppUser creator, Date creationDate, AppUser modifier,
			Date modificationDate) {
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

	public void setDescription(String description) {
		this.description = description;
	}

	public Impediment getImpediment() {
		return this.impediment;
	}

	public void setImpediment(Impediment impediment) {
		this.impediment = impediment;
	}

	public Date getSubmissionDate() {
		return this.submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public AppUser getProvider() {
		return this.provider;
	}

	public void setProvider(AppUser provider) {
		this.provider = provider;
	}

	public AppUser getCreator() {
		return this.creator;
	}

	public void setCreator(AppUser creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public AppUser getModifier() {
		return this.modifier;
	}

	public void setModifier(AppUser modifier) {
		this.modifier = modifier;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Override
	public String toString() {
		return "Remedy [id=" + this.id + ", description=" + this.description + ", impediment=" + this.impediment + ", submissionDate=" + this.submissionDate
				+ ", provider=" + this.provider + ", creator=" + this.creator + ", creationDate=" + this.creationDate + ", modifier=" + this.modifier
				+ ", modificationDate=" + this.modificationDate + "]";
	}

}
