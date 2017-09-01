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
	private final AppUserRepresentor creator;
	private final Date creationDate;
	private final AppUserRepresentor modifier;
	private final Date modificationDate;
	private final List<TaskRepresentor> tasks;
	private final List<TeamSubmoduleAssignmentRepresentor> assignedTeams;
	private final List<AppUserSubmoduleAssignmentRepresentor> assignedUsers;
	private final ProjectRepresentor project;
	private double completion;
	private List<TaskRepresentor> overdueTasks;
	private List<TaskRepresentor> ongoingTasks;
	private List<TaskRepresentor> completedtasks;

	public SubmoduleRepresentor() {
		this(null, "", "", new Date(), null, new Date(), null, new Date(), null);
	}

	public SubmoduleRepresentor(Long id, String name, String description, Date deadline, AppUserRepresentor creator, Date creationDate,
			AppUserRepresentor modifier, Date modificationDate, ProjectRepresentor project) {
		super(deadline != null ? deadline : new Date(), id);
		this.id = id;
		this.name = name;
		this.description = description;
		this.deadline = deadline;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
		this.tasks = new ArrayList<>();
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.project = project;
	}

	public SubmoduleRepresentor(String name, String description, Date deadline, AppUserRepresentor creator, Date creationDate, AppUserRepresentor modifier,
			Date modificationDate, ProjectRepresentor project) {
		super(deadline != null ? deadline : new Date(), null);
		this.name = name;
		this.description = description;
		this.deadline = deadline;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
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

	public double getCompletion() {
		int progressSum = 0;
		for (final TaskRepresentor task : this.getTasks()) {
			progressSum += task.getCompletion();
		}
		return this.getTasks().size() != 0 ? progressSum / this.getTasks().size() : 0;
	}

	@Override
	public String toString() {
		return "\nSubmoduleRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", deadline=" + this.deadline
				+ ", creator=" + this.creator + ", creationDate=" + this.creationDate + ", modifier=" + this.modifier + ", modificationDate="
				+ this.modificationDate + ", tasks=" + this.tasks + ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers
				+ ", project=" + this.project + "]";
	}

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
