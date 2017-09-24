package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamRepresentor extends AbstractMonitoredRepresentor implements Serializable {

	private static final long serialVersionUID = -1370141284024070447L;

	private Long id;
	private final String name;
	private final AppUserRepresentor leader;
	private final List<AppUserRepresentor> members;
	private final List<TeamObjectiveAssignmentRepresentor> objectives;
	private final List<TeamProjectAssignmentRepresentor> projects;
	private final List<TeamSubmoduleAssignmentRepresentor> submodules;
	private final List<TeamTaskAssignmentRepresentor> tasks;

	public TeamRepresentor() {
		this(null, "", null);
	}

	public TeamRepresentor(Long id, String name, AppUserRepresentor leader) {
		this(name, leader);
		this.id = id;
	}

	public TeamRepresentor(String name, AppUserRepresentor leader) {
		super(null);
		this.name = name;
		this.leader = leader;
		this.members = new ArrayList<>();
		this.objectives = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.submodules = new ArrayList<>();
		this.tasks = new ArrayList<>();
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

	public AppUserRepresentor getLeader() {
		return this.leader;
	}

	@Override
	public AppUserRepresentor getCreator() {
		return this.creator;
	}

	@Override
	public Date getCreationDate() {
		return this.creationDate;
	}

	@Override
	public AppUserRepresentor getModifier() {
		return this.modifier;
	}

	@Override
	public Date getModificationDate() {
		return this.modificationDate;
	}

	public List<AppUserRepresentor> getMembers() {
		return this.members;
	}

	public List<TeamObjectiveAssignmentRepresentor> getObjectives() {
		return this.objectives;
	}

	public List<TeamProjectAssignmentRepresentor> getProjects() {
		return this.projects;
	}

	public List<TeamSubmoduleAssignmentRepresentor> getSubmodules() {
		return this.submodules;
	}

	public List<TeamTaskAssignmentRepresentor> getTasks() {
		return this.tasks;
	}

	@Override
	public String toString() {
		return "TeamRepresentor [id=" + this.id + ", name=" + this.name + ", leader=" + this.leader + ", members=" + this.members + ", objectives="
				+ this.objectives + ", projects=" + this.projects + ", submodules=" + this.submodules + ", tasks=" + this.tasks + ", creator=" + this.creator
				+ ", creationDate=" + this.creationDate + ", modifier=" + this.modifier + ", modificationDate=" + this.modificationDate + "]";
	}

	public void addMember(AppUserRepresentor user) {
		this.members.add(user);
	}

	public void addObjectiveAssignment(TeamObjectiveAssignmentRepresentor objective) {
		this.objectives.add(objective);
	}

	public void addProjectAssignment(TeamProjectAssignmentRepresentor project) {
		this.projects.add(project);
	}

	public void addSubmoduleAssignment(TeamSubmoduleAssignmentRepresentor submodule) {
		this.submodules.add(submodule);
	}

	public void addTaskAssignment(TeamTaskAssignmentRepresentor task) {
		this.tasks.add(task);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final TeamRepresentor other = (TeamRepresentor) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
