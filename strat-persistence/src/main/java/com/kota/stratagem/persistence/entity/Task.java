package com.kota.stratagem.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
		@NamedQuery(name = TaskQuery.GET_ALL_TASKS, query = "SELECT t FROM Task t LEFT JOIN FETCH t.dependantTasks LEFT JOIN FETCH t.taskDependencies ORDER BY t.name"),
		@NamedQuery(name = TaskQuery.GET_BY_ID, query = "SELECT t FROM Task t WHERE t.id=:" + TaskParameter.ID),
		@NamedQuery(name = TaskQuery.REMOVE_BY_ID, query = "DELETE FROM Task t WHERE t.id=:" + TaskParameter.ID)
		//
})
public class Task implements Serializable {

	private static final long serialVersionUID = -6357816746519911429L;

	@Id
	@SequenceGenerator(name = "taskGenerator", sequenceName = "task_task_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taskGenerator")
	@Column(name = "task_id", nullable = false)
	private Long id;

	@Column(name = "task_name", nullable = false)
	private String name;

	@Column(name = "task_description", nullable = true)
	private String description;

	@Column(name = "task_completion_percentage", nullable = false)
	private double completion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "task_deadline", nullable = true)
	private Date deadline;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "task_creator", nullable = false)
	private AppUser creator;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "task_creation_date", nullable = false)
	private Date creationDate;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "task_modifier", nullable = false)
	private AppUser modifier;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "task_modification_date", nullable = false)
	private Date modificationDate;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Team.class)
	@JoinTable(name = "team_task_assignments", joinColumns = @JoinColumn(name = "assignment_task", nullable = false), inverseJoinColumns = @JoinColumn(name = "assignment_recipient", nullable = false))
	private Set<Team> assignedTeams;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinTable(name = "user_task_assignments", joinColumns = @JoinColumn(name = "assignment_task", nullable = false), inverseJoinColumns = @JoinColumn(name = "assignment_recipient", nullable = false))
	private Set<AppUser> assignedUsers;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Impediment.class)
	@JoinTable(name = "project_impediments", joinColumns = @JoinColumn(name = "project_impediment_project_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "project_impediment_impediment_id", nullable = false))
	private Set<Impediment> impediments;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Task.class)
	@JoinTable(name = "task_dependencies", joinColumns = @JoinColumn(name = "dependency_maintainer", nullable = false), inverseJoinColumns = @JoinColumn(name = "dependency_dependent", nullable = false))
	private Set<Task> dependantTasks;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Task.class)
	@JoinTable(name = "task_dependencies", joinColumns = @JoinColumn(name = "dependency_dependent", nullable = false), inverseJoinColumns = @JoinColumn(name = "dependency_maintainer", nullable = false))
	private Set<Task> taskDependencies;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Objective.class)
	@JoinTable(name = "objective_tasks", joinColumns = @JoinColumn(name = "objective_task_task_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "objective_task_objective_id", nullable = false))
	@NotFound(action = NotFoundAction.IGNORE)
	private Objective objective;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Project.class)
	@JoinTable(name = "project_tasks", joinColumns = @JoinColumn(name = "project_task_task_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "project_task_project_id", nullable = false))
	@NotFound(action = NotFoundAction.IGNORE)
	private Project project;

	public Task() {
		this.assignedTeams = new HashSet<>();
		this.assignedUsers = new HashSet<>();
		this.impediments = new HashSet<>();
		this.dependantTasks = new HashSet<>();
		this.taskDependencies = new HashSet<>();
	}

	public Task(Long id, String name, String description, double completion, Date deadline, AppUser creator, Date creationDate, AppUser modifier,
			Date modificationDate, Set<Team> assignedTeams, Set<AppUser> assignedUsers, Set<Impediment> impediments, Set<Task> dependantTasks,
			Set<Task> taskDependencies, Objective objective, Project project) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.completion = completion;
		this.deadline = deadline;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
		this.assignedTeams = assignedTeams;
		this.assignedUsers = assignedUsers;
		this.impediments = impediments;
		this.dependantTasks = dependantTasks;
		this.taskDependencies = taskDependencies;
		this.objective = objective;
		this.project = project;
	}

	public Task(String name, String description, double completion, Date deadline, AppUser creator, Date creationDate, AppUser modifier, Date modificationDate,
			Set<Team> assignedTeams, Set<AppUser> assignedUsers, Set<Impediment> impediments, Set<Task> dependantTasks, Set<Task> taskDependencies,
			Objective objective, Project project) {
		super();
		this.name = name;
		this.description = description;
		this.completion = completion;
		this.deadline = deadline;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
		this.assignedTeams = assignedTeams;
		this.assignedUsers = assignedUsers;
		this.impediments = impediments;
		this.dependantTasks = dependantTasks;
		this.taskDependencies = taskDependencies;
		this.objective = objective;
		this.project = project;
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

	public Set<Team> getAssignedTeams() {
		return this.assignedTeams;
	}

	public void setAssignedTeams(Set<Team> assignedTeams) {
		this.assignedTeams = assignedTeams;
	}

	public Set<AppUser> getAssignedUsers() {
		return this.assignedUsers;
	}

	public void setAssignedUsers(Set<AppUser> assignedUsers) {
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

	@Override
	public String toString() {
		return "Task [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", completion=" + this.completion + ", deadline="
				+ this.deadline + ", creator=" + this.creator + ", creationDate=" + this.creationDate + ", modifier=" + this.modifier + ", modificationDate="
				+ this.modificationDate + ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers + ", impediments="
				+ this.impediments + ", dependantTasks=" + this.dependantTasks + ", taskDependencies=" + this.taskDependencies + ", objective=" + this.objective
				+ ", project=" + this.project + "]";
	}

}