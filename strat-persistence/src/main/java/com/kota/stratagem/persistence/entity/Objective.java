package com.kota.stratagem.persistence.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.parameter.ObjectiveParameter;
import com.kota.stratagem.persistence.query.ObjectiveQuery;

@Entity
@Table(name = "objectives")
@NamedQueries(value = { //
		@NamedQuery(name = ObjectiveQuery.COUNT_BY_ID, query = "SELECT COUNT(o) FROM Objective o WHERE o.id=:" + ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.GET_ALL_OBJECTIVES, query = "SELECT o FROM Objective o LEFT JOIN FETCH o.projects p LEFT JOIN FETCH o.tasks t ORDER BY o.name"),
		@NamedQuery(name = ObjectiveQuery.GET_BY_ID, query = "SELECT o FROM Objective o WHERE o.id=:" + ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.GET_BY_ID_WITH_PROJECTS_AND_TASKS, query = "SELECT o FROM Objective o LEFT JOIN FETCH o.projects p LEFT JOIN FETCH o.tasks t WHERE o.id=:"
				+ ObjectiveParameter.ID),
		@NamedQuery(name = ObjectiveQuery.REMOVE_BY_ID, query = "DELETE FROM Objective o WHERE o.id=:" + ObjectiveParameter.ID)
		//
})
@SequenceGenerator(name = "objectiveGenerator", sequenceName = "objectives_objective_id_seq", allocationSize = 1)
public class Objective implements Serializable {

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

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Project.class)
	@JoinTable(name = "objective_projects", joinColumns = @JoinColumn(name = "objective_project_objective", nullable = false), inverseJoinColumns = @JoinColumn(name = "objective_project_project", nullable = false))
	private Set<Project> projects;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Task.class)
	@JoinTable(name = "objective_tasks", joinColumns = @JoinColumn(name = "objective_task_objective_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "objective_task_task_id", nullable = false))
	private Set<Task> tasks;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Team.class)
	@JoinTable(name = "team_objective_assignments", joinColumns = @JoinColumn(name = "assignment_objective", nullable = false), inverseJoinColumns = @JoinColumn(name = "assignment_recipient", nullable = false))
	private Set<Team> assignedTeams;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinTable(name = "user_objective_assignments", joinColumns = @JoinColumn(name = "assignment_objective", nullable = false), inverseJoinColumns = @JoinColumn(name = "assignment_recipient", nullable = false))
	private Set<AppUser> assignedUsers;

	public Objective() {
		this.projects = new HashSet<>();
		this.tasks = new HashSet<>();
		this.assignedTeams = new HashSet<>();
		this.assignedUsers = new HashSet<>();
	}

	public Objective(Long id, String name, String description, int priority, ObjectiveStatus status, Set<Project> projects, Set<Task> tasks,
			Set<Team> assignedTeams, Set<AppUser> assignedUsers) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.projects = projects;
		this.tasks = tasks;
		this.assignedTeams = assignedTeams;
		this.assignedUsers = assignedUsers;
	}

	public Objective(String name, String description, int priority, ObjectiveStatus status, Set<Project> projects, Set<Task> tasks, Set<Team> assignedTeams,
			Set<AppUser> assignedUsers) {
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.projects = projects;
		this.tasks = tasks;
		this.assignedTeams = assignedTeams;
		this.assignedUsers = assignedUsers;
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

	@Override
	public String toString() {
		return "Objective [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority + ", status="
				+ this.status + "]";
	}

	/*
	 * @Override public String toString() { return "Objective [id=" + id + ", name=" + name + ", description=" +
	 * description + ", priority=" + priority + ", status=" + status + ", projects=" + projects + ", tasks=" + tasks +
	 * ", assignedTeams=" + assignedTeams + ", assignedUsers=" + assignedUsers + "]"; }
	 */

}
