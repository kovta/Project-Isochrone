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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.parameter.ObjectiveParameter;
import com.kota.stratagem.persistence.query.ObjectiveQuery;

@Entity
@Table(name = "objectives")
@NamedQueries(value = { //
		@NamedQuery(name = ObjectiveQuery.COUNT_BY_ID, query = "SELECT COUNT(o) FROM Objective o WHERE o.id=:" + ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.GET_BY_ID, query = "SELECT o FROM Objective o WHERE o.id=:" + ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.GET_BY_ID_WITH_MONITORING, query = "SELECT o FROM Objective o LEFT JOIN FETCH o.creator crt LEFT JOIN FETCH o.modifier mod WHERE o.id=:"
				+ ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.GET_BY_ID_WITH_ASSIGNMENTS, query = "SELECT o FROM Objective o LEFT JOIN FETCH o.assignedUsers au LEFT JOIN FETCH o.assignedTeams at WHERE o.id=:"
				+ ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.GET_BY_ID_WITH_TASKS, query = "SELECT o FROM Objective o LEFT JOIN FETCH o.tasks t LEFT JOIN FETCH o.creator WHERE o.id=:"
				+ ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.GET_BY_ID_WITH_PROJECTS, query = "SELECT o FROM Objective o LEFT JOIN FETCH o.projects p WHERE o.id=:"
				+ ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.GET_BY_ID_WITH_PROJECTS_AND_TASKS, query = "SELECT o FROM Objective o LEFT JOIN FETCH o.projects p LEFT JOIN FETCH o.tasks t WHERE o.id=:"
				+ ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.GET_BY_ID_COMPLETE, query = "SELECT o FROM Objective o LEFT JOIN FETCH o.projects p LEFT JOIN FETCH p.submodules psm LEFT JOIN FETCH psm.tasks smt LEFT JOIN FETCH p.tasks pt LEFT JOIN FETCH o.tasks t LEFT JOIN FETCH o.assignedUsers au LEFT JOIN FETCH o.assignedTeams at LEFT JOIN FETCH o.creator LEFT JOIN FETCH o.modifier WHERE o.id=:"
				+ ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.GET_ALL_OBJECTIVES, query = "SELECT o FROM Objective o LEFT JOIN FETCH o.projects p LEFT JOIN FETCH o.tasks t ORDER BY o.name"),
		@NamedQuery(name = ObjectiveQuery.REMOVE_BY_ID, query = "DELETE FROM Objective o WHERE o.id=:" + ObjectiveParameter.ID)
		//
})
@SequenceGenerator(name = "objectiveGenerator", sequenceName = "objectives_objective_id_seq", allocationSize = 1)
@AttributeOverrides({ //
		@AttributeOverride(name = "creationDate", column = @Column(name = "objective_creation_date", nullable = false)),
		@AttributeOverride(name = "modificationDate", column = @Column(name = "objective_modification_date", nullable = false))
		//
})
@AssociationOverrides({ //
		@AssociationOverride(name = "creator", joinColumns = @JoinColumn(name = "objective_creator", referencedColumnName = "user_id", nullable = false)),
		@AssociationOverride(name = "modifier", joinColumns = @JoinColumn(name = "objective_modifier", referencedColumnName = "user_id", nullable = false))
		//
})
public class Objective extends AbstractMonitoredEntity implements Serializable {

	private static final long serialVersionUID = 3624081320738998792L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "objectiveGenerator")
	@Column(name = "objective_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@Column(name = "objective_name", nullable = false)
	private String name;

	@Column(name = "objective_description", nullable = true)
	private String description;

	@Column(name = "objective_priority", nullable = false)
	private int priority;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "objective_status_id", nullable = false)
	private ObjectiveStatus status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "objective_deadline", nullable = true)
	private Date deadline;

	@Column(name = "objective_confidentiality", nullable = false)
	private Boolean confidential;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, targetEntity = Project.class)
	@JoinTable(name = "objective_projects", joinColumns = @JoinColumn(name = "objective_project_objective", nullable = false), inverseJoinColumns = @JoinColumn(name = "objective_project_project", nullable = false))
	private Set<Project> projects;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, targetEntity = Task.class)
	@JoinTable(name = "objective_tasks", joinColumns = @JoinColumn(name = "objective_task_objective_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "objective_task_task_id", nullable = false))
	private Set<Task> tasks;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = TeamObjectiveAssignment.class, mappedBy = "objective")
	private Set<TeamObjectiveAssignment> assignedTeams;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = AppUserObjectiveAssignment.class, mappedBy = "objective")
	private Set<AppUserObjectiveAssignment> assignedUsers;

	public Objective() {
		this.projects = new HashSet<>();
		this.tasks = new HashSet<>();
		this.assignedTeams = new HashSet<>();
		this.assignedUsers = new HashSet<>();
	}

	public Objective(Long id, String name, String description, int priority, ObjectiveStatus status, Date deadline, Boolean confidential, Date creationDate,
			Date modificationDate) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.deadline = deadline;
		this.confidential = confidential;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
	}

	public Objective(String name, String description, int priority, ObjectiveStatus status, Date deadline, Boolean confidential, Date creationDate,
			Date modificationDate) {
		super();
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.deadline = deadline;
		this.confidential = confidential;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
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

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ObjectiveStatus getStatus() {
		return this.status;
	}

	public void setStatus(ObjectiveStatus status) {
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

	public Set<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public Set<TeamObjectiveAssignment> getAssignedTeams() {
		return this.assignedTeams;
	}

	public void setAssignedTeams(Set<TeamObjectiveAssignment> assignedTeams) {
		this.assignedTeams = assignedTeams;
	}

	public Set<AppUserObjectiveAssignment> getAssignedUsers() {
		return this.assignedUsers;
	}

	public void setAssignedUsers(Set<AppUserObjectiveAssignment> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	@Override
	public String toString() {
		return "Objective [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority + ", status="
				+ this.status + ", deadline=" + this.deadline + ", confidential=" + this.confidential + ", creator=" + this.creator + ", creationDate="
				+ this.creationDate + ", modifier=" + this.modifier + ", modificationDate=" + this.modificationDate + ", projects=" + this.projects + ", tasks="
				+ this.tasks + ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers + "]";
	}

	public void addProject(Project project) {
		this.projects.add(project);
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

}
