package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TeamRepresentor implements Serializable {

	private static final long serialVersionUID = -1370141284024070447L;

	private Long id;
	private final String name;
	private final AppUserRepresentor leader;
	private final List<AppUserRepresentor> members;
	private final List<ObjectiveRepresentor> objectives;
	private final List<ProjectRepresentor> projects;
	private final List<TaskRepresentor> tasks;

	public TeamRepresentor() {
		this(null, "", null);
	}

	public TeamRepresentor(Long id, String name, AppUserRepresentor leader) {
		this.id = id;
		this.name = name;
		this.leader = leader;
		this.members = new ArrayList<>();
		this.objectives = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.tasks = new ArrayList<>();
	}

	public TeamRepresentor(String name, AppUserRepresentor leader) {
		this.name = name;
		this.leader = leader;
		this.members = new ArrayList<>();
		this.objectives = new ArrayList<>();
		this.projects = new ArrayList<>();
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

	public List<AppUserRepresentor> getMembers() {
		return this.members;
	}

	public void addMember(AppUserRepresentor user) {
		this.members.add(user);
	}

	public List<ObjectiveRepresentor> getObjectives() {
		return this.objectives;
	}

	public void addObjective(ObjectiveRepresentor objective) {
		this.objectives.add(objective);
	}

	public List<ProjectRepresentor> getProjects() {
		return this.projects;
	}

	public void addProject(ProjectRepresentor project) {
		this.projects.add(project);
	}

	public List<TaskRepresentor> getTasks() {
		return this.tasks;
	}

	public void addTask(TaskRepresentor task) {
		this.tasks.add(task);
	}

	@Override
	public String toString() {
		return "TeamRepresentor [id=" + this.id + ", name=" + this.name + ", leader=" + this.leader + ", members=" + this.members + ", objectives="
				+ this.objectives + ", projects=" + this.projects + ", tasks=" + this.tasks + "]";
	}

}
