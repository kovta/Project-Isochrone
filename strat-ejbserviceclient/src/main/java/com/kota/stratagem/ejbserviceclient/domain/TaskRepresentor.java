package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskRepresentor implements Serializable {

	private static final long serialVersionUID = -552279169521037564L;

	private Long id;
	private final String name;
	private final String description;
	private final double completion;
	private final List<TeamRepresentor> assignedTeams;
	private final List<AppUserRepresentor> assignedUsers;
	private final List<ImpedimentRepresentor> impediments;
	private final List<TaskRepresentor> dependantTasks;
	private final List<TaskRepresentor> taskDependencies;
	private ObjectiveRepresentor objective;
	private ProjectRepresentor project;

	public TaskRepresentor() {
		this(null, "", "", 0);
	}

	public TaskRepresentor(Long id, String name, String description, double completion) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.completion = completion;
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.impediments = new ArrayList<>();
		this.dependantTasks = new ArrayList<>();
		this.taskDependencies = new ArrayList<>();
		this.objective = null;
		this.project = null;
	}

	public TaskRepresentor(String name, String description, double completion) {
		this.name = name;
		this.description = description;
		this.completion = completion;
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.impediments = new ArrayList<>();
		this.dependantTasks = new ArrayList<>();
		this.taskDependencies = new ArrayList<>();
		this.objective = null;
		this.project = null;
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

	public String getDescription() {
		return this.description;
	}

	public double getCompletion() {
		return this.completion;
	}

	public List<TeamRepresentor> getAssignedTeams() {
		return this.assignedTeams;
	}

	public void addTeam(TeamRepresentor team) {
		this.assignedTeams.add(team);
	}

	public List<AppUserRepresentor> getAssignedUsers() {
		return this.assignedUsers;
	}

	public void addUser(AppUserRepresentor user) {
		this.assignedUsers.add(user);
	}

	public List<ImpedimentRepresentor> getImpediments() {
		return this.impediments;
	}

	public void addImpediment(ImpedimentRepresentor impediment) {
		this.impediments.add(impediment);
	}

	public List<TaskRepresentor> getDependantTasks() {
		return this.dependantTasks;
	}

	public void addDependantTask(TaskRepresentor task) {
		this.dependantTasks.add(task);
	}

	public List<TaskRepresentor> getTaskDependencies() {
		return this.taskDependencies;
	}

	public void addTaskDependency(TaskRepresentor task) {
		this.taskDependencies.add(task);
	}

	public ObjectiveRepresentor getObjective() {
		return this.objective;
	}

	public void setObjective(ObjectiveRepresentor objective) {
		this.objective = objective;
	}

	public ProjectRepresentor getProject() {
		return this.project;
	}

	public void setProject(ProjectRepresentor project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "\n\tTaskRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", comlpetion=" + this.completion
				+ ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers + ", impediments=" + this.impediments + ", dependantTasks="
				+ this.dependantTasks + ", taskDependencies=" + this.taskDependencies
				// + ", objective=" + this.objective + ", project=" + this.project
				+ "]";
	}

}