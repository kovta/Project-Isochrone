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
@Table(name = "team_project_assignments")
@SequenceGenerator(name = "teamProjectAssignmentGenerator", sequenceName = "team_project_assignments_assignment_id_seq", allocationSize = 1)
public class TeamProjectAssignment extends AbstractTeamAssignment implements Serializable {

	private static final long serialVersionUID = -291842579597781124L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamProjectAssignmentGenerator")
	@Column(name = "assignment_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = Project.class)
	@JoinColumn(name = "assignment_project", referencedColumnName = "project_id", nullable = false)
	private Project project;

	public TeamProjectAssignment() {
		super();
		this.creationDate = new Date();
	}

	public TeamProjectAssignment(AppUser entrustor, Team recipient, Project project, Date creationDate) {
		super();
		this.project = project;
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

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "TeamProjectAssignment [id=" + this.id + ", project=" + this.project + "]";
	}

}
