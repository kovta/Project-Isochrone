package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.catalog.ProjectStatusRepresentor;

public class ProjectRepresentor extends AbstractTimeConstraintRepresentor implements Serializable {

	private static final long serialVersionUID = -2331431817299985578L;

	private Long id;
	private final String name;
	private final String description;
	private final ProjectStatusRepresentor status;
	private final Date deadline;
	private final Boolean confidential;
	private final List<SubmoduleRepresentor> submodules;
	private final List<TaskRepresentor> tasks;
	private final List<TeamProjectAssignmentRepresentor> assignedTeams;
	private final List<AppUserProjectAssignmentRepresentor> assignedUsers;
	private final List<ImpedimentRepresentor> impediments;
	private final ObjectiveRepresentor objective;

	private double completion;
	private List<SubmoduleRepresentor> overdueSubmodules;
	private List<SubmoduleRepresentor> ongoingSubmodules;
	private List<SubmoduleRepresentor> completedSubmodules;
	private List<TaskRepresentor> overdueTasks;
	private List<TaskRepresentor> ongoingTasks;
	private List<TaskRepresentor> completedTasks;

	public ProjectRepresentor() {
		this(null, "", "", ProjectStatusRepresentor.PROPOSED, new Date(), false, null);
	}

	public ProjectRepresentor(Long id, String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean confidential,
			ObjectiveRepresentor objective) {
		this(name, description, status, deadline, confidential, objective);
		this.id = id;
	}

	public ProjectRepresentor(String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean confidential,
			ObjectiveRepresentor objective) {
		super(deadline != null ? deadline : new Date(), null);
		this.name = name;
		this.description = description;
		this.status = status;
		this.deadline = deadline;
		this.confidential = confidential;
		this.submodules = new ArrayList<>();
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

	public Boolean getConfidential() {
		return this.confidential;
	}

	public List<SubmoduleRepresentor> getSubmodules() {
		return this.submodules;
	}

	public List<TaskRepresentor> getTasks() {
		return this.tasks;
	}

	public List<TeamProjectAssignmentRepresentor> getAssignedTeams() {
		return this.assignedTeams;
	}

	public List<AppUserProjectAssignmentRepresentor> getAssignedUsers() {
		return this.assignedUsers;
	}

	public List<ImpedimentRepresentor> getImpediments() {
		return this.impediments;
	}

	public ObjectiveRepresentor getObjective() {
		return this.objective;
	}

	public Boolean isCompleted() {
		return this.getCompletion() == 100;
	}

	public Boolean isOngoing() {
		return (this.getCompletion() < 100) && (this.getCompletion() > 0);
	}

	public Boolean isUnstarted() {
		return this.getCompletion() == 0;
	}

	public double getCompletion() {
		return this.completion;
	}

	public void setCompletion(double completion) {
		this.completion = completion;
	}

	public List<SubmoduleRepresentor> getOverdueSubmodules() {
		return this.overdueSubmodules;
	}

	public void setOverdueSubmodules(List<SubmoduleRepresentor> overdueSubmodules) {
		this.overdueSubmodules = overdueSubmodules;
	}

	public List<SubmoduleRepresentor> getOngoingSubmodules() {
		return this.ongoingSubmodules;
	}

	public void setOngoingSubmodules(List<SubmoduleRepresentor> ongoingSubmodules) {
		this.ongoingSubmodules = ongoingSubmodules;
	}

	public List<SubmoduleRepresentor> getCompletedSubmodules() {
		return this.completedSubmodules;
	}

	public void setCompletedSubmodules(List<SubmoduleRepresentor> completedSubmodules) {
		this.completedSubmodules = completedSubmodules;
	}

	public List<TaskRepresentor> getOverdueTasks() {
		return this.overdueTasks;
	}

	public void setOverdueTasks(List<TaskRepresentor> overdueTasks) {
		this.overdueTasks = overdueTasks;
	}

	public List<TaskRepresentor> getOngoingTasks() {
		return this.ongoingTasks;
	}

	public void setOngoingTasks(List<TaskRepresentor> ongoingTasks) {
		this.ongoingTasks = ongoingTasks;
	}

	public List<TaskRepresentor> getCompletedTasks() {
		return this.completedTasks;
	}

	public void setCompletedTasks(List<TaskRepresentor> completedTasks) {
		this.completedTasks = completedTasks;
	}

	@Override
	public String toString() {
		return "ProjectRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", status=" + this.status + ", deadline="
				+ this.deadline + ", confidential=" + this.confidential + ", creator=" + this.creator + ", creationDate=" + this.creationDate + ", modifier="
				+ this.modifier + ", modificationDate=" + this.modificationDate + ", submodules=" + this.submodules + ", tasks=" + this.tasks
				+ ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers + ", impediments=" + this.impediments + ", objective="
				+ this.objective + "]";
	}

	@Override
	public String toTextMessage() {
		return "ProjectRepresentor | [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", status=" + this.status + ", deadline="
				+ this.deadline + ", confidential=" + this.confidential + ", creator_id=" + this.creator.getId() + ", creationDate=" + this.creationDate
				+ ", modifier_id=" + this.modifier.getId() + ", objective_id=" + this.objective.getId() + "]";
	}

	public void addSubmodules(SubmoduleRepresentor submodule) {
		this.submodules.add(submodule);
	}

	public void addTask(TaskRepresentor task) {
		this.tasks.add(task);
	}

	public void addTeam(TeamProjectAssignmentRepresentor team) {
		this.assignedTeams.add(team);
	}

	public void addUser(AppUserProjectAssignmentRepresentor user) {
		this.assignedUsers.add(user);
	}

	public void addImpediment(ImpedimentRepresentor impediment) {
		this.impediments.add(impediment);
	}

}