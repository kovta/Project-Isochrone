package com.kota.stratagem.persistence.entity;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kota.stratagem.persistence.parameter.TeamParameter;
import com.kota.stratagem.persistence.query.TeamQuery;

@Entity
@Table(name = "teams")
@NamedQueries(value = { //
		@NamedQuery(name = TeamQuery.COUNT_BY_ID, query = "SELECT COUNT(t) FROM Team t WHERE t.id=:" + TeamParameter.ID),
		@NamedQuery(name = TeamQuery.GET_ALL_TEAMS, query = "SELECT t FROM Team t ORDER BY t.name"),
		@NamedQuery(name = TeamQuery.GET_BY_ID, query = "SELECT t FROM Team t WHERE t.id=:" + TeamParameter.ID),
		@NamedQuery(name = TeamQuery.REMOVE_BY_ID, query = "DELETE FROM Team t WHERE t.id=:" + TeamParameter.ID)
		//
})
@SequenceGenerator(name = "teamGenerator", sequenceName = "teams_team_id_seq", allocationSize = 1)
public class Team implements Serializable {

	private static final long serialVersionUID = -3554913763648115162L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamGenerator")
	@Column(name = "team_id", nullable = false)
	private Long id;

	@Column(name = "team_name", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "team_leader", nullable = false)
	private AppUser leader;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinTable(name = "team_members", joinColumns = @JoinColumn(name = "team_member_team_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "team_member_user_id", nullable = false))
	private Set<AppUser> members;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Objective.class)
	@JoinTable(name = "team_objective_assignments", joinColumns = @JoinColumn(name = "assignment_recipient", nullable = false), inverseJoinColumns = @JoinColumn(name = "assignment_objective", nullable = false))
	private Set<Objective> objectives;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Project.class)
	@JoinTable(name = "team_project_assignments", joinColumns = @JoinColumn(name = "assignment_recipient", nullable = false), inverseJoinColumns = @JoinColumn(name = "assignment_project", nullable = false))
	private Set<Project> projects;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Task.class)
	@JoinTable(name = "team_task_assignments", joinColumns = @JoinColumn(name = "assignment_recipient", nullable = false), inverseJoinColumns = @JoinColumn(name = "assignment_task", nullable = false))
	private Set<Task> tasks;

	public Team() {
		this.members = new HashSet<>();
		this.objectives = new HashSet<>();
		this.projects = new HashSet<>();
		this.tasks = new HashSet<>();
	}

	public Team(Long id, String name, AppUser leader, Set<AppUser> members, Set<Objective> objectives, Set<Project> projects, Set<Task> tasks) {
		this.id = id;
		this.name = name;
		this.leader = leader;
		this.members = members;
		this.objectives = objectives;
		this.projects = projects;
		this.tasks = tasks;
	}

	public Team(String name, AppUser leader, Set<AppUser> members, Set<Objective> objectives, Set<Project> projects, Set<Task> tasks) {
		this.name = name;
		this.leader = leader;
		this.members = members;
		this.objectives = objectives;
		this.projects = projects;
		this.tasks = tasks;
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

	public AppUser getLeader() {
		return this.leader;
	}

	public void setLeader(AppUser leader) {
		this.leader = leader;
	}

	public Set<AppUser> getMembers() {
		return this.members;
	}

	public void setMembers(Set<AppUser> members) {
		this.members = members;
	}

	public Set<Objective> getObjectives() {
		return this.objectives;
	}

	public void setObjectives(Set<Objective> objectives) {
		this.objectives = objectives;
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

	@Override
	public String toString() {
		return "Team [id=" + this.id + ", name=" + this.name + ", leader=" + this.leader + ", members=" + this.members + ", objectives=" + this.objectives
				+ ", projects=" + this.projects + ", tasks=" + this.tasks + "]";
	}

}
