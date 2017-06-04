package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamRepresentor implements Serializable {

	private static final long serialVersionUID = -1370141284024070447L;

	private Long id;
	private final String name;
	private final AppUserRepresentor leader;
	private final AppUserRepresentor creator;
	private final Date creationDate;
	private final AppUserRepresentor modifier;
	private final Date modificationDate;
	private final List<AppUserRepresentor> members;
	private final List<ObjectiveRepresentor> objectives;
	private final List<ProjectRepresentor> projects;
	private final List<TaskRepresentor> tasks;

	public TeamRepresentor() {
		this(null, "", null, null, new Date(), null, new Date());
	}

	public TeamRepresentor(Long id, String name, AppUserRepresentor leader, AppUserRepresentor creator, Date creationDate, AppUserRepresentor modifier,
			Date modificationDate) {
		super();
		this.id = id;
		this.name = name;
		this.leader = leader;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
		this.members = new ArrayList<>();
		this.objectives = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.tasks = new ArrayList<>();
	}

	public TeamRepresentor(String name, AppUserRepresentor leader, AppUserRepresentor creator, Date creationDate, AppUserRepresentor modifier,
			Date modificationDate) {
		super();
		this.name = name;
		this.leader = leader;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
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

	public AppUserRepresentor getCreator() {
		return this.creator;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public AppUserRepresentor getModifier() {
		return this.modifier;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	public List<AppUserRepresentor> getMembers() {
		return this.members;
	}

	public List<ObjectiveRepresentor> getObjectives() {
		return this.objectives;
	}

	public List<ProjectRepresentor> getProjects() {
		return this.projects;
	}

	public List<TaskRepresentor> getTasks() {
		return this.tasks;
	}

	@Override
	public String toString() {
		return "\nTeamRepresentor [id=" + this.id + ", name=" + this.name + ", leader=" + this.leader + ", creator=" + this.creator + ", creationDate="
				+ this.creationDate + ", modifier=" + this.modifier + ", modificationDate=" + this.modificationDate + ", members=" + this.members
				+ ", objectives=" + this.objectives + ", projects=" + this.projects + ", tasks=" + this.tasks + "]\n";
	}

	public void addMember(AppUserRepresentor user) {
		this.members.add(user);
	}

	public void addObjective(ObjectiveRepresentor objective) {
		this.objectives.add(objective);
	}

	public void addProject(ProjectRepresentor project) {
		this.projects.add(project);
	}

	public void addTask(TaskRepresentor task) {
		this.tasks.add(task);
	}

}
