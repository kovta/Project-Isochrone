package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectRepresentor implements Serializable {

	private static final long serialVersionUID = -2331431817299985578L;

	private Long id;
	private final String name;
	private final String description;
	private final ProjectStatusRepresentor status;
	private final Date deadline;
	private final Boolean visible;
	private final List<TaskRepresentor> tasks;
	private final List<TeamRepresentor> assignedTeams;
	private final List<AppUserRepresentor> assignedUsers;
	private final List<ImpedimentRepresentor> impediments;
	private final ObjectiveRepresentor objective;

	public ProjectRepresentor() {
		this(null, "", "", ProjectStatusRepresentor.PROPOSED, new Date(), true, null);
	}

	public ProjectRepresentor(Long id, String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean visible,
			ObjectiveRepresentor objective) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.deadline = deadline;
		this.visible = visible;
		this.tasks = new ArrayList<>();
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.impediments = new ArrayList<>();
		this.objective = objective;
	}

	public ProjectRepresentor(String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean visible,
			ObjectiveRepresentor objective) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.deadline = deadline;
		this.visible = visible;
		this.tasks = new ArrayList<>();
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.impediments = new ArrayList<>();
		this.objective = objective;
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

	public ProjectStatusRepresentor getStatus() {
		return this.status;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public List<TaskRepresentor> getTasks() {
		return this.tasks;
	}

	public void addTask(TaskRepresentor task) {
		this.tasks.add(task);
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

	public ObjectiveRepresentor getObjective() {
		return this.objective;
	}

	@Override
	public String toString() {
		return "\nProjectRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", status=" + this.status + ", deadline="
				+ this.deadline + ", visible=" + this.visible + ", \n\ttasks=" + this.tasks + ", \n\tassignedTeams=" + this.assignedTeams
				+ ", \n\tassignedUsers=" + this.assignedUsers + ", \n\timpediments=" + this.impediments + ", \n\tobjective=" + this.objective + "]\t";
	}

}