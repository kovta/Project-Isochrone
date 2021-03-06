package com.kota.stratagem.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.parameter.ProjectParameter;
import com.kota.stratagem.persistence.query.ProjectQuery;

@Entity
@Table(name = "projects")
@NamedQueries(value = { //
		@NamedQuery(name = ProjectQuery.COUNT_BY_ID, query = "SELECT COUNT(p) FROM Project p WHERE p.id=:" + ProjectParameter.ID),
		@NamedQuery(name = ProjectQuery.GET_BY_ID, query = "SELECT p FROM Project p WHERE p.id=:" + ProjectParameter.ID),
		@NamedQuery(name = ProjectQuery.GET_BY_ID_WITH_MONITORING, query = "SELECT p FROM Project p LEFT JOIN FETCH p.creator crt LEFT JOIN FETCH p.modifier mod WHERE p.id=:"
				+ ProjectParameter.ID),
		@NamedQuery(name = ProjectQuery.GET_BY_ID_WITH_ASSIGNMENTS, query = "SELECT p FROM Project p LEFT JOIN FETCH p.assignedUsers au LEFT JOIN FETCH p.assignedTeams at WHERE p.id=:"
				+ ProjectParameter.ID),
		@NamedQuery(name = ProjectQuery.GET_BY_ID_WITH_TASKS, query = "SELECT p FROM Project p LEFT JOIN FETCH p.tasks t LEFT JOIN FETCH p.creator WHERE p.id=:"
				+ ProjectParameter.ID),
		@NamedQuery(name = ProjectQuery.GET_BY_ID_WITH_SUBMODULES, query = "SELECT p FROM Project p LEFT JOIN FETCH p.submodules sm LEFT JOIN FETCH p.creator WHERE p.id=:"
				+ ProjectParameter.ID),
		@NamedQuery(name = ProjectQuery.GET_BY_ID_WITH_SUBMODULES_AND_TASKS, query = "SELECT p FROM Project p LEFT JOIN FETCH p.submodules sm LEFT JOIN FETCH sm.tasks smt LEFT JOIN FETCH p.tasks t WHERE p.id=:"
				+ ProjectParameter.ID),
		@NamedQuery(name = ProjectQuery.GET_BY_ID_COMPLETE, query = "SELECT p FROM Project p LEFT JOIN FETCH p.submodules sm LEFT JOIN FETCH sm.tasks smt LEFT JOIN FETCH p.tasks t LEFT JOIN FETCH p.assignedUsers au LEFT JOIN FETCH p.assignedTeams at LEFT JOIN FETCH p.creator LEFT JOIN FETCH p.modifier WHERE p.id=:"
				+ ProjectParameter.ID),
		@NamedQuery(name = ProjectQuery.GET_ALL_BY_STATUS, query = "SELECT p FROM Project p LEFT JOIN FETCH p.submodules sm LEFT JOIN FETCH sm.tasks smt LEFT JOIN FETCH p.tasks t WHERE p.status=:"
				+ ProjectParameter.STATUS + " ORDER BY p.name"),
		@NamedQuery(name = ProjectQuery.GET_ALL_PROJECTS, query = "SELECT p FROM Project p LEFT JOIN FETCH p.submodules sm LEFT JOIN FETCH sm.tasks smt LEFT JOIN FETCH p.tasks t ORDER BY p.name"),
		@NamedQuery(name = ProjectQuery.REMOVE_BY_ID, query = "DELETE FROM Project p WHERE p.id=:" + ProjectParameter.ID)
		//
})
@SequenceGenerator(name = "projectGenerator", sequenceName = "projects_project_id_seq", allocationSize = 1)
@AttributeOverrides({ //
		@AttributeOverride(name = "creationDate", column = @Column(name = "project_creation_date", nullable = false)),
		@AttributeOverride(name = "modificationDate", column = @Column(name = "project_modification_date", nullable = false))
		//
})
@AssociationOverrides({ //
		@AssociationOverride(name = "creator", joinColumns = @JoinColumn(name = "project_creator", referencedColumnName = "user_id", nullable = false)),
		@AssociationOverride(name = "modifier", joinColumns = @JoinColumn(name = "project_modifier", referencedColumnName = "user_id", nullable = false))
		//
})
public class Project extends AbstractMonitoredEntity implements Serializable {

	private static final long serialVersionUID = -6784523546510114561L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projectGenerator")
	@Column(name = "project_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@Column(name = "project_name", nullable = false)
	private String name;

	@Column(name = "project_description", nullable = true)
	private String description;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "project_status_id", nullable = false)
	private ProjectStatus status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "project_deadline", nullable = true)
	private Date deadline;

	@Column(name = "project_confidentiality", nullable = false)
	private Boolean confidential;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, targetEntity = Submodule.class)
	@JoinTable(name = "project_submodules", joinColumns = @JoinColumn(name = "project_submodule_project", nullable = false), inverseJoinColumns = @JoinColumn(name = "project_submodule_submodule", nullable = false))
	private Set<Submodule> submodules;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, targetEntity = Task.class)
	@JoinTable(name = "project_tasks", joinColumns = @JoinColumn(name = "project_task_project_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "project_task_task_id", nullable = false))
	private Set<Task> tasks;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = TeamProjectAssignment.class, mappedBy = "project")
	private Set<TeamProjectAssignment> assignedTeams;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUserProjectAssignment.class, mappedBy = "project")
	private Set<AppUserProjectAssignment> assignedUsers;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Impediment.class)
	@JoinTable(name = "project_impediments", joinColumns = @JoinColumn(name = "project_impediment_project_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "project_impediment_impediment_id", nullable = false))
	private Set<Impediment> impediments;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = Objective.class)
	@JoinTable(name = "objective_projects", joinColumns = @JoinColumn(name = "objective_project_project", nullable = false), inverseJoinColumns = @JoinColumn(name = "objective_project_objective", nullable = false))
	private Objective objective;

	public Project() {
		this.tasks = new HashSet<>();
		this.submodules = new HashSet<>();
		this.assignedTeams = new HashSet<>();
		this.assignedUsers = new HashSet<>();
		this.impediments = new HashSet<>();
	}

	public Project(Long id, String name, String description, ProjectStatus status, Date deadline, Boolean confidential, Date creationDate,
			Date modificationDate, Objective objective) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.deadline = deadline;
		this.confidential = confidential;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.objective = objective;
	}

	public Project(String name, String description, ProjectStatus status, Date deadline, Boolean confidential, Date creationDate, Date modificationDate,
			Objective objective) {
		super();
		this.name = name;
		this.description = description;
		this.status = status;
		this.deadline = deadline;
		this.confidential = confidential;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.objective = objective;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProjectStatus getStatus() {
		return this.status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Boolean getConfidential() {
		return this.confidential;
	}

	public void setConfidential(Boolean confidential) {
		this.confidential = confidential;
	}

	public Set<Submodule> getSubmodules() {
		return this.submodules;
	}

	public void setSubmodules(Set<Submodule> submodules) {
		this.submodules = submodules;
	}

	public Set<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public Set<TeamProjectAssignment> getAssignedTeams() {
		return this.assignedTeams;
	}

	public void setAssignedTeams(Set<TeamProjectAssignment> assignedTeams) {
		this.assignedTeams = assignedTeams;
	}

	public Set<AppUserProjectAssignment> getAssignedUsers() {
		return this.assignedUsers;
	}

	public void setAssignedUsers(Set<AppUserProjectAssignment> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	public Set<Impediment> getImpediments() {
		return this.impediments;
	}

	public void setImpediments(Set<Impediment> impediments) {
		this.impediments = impediments;
	}

	public Objective getObjective() {
		return this.objective;
	}

	public void setObjective(Objective objective) {
		this.objective = objective;
	}

	@Override
	public String toString() {
		return "Project [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", status=" + this.status + ", deadline="
				+ this.deadline + ", confidential=" + this.confidential + ", creator=" + this.creator + ", creationDate=" + this.creationDate + ", modifier="
				+ this.modifier + ", modificationDate=" + this.modificationDate + ", submodules=" + this.submodules + ", tasks=" + this.tasks
				+ ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers + ", impediments=" + this.impediments + ", objective="
				+ this.objective + "]";
	}

	public void addSubmodule(Submodule submodule) {
		this.getSubmodules().add(submodule);
	}

	public void addTask(Task task) {
		this.getTasks().add(task);
	}

}