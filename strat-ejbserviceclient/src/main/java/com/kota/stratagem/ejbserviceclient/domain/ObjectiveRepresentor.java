package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectiveRepresentor implements Serializable {

	private static final long serialVersionUID = -4038127838789105749L;

	private Long id;
	private String name;
	private String description;
	private int priority;
	private ObjectiveStatusRepresentor status;
	private List<ProjectRepresentor> projects;
	private List<TaskRepresentor> tasks;
	private List<TeamRepresentor> assignedTeams;
	private List<AppUserRepresentor> assignedUsers;

	public ObjectiveRepresentor() {
		this(null, "", "", 10, ObjectiveStatusRepresentor.PLANNED);
	}

	public ObjectiveRepresentor(Long id, String name, String description, int priority, ObjectiveStatusRepresentor status) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.projects = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
	}

	public ObjectiveRepresentor(String name, String description, int priority, ObjectiveStatusRepresentor status) {
		super();
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.projects = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
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

	public ObjectiveStatusRepresentor getStatus() {
		return this.status;
	}

	public void setStatus(ObjectiveStatusRepresentor status) {
		this.status = status;
	}

	public List<ProjectRepresentor> getProjects() {
		return this.projects;
	}

	public void setProjects(List<ProjectRepresentor> projects) {
		this.projects = projects;
	}

	public List<TaskRepresentor> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<TaskRepresentor> tasks) {
		this.tasks = tasks;
	}

	public List<TeamRepresentor> getAssignedTeams() {
		return this.assignedTeams;
	}

	public void setAssignedTeams(List<TeamRepresentor> assignedTeams) {
		this.assignedTeams = assignedTeams;
	}

	public List<AppUserRepresentor> getAssignedUsers() {
		return this.assignedUsers;
	}

	public void setAssignedUsers(List<AppUserRepresentor> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	public void addProject(ProjectRepresentor project) {
		this.projects.add(project);
	}

	public void addTask(TaskRepresentor task) {
		this.tasks.add(task);
	}

	public void addTeam(TeamRepresentor team) {
		this.assignedTeams.add(team);
	}

	public void addUser(AppUserRepresentor user) {
		this.assignedUsers.add(user);
	}

	@Override
	public String toString() {
		return "ObjectiveRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority + ", status="
				+ this.status + ", projects=" + this.projects + ", tasks=" + this.tasks + ", assignedTeams=" + this.assignedTeams + ", assignedUsers="
				+ this.assignedUsers + "]\n";
	}

}
