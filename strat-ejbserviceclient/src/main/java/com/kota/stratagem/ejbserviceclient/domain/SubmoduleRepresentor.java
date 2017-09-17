package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubmoduleRepresentor extends AbstractTimeConstraintRepresentor implements Serializable {

	private static final long serialVersionUID = -7646277745869655229L;

	private Long id;
	private final String name;
	private final String description;
	private final Date deadline;
	private final List<TaskRepresentor> tasks;
	private final List<TeamSubmoduleAssignmentRepresentor> assignedTeams;
	private final List<AppUserSubmoduleAssignmentRepresentor> assignedUsers;
	private final ProjectRepresentor project;

	private double completion;
	private Double durationSum;
	private Double completedDurationSum;
	private Boolean estimated;
	private Date expectedCompletionDate;
	private Date estimatedCompletionDate;
	private Double targetDeviation;
	private Double earlyFinishEstimation;
	private List<TaskRepresentor> overdueTasks;
	private List<TaskRepresentor> ongoingTasks;
	private List<TaskRepresentor> completedTasks;

	public SubmoduleRepresentor() {
		this(null, "", "", new Date(), null);
	}

	public SubmoduleRepresentor(Long id, String name, String description, Date deadline, ProjectRepresentor project) {
		this(name, description, deadline, project);
		this.id = id;
	}

	public SubmoduleRepresentor(String name, String description, Date deadline, ProjectRepresentor project) {
		super(deadline != null ? deadline : new Date(), null);
		this.name = name;
		this.description = description;
		this.deadline = deadline;
		this.tasks = new ArrayList<>();
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.project = project;
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

	public Date getDeadline() {
		return this.deadline;
	}

	public List<TaskRepresentor> getTasks() {
		return this.tasks;
	}

	public List<TeamSubmoduleAssignmentRepresentor> getAssignedTeams() {
		return this.assignedTeams;
	}

	public List<AppUserSubmoduleAssignmentRepresentor> getAssignedUsers() {
		return this.assignedUsers;
	}

	public ProjectRepresentor getProject() {
		return this.project;
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

	public Boolean isDeadlineProvided() {
		return this.getDeadline() != null;
	}

	public double getCompletion() {
		return this.completion;
	}

	public void setCompletion(double completion) {
		this.completion = completion;
	}

	public Double getDurationSum() {
		return this.durationSum;
	}

	public void setDurationSum(Double durationSum) {
		this.durationSum = durationSum;
	}

	public Double getCompletedDurationSum() {
		return this.completedDurationSum;
	}

	public void setCompletedDurationSum(Double completedDurationSum) {
		this.completedDurationSum = completedDurationSum;
	}

	public Boolean getEstimated() {
		return this.estimated;
	}

	public void setEstimated(Boolean estimated) {
		this.estimated = estimated;
	}

	public Date getExpectedCompletionDate() {
		return this.expectedCompletionDate;
	}

	public void setExpectedCompletionDate(Date expectedCompletionDate) {
		this.expectedCompletionDate = expectedCompletionDate;
	}

	public Date getEstimatedCompletionDate() {
		return this.estimatedCompletionDate;
	}

	public void setEstimatedCompletionDate(Date estimatedCompletionDate) {
		this.estimatedCompletionDate = estimatedCompletionDate;
	}

	public Double getTargetDeviation() {
		return this.targetDeviation;
	}

	public void setTargetDeviation(Double targetDeviation) {
		this.targetDeviation = targetDeviation;
	}

	public Double getEarlyFinishEstimation() {
		return this.earlyFinishEstimation;
	}

	public void setEarlyFinishEstimation(Double earlyFinishEstimation) {
		this.earlyFinishEstimation = earlyFinishEstimation;
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
		return "\nSubmoduleRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", deadline=" + this.deadline
				+ ", creator=" + this.creator + ", creationDate=" + this.creationDate + ", modifier=" + this.modifier + ", modificationDate="
				+ this.modificationDate + ", tasks=" + this.tasks + ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers
				+ ", project=" + this.project + "]";
	}

	@Override
	public String toTextMessage() {
		return "SubmoduleRepresentor | [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", deadline=" + this.deadline
				+ ", creator_id=" + this.creator.getId() + ", creationDate=" + this.creationDate + ", modifier_id=" + this.modifier.getId() + ", project_id="
				+ this.project.getId() + "]";
	}

	public void addTask(TaskRepresentor task) {
		this.tasks.add(task);
	}

	public void addTeam(TeamSubmoduleAssignmentRepresentor team) {
		this.assignedTeams.add(team);
	}

	public void addUser(AppUserSubmoduleAssignmentRepresentor user) {
		this.assignedUsers.add(user);
	}

}
