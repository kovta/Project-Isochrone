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
		@NamedQuery(name = TeamQuery.GET_BY_ID, query = "SELECT t FROM Team t WHERE t.id=:" + TeamParameter.ID),
		@NamedQuery(name = TeamQuery.GET_BY_ID_WITH_LEADER_AND_MEMBERS, query = "SELECT t FROM Team t LEFT JOIN FETCH t.leader l LEFT JOIN FETCH t.members tm WHERE t.id=:"
				+ TeamParameter.ID),
		@NamedQuery(name = TeamQuery.GET_BY_ID_COMPLETE, query = "SELECT t FROM Team t LEFT JOIN FETCH t.leader l LEFT JOIN FETCH t.members tm LEFT JOIN FETCH t.objectives LEFT JOIN FETCH t.projects LEFT JOIN FETCH t.submodules LEFT JOIN FETCH t.tasks LEFT JOIN FETCH t.creator LEFT JOIN FETCH t.modifier WHERE t.id=:"
				+ TeamParameter.ID),
		@NamedQuery(name = TeamQuery.GET_ALL_TEAMS, query = "SELECT t FROM Team t LEFT JOIN FETCH t.leader l LEFT JOIN FETCH t.members tm LEFT JOIN FETCH t.objectives LEFT JOIN FETCH t.projects LEFT JOIN FETCH t.submodules LEFT JOIN FETCH t.tasks ORDER BY t.name"),
		@NamedQuery(name = TeamQuery.REMOVE_BY_ID, query = "DELETE FROM Team t WHERE t.id=:" + TeamParameter.ID)
		//
})
@SequenceGenerator(name = "teamGenerator", sequenceName = "teams_team_id_seq", allocationSize = 1)
@AttributeOverrides({ //
		@AttributeOverride(name = "creationDate", column = @Column(name = "team_creation_date", nullable = false)),
		@AttributeOverride(name = "modificationDate", column = @Column(name = "team_modification_date", nullable = false))
		//
})
@AssociationOverrides({ //
		@AssociationOverride(name = "creator", joinColumns = @JoinColumn(name = "team_creator", referencedColumnName = "user_id", nullable = false)),
		@AssociationOverride(name = "modifier", joinColumns = @JoinColumn(name = "team_modifier", referencedColumnName = "user_id", nullable = false))
		//
})
public class Team extends AbstractMonitoredEntity implements Serializable {

	private static final long serialVersionUID = -3554913763648115162L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamGenerator")
	@Column(name = "team_id", nullable = false)
	private Long id;

	@Column(name = "team_name", nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "team_leader", referencedColumnName = "user_id", nullable = false)
	private AppUser leader;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinTable(name = "team_members", joinColumns = @JoinColumn(name = "team_member_team_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "team_member_user_id", nullable = false))
	private Set<AppUser> members;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = TeamObjectiveAssignment.class, mappedBy = "recipient")
	private Set<TeamObjectiveAssignment> objectives;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = TeamProjectAssignment.class, mappedBy = "recipient")
	private Set<TeamProjectAssignment> projects;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = TeamSubmoduleAssignment.class, mappedBy = "recipient")
	private Set<TeamSubmoduleAssignment> submodules;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = TeamTaskAssignment.class, mappedBy = "recipient")
	private Set<TeamTaskAssignment> tasks;

	public Team() {
		this.members = new HashSet<>();
		this.objectives = new HashSet<>();
		this.projects = new HashSet<>();
		this.tasks = new HashSet<>();
	}

	public Team(Long id, String name, AppUser leader, Date creationDate, Date modificationDate) {
		this(name, leader, creationDate, modificationDate);
		this.id = id;
	}

	public Team(String name, AppUser leader, Date creationDate, Date modificationDate) {
		super();
		this.name = name;
		this.leader = leader;
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

	public AppUser getLeader() {
		return this.leader;
	}

	public void setLeader(AppUser leader) {
		this.leader = leader;
	}

	@Override
	public AppUser getCreator() {
		return this.creator;
	}

	@Override
	public void setCreator(AppUser creator) {
		this.creator = creator;
	}

	@Override
	public Date getCreationDate() {
		return this.creationDate;
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public AppUser getModifier() {
		return this.modifier;
	}

	@Override
	public void setModifier(AppUser modifier) {
		this.modifier = modifier;
	}

	@Override
	public Date getModificationDate() {
		return this.modificationDate;
	}

	@Override
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Set<AppUser> getMembers() {
		return this.members;
	}

	public void setMembers(Set<AppUser> members) {
		this.members = members;
	}

	public Set<TeamObjectiveAssignment> getObjectives() {
		return this.objectives;
	}

	public void setObjectives(Set<TeamObjectiveAssignment> objectives) {
		this.objectives = objectives;
	}

	public Set<TeamProjectAssignment> getProjects() {
		return this.projects;
	}

	public void setProjects(Set<TeamProjectAssignment> projects) {
		this.projects = projects;
	}

	public Set<TeamSubmoduleAssignment> getSubmodules() {
		return this.submodules;
	}

	public void setSubmodules(Set<TeamSubmoduleAssignment> submodules) {
		this.submodules = submodules;
	}

	public Set<TeamTaskAssignment> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<TeamTaskAssignment> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "Team [id=" + this.id + ", name=" + this.name + ", leader=" + this.leader + ", creator=" + this.creator + ", creationDate=" + this.creationDate
				+ ", modifier=" + this.modifier + ", modificationDate=" + this.modificationDate + ", members=" + this.members + ", objectives="
				+ this.objectives + ", projects=" + this.projects + ", tasks=" + this.tasks + "]";
	}

}
