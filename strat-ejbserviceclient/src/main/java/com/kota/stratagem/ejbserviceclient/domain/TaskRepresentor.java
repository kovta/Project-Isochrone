package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepresentor implements Serializable {

	private static final long serialVersionUID = -552279169521037564L;

	private Long id;
	private final String name;
	private final String description;
	private final double completion;
	private final Date deadline;
	private final AppUserRepresentor creator;
	private final Date creationDate;
	private final AppUserRepresentor modifier;
	private final Date modificationDate;
	private final List<TeamRepresentor> assignedTeams;
	private final List<AppUserRepresentor> assignedUsers;
	private final List<ImpedimentRepresentor> impediments;
	private final List<TaskRepresentor> dependantTasks;
	private final List<TaskRepresentor> taskDependencies;
	private ObjectiveRepresentor objective;
	private ProjectRepresentor project;

	public TaskRepresentor() {
		this(null, "", "", 0, new Date(), null, new Date(), null, new Date());
	}

	// Projects and Objectives removed from constructor due to structure ambiguity
	public TaskRepresentor(Long id, String name, String description, double completion, Date deadline, AppUserRepresentor creator, Date creationDate,
			AppUserRepresentor modifier, Date modificationDate) {
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
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.impediments = new ArrayList<>();
		this.dependantTasks = new ArrayList<>();
		this.taskDependencies = new ArrayList<>();
		this.objective = null;
		this.project = null;
	}

	public TaskRepresentor(String name, String description, double completion, Date deadline, AppUserRepresentor creator, Date creationDate,
			AppUserRepresentor modifier, Date modificationDate) {
		super();
		this.name = name;
		this.description = description;
		this.completion = completion;
		this.deadline = deadline;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
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

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public double getCompletion() {
		return this.completion;
	}

	public Date getDeadline() {
		return this.deadline;
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

	public List<TeamRepresentor> getAssignedTeams() {
		return this.assignedTeams;
	}

	public List<AppUserRepresentor> getAssignedUsers() {
		return this.assignedUsers;
	}

	public List<ImpedimentRepresentor> getImpediments() {
		return this.impediments;
	}

	public List<TaskRepresentor> getDependantTasks() {
		return this.dependantTasks;
	}

	public List<TaskRepresentor> getTaskDependencies() {
		return this.taskDependencies;
	}

	@Override
	public String toString() {
		return "\nTaskRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", completion=" + this.completion
				+ ", deadline=" + this.deadline + ", creator=" + this.creator + ", creationDate=" + this.creationDate + ", modifier=" + this.modifier
				+ ", modificationDate=" + this.modificationDate + ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers
				+ ", impediments=" + this.impediments + ", dependantTasks=" + this.dependantTasks + ", taskDependencies=" + this.taskDependencies
				+ ", objective=" + this.objective + ", project=" + this.project + "]\n";
	}

}