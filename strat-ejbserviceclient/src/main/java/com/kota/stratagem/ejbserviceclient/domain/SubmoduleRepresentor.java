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
	private final List<TeamRepresentor> assignedTeams;
	private final List<AppUserRepresentor> assignedUsers;
	private final ProjectRepresentor project;
	private final double completion;

	public SubmoduleRepresentor() {
		this(null, "", "", new Date(), null, new Date(), null, new Date(), null);
	}

	public SubmoduleRepresentor(Long id, String name, String description, Date deadline, AppUserRepresentor creator, Date creationDate,
			AppUserRepresentor modifier, Date modificationDate, ProjectRepresentor project) {
		super(deadline != null ? deadline : new Date());
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
		int progressSum = 0;
		for (final TaskRepresentor task : this.getTasks()) {
			progressSum += task.getCompletion();
		}
		this.completion = this.getTasks().size() != 0 ? progressSum / this.getTasks().size() : 0;
	}

	public SubmoduleRepresentor(String name, String description, Date deadline, AppUserRepresentor creator, Date creationDate, AppUserRepresentor modifier,
			Date modificationDate, ProjectRepresentor project) {
		super(deadline != null ? deadline : new Date());
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
		int progressSum = 0;
		for (final TaskRepresentor task : this.getTasks()) {
			progressSum += task.getCompletion();
		}
		this.completion = this.getTasks().size() != 0 ? progressSum / this.getTasks().size() : 0;
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

	public List<TeamRepresentor> getAssignedTeams() {
		return this.assignedTeams;
	}

	public List<AppUserRepresentor> getAssignedUsers() {
		return this.assignedUsers;
	}

	public ProjectRepresentor getProject() {
		return this.project;
	}

	public double getCompletion() {
		return this.completion;
	}

	@Override
	public String toString() {
		return "SubmoduleRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", deadline=" + this.deadline
				+ ", creator=" + this.creator + ", creationDate=" + this.creationDate + ", modifier=" + this.modifier + ", modificationDate="
				+ this.modificationDate + ", tasks=" + this.tasks + ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers
				+ ", project=" + this.project + "]";
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

}
