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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "team_submodule_assignments")
@SequenceGenerator(name = "teamSubmoduleAssignmentGenerator", sequenceName = "team_submodule_assignments_assignment_id_seq", allocationSize = 1)
public class TeamSubmoduleAssignment extends AbstractTeamAssignment implements Serializable {

	private static final long serialVersionUID = -236212267967798240L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamSubmoduleAssignmentGenerator")
	@Column(name = "assignment_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = Submodule.class)
	@JoinColumn(name = "assignment_submodule", referencedColumnName = "submodule_id", nullable = false)
	private Submodule submodule;

	public TeamSubmoduleAssignment() {
		super();
		this.creationDate = new Date();
	}

	public TeamSubmoduleAssignment(AppUser entrustor, Team recipient, Submodule submodule, Date creationDate) {
		super();
		this.submodule = submodule;
		this.recipient = recipient;
		this.entrustor = entrustor;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Submodule getSubmodule() {
		return this.submodule;
	}

	public void setSubmodule(Submodule submodule) {
		this.submodule = submodule;
	}

	@Override
	public String toString() {
		return "TeamSubmoduleAssignment [id=" + this.id + ", submodule=" + this.submodule + "]";
	}

}