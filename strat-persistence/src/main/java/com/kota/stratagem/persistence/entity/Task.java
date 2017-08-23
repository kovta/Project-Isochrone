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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.kota.stratagem.persistence.parameter.TaskParameter;
import com.kota.stratagem.persistence.query.TaskQuery;

@Entity
@Table(name = "tasks")
@NamedQueries(value = { //
		@NamedQuery(name = TaskQuery.COUNT_BY_ID, query = "SELECT COUNT(t) FROM Task t WHERE t.id=:" + TaskParameter.ID),
		@NamedQuery(name = TaskQuery.GET_BY_ID, query = "SELECT t FROM Task t WHERE t.id=:" + TaskParameter.ID),
		@NamedQuery(name = TaskQuery.GET_BY_ID_WITH_ASSIGNMENTS, query = "SELECT t FROM Task t LEFT JOIN FETCH t.assignedUsers au LEFT JOIN FETCH t.assignedTeams at WHERE t.id=:"
				+ TaskParameter.ID),
		@NamedQuery(name = TaskQuery.GET_BY_ID_COMPLETE, query = "SELECT t FROM Task t LEFT JOIN FETCH t.assignedUsers au LEFT JOIN FETCH t.assignedTeams at WHERE t.id=:"
				+ TaskParameter.ID),
		@NamedQuery(name = TaskQuery.GET_ALL_TASKS, query = "SELECT t FROM Task t LEFT JOIN FETCH t.dependantTasks LEFT JOIN FETCH t.taskDependencies ORDER BY t.name"),
		@NamedQuery(name = TaskQuery.REMOVE_BY_ID, query = "DELETE FROM Task t WHERE t.id=:" + TaskParameter.ID)
		//
})
@SequenceGenerator(name = "taskGenerator", sequenceName = "tasks_task_id_seq", allocationSize = 1)
@AttributeOverrides({ //
		@AttributeOverride(name = "creationDate", column = @Column(name = "task_creation_date", nullable = false)),
		@AttributeOverride(name = "modificationDate", column = @Column(name = "task_modification_date", nullable = false))
		//
})
@AssociationOverrides({ //
		@AssociationOverride(name = "creator", joinColumns = @JoinColumn(name = "task_creator", referencedColumnName = "user_id", nullable = false)),
		@AssociationOverride(name = "modifier", joinColumns = @JoinColumn(name = "task_modifier", referencedColumnName = "user_id", nullable = false))
		//
})
public class Task extends AbstractMonitoredItem implements Serializable {

	private static final long serialVersionUID = -6357816746519911429L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taskGenerator")
	@Column(name = "task_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@Column(name = "task_name", nullable = false)
	private String name;

	@Column(name = "task_description", nullable = true)
	private String description;

	@Column(name = "task_priority", nullable = false)
	private int priority;

	@Column(name = "task_completion_percentage", nullable = false)
	private double completion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "task_deadline", nullable = true)
	private Date deadline;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = TeamTaskAssignment.class, mappedBy = "task")
	private Set<TeamTaskAssignment> assignedTeams;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUserTaskAssignment.class, mappedBy = "task")
	private Set<AppUserTaskAssignment> assignedUsers;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Impediment.class)
	@JoinTable(name = "project_impediments", joinColumns = @JoinColumn(name = "project_impediment_project_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "project_impediment_impediment_id", nullable = false))
	private Set<Impediment> impediments;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Task.class)
	@JoinTable(name = "task_dependencies", joinColumns = @JoinColumn(name = "dependency_maintainer", nullable = false), inverseJoinColumns = @JoinColumn(name = "dependency_dependent", nullable = false))
	private Set<Task> dependantTasks;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Task.class)
	@JoinTable(name = "task_dependencies", joinColumns = @JoinColumn(name = "dependency_dependent", nullable = false), inverseJoinColumns = @JoinColumn(name = "dependency_maintainer", nullable = false))
	private Set<Task> taskDependencies;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, targetEntity = Objective.class)
	@JoinTable(name = "objective_tasks", joinColumns = @JoinColumn(name = "objective_task_task_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "objective_task_objective_id", nullable = false))
	@NotFound(action = NotFoundAction.IGNORE)
	private Objective objective;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, targetEntity = Project.class)
	@JoinTable(name = "project_tasks", joinColumns = @JoinColumn(name = "project_task_task_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "project_task_project_id", nullable = false))
	@NotFound(action = NotFoundAction.IGNORE)
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, targetEntity = Submodule.class)
	@JoinTable(name = "submodule_tasks", joinColumns = @JoinColumn(name = "submodule_task_task_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "submodule_task_submodule_id", nullable = false))
	@NotFound(action = NotFoundAction.IGNORE)
	private Submodule submodule;

	public Task() {
		this.assignedTeams = new HashSet<>();
		this.assignedUsers = new HashSet<>();
		this.impediments = new HashSet<>();
		this.dependantTasks = new HashSet<>();
		this.taskDependencies = new HashSet<>();
	}

	public Task(Long id, String name, String description, int priority, double completion, Date deadline, Date creationDate, Date modificationDate) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.completion = completion;
		this.deadline = deadline;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
	}

	public Task(String name, String description, int priority, double completion, Date deadline, Date creationDate, Date modificationDate) {
		super();
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.completion = completion;
		this.deadline = deadline;
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

	public double getCompletion() {
		return this.completion;
	}

	public void setCompletion(double completion) {
		this.completion = completion;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Set<TeamTaskAssignment> getAssignedTeams() {
		return this.assignedTeams;
	}

	public void setAssignedTeams(Set<TeamTaskAssignment> assignedTeams) {
		this.assignedTeams = assignedTeams;
	}

	public Set<AppUserTaskAssignment> getAssignedUsers() {
		return this.assignedUsers;
	}

	public void setAssignedUsers(Set<AppUserTaskAssignment> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	public Set<Impediment> getImpediments() {
		return this.impediments;
	}

	public void setImpediments(Set<Impediment> impediments) {
		this.impediments = impediments;
	}

	public Set<Task> getDependantTasks() {
		return this.dependantTasks;
	}

	public void setDependantTasks(Set<Task> dependantTasks) {
		this.dependantTasks = dependantTasks;
	}

	public Set<Task> getTaskDependencies() {
		return this.taskDependencies;
	}

	public void setTaskDependencies(Set<Task> taskDependencies) {
		this.taskDependencies = taskDependencies;
	}

	public Objective getObjective() {
		return this.objective;
	}

	public void setObjective(Objective objective) {
		this.objective = objective;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Submodule getSubmodule() {
		return this.submodule;
	}

	public void setSubmodule(Submodule submodule) {
		this.submodule = submodule;
	}

	@Override
	public String toString() {
		return "Task [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority + ", completion="
				+ this.completion + ", deadline=" + this.deadline + ", creator=" + this.creator + ", creationDate=" + this.creationDate + ", modifier="
				+ this.modifier + ", modificationDate=" + this.modificationDate + ", assignedTeams=" + this.assignedTeams + ", assignedUsers="
				+ this.assignedUsers + ", impediments=" + this.impediments + ", dependantTasks=" + this.dependantTasks + ", taskDependencies="
				+ this.taskDependencies + ", objective=" + this.objective + ", project=" + this.project + "]";
	}

}