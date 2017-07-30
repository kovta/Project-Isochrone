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

import com.kota.stratagem.persistence.parameter.SubmoduleParameter;
import com.kota.stratagem.persistence.query.SubmoduleQuery;

@Entity
@Table(name = "submodules")
@NamedQueries(value = { //
		@NamedQuery(name = SubmoduleQuery.COUNT_BY_ID, query = "SELECT COUNT(sm) FROM Submodule sm WHERE sm.id=:" + SubmoduleParameter.ID),
		@NamedQuery(name = SubmoduleQuery.GET_BY_ID, query = "SELECT sm FROM Submodule sm WHERE sm.id=:" + SubmoduleParameter.ID),
		@NamedQuery(name = SubmoduleQuery.GET_BY_ID_WITH_TASKS, query = "SELECT sm FROM Submodule sm LEFT JOIN FETCH sm.tasks t WHERE sm.id=:"
				+ SubmoduleParameter.ID),
		@NamedQuery(name = SubmoduleQuery.GET_ALL_SUBMODULES, query = "SELECT sm FROM Submodule sm LEFT JOIN FETCH sm.tasks t ORDER BY sm.name"),
		@NamedQuery(name = SubmoduleQuery.REMOVE_BY_ID, query = "DELETE FROM Submodule sm WHERE sm.id=:" + SubmoduleParameter.ID)
		//
})
@SequenceGenerator(name = "submoduleGenerator", sequenceName = "submodules_submodule_id_seq", allocationSize = 1)
public class Submodule implements Serializable {

	private static final long serialVersionUID = -1940935516451348184L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "submoduleGenerator")
	@Column(name = "submodule_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@Column(name = "submodule_name", nullable = false)
	private String name;

	@Column(name = "submodule_description", nullable = true)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submodule_deadline", nullable = true)
	private Date deadline;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "submodule_creator", referencedColumnName = "user_id", nullable = false)
	private AppUser creator;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submodule_creation_date", nullable = false)
	private Date creationDate;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "submodule_modifier", referencedColumnName = "user_id", nullable = false)
	private AppUser modifier;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submodule_modification_date", nullable = false)
	private Date modificationDate;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, targetEntity = Task.class)
	@JoinTable(name = "submodule_tasks", joinColumns = @JoinColumn(name = "submodule_task_submodule_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "submodule_task_task_id", nullable = false))
	private Set<Task> tasks;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Team.class)
	@JoinTable(name = "team_submodule_assignments", joinColumns = @JoinColumn(name = "assignment_submodule", nullable = false), inverseJoinColumns = @JoinColumn(name = "assignment_recipient", nullable = false))
	private Set<Team> assignedTeams;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinTable(name = "user_submodule_assignments", joinColumns = @JoinColumn(name = "assignment_submodule", nullable = false), inverseJoinColumns = @JoinColumn(name = "assignment_recipient", nullable = false))
	private Set<AppUser> assignedUsers;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = Project.class)
	@JoinTable(name = "project_submodules", joinColumns = @JoinColumn(name = "project_submodule_submodule", nullable = false), inverseJoinColumns = @JoinColumn(name = "project_submodule_project", nullable = false))
	@NotFound(action = NotFoundAction.IGNORE)
	private Project project;

	public Submodule() {
		this.tasks = new HashSet<>();
		this.assignedTeams = new HashSet<>();
		this.assignedUsers = new HashSet<>();
	}

	public Submodule(Long id, String name, String description, Date deadline, Date creationDate, Date modificationDate, Set<Task> tasks,
			Set<Team> assignedTeams, Set<AppUser> assignedUsers, Project project) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.deadline = deadline;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.tasks = tasks;
		this.assignedTeams = assignedTeams;
		this.assignedUsers = assignedUsers;
		this.project = project;
	}

	public Submodule(String name, String description, Date deadline, Date creationDate, Date modificationDate, Set<Task> tasks, Set<Team> assignedTeams,
			Set<AppUser> assignedUsers, Project project) {
		super();
		this.name = name;
		this.description = description;
		this.deadline = deadline;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.tasks = tasks;
		this.assignedTeams = assignedTeams;
		this.assignedUsers = assignedUsers;
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

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Submodule [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", deadline=" + this.deadline + ", creator="
				+ this.creator + ", creationDate=" + this.creationDate + ", modifier=" + this.modifier + ", modificationDate=" + this.modificationDate
				+ ", tasks=" + this.tasks + ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers + ", project=" + this.project
				+ "]";
	}

	public void addTask(Task task) {
		this.getTasks().add(task);
	}

}
