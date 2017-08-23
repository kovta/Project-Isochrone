package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor;

public class ObjectiveRepresentor extends AbstractTimeConstraintRepresentor implements Serializable {

	private static final long serialVersionUID = -4038127838789105749L;

	private Long id;
	private final String name;
	private String description;
	private int priority;
	private ObjectiveStatusRepresentor status;
	private final Date deadline;
	private final Boolean confidential;
	private final AppUserRepresentor creator;
	private final Date creationDate;
	private final AppUserRepresentor modifier;
	private final Date modificationDate;
	private List<ProjectRepresentor> projects;
	private List<TaskRepresentor> tasks;
	private List<TeamObjectiveAssignmentRepresentor> assignedTeams;
	private List<AppUserObjectiveAssignmentRepresentor> assignedUsers;

	public ObjectiveRepresentor() {
		this(null, "", "", 10, ObjectiveStatusRepresentor.PLANNED, null, false, null, new Date(), null, new Date());
	}

	public ObjectiveRepresentor(Long id, String name, String description, int priority, ObjectiveStatusRepresentor status, Date deadline, Boolean confidential,
			AppUserRepresentor creator, Date creationDate, AppUserRepresentor modifier, Date modificationDate) {
		super(deadline != null ? deadline : new Date());
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.deadline = deadline;
		this.confidential = confidential;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
		this.projects = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
	}

	public ObjectiveRepresentor(String name, String description, int priority, ObjectiveStatusRepresentor status, Date deadline, Boolean confidential,
			AppUserRepresentor creator, Date creationDate, AppUserRepresentor modifier, Date modificationDate) {
		super(deadline != null ? deadline : new Date());
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.deadline = deadline;
		this.confidential = confidential;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
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

	public Date getDeadline() {
		return this.deadline;
	}

	public Boolean getConfidential() {
		return this.confidential;
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

	public List<TeamObjectiveAssignmentRepresentor> getAssignedTeams() {
		return this.assignedTeams;
	}

	public void setAssignedTeams(List<TeamObjectiveAssignmentRepresentor> assignedTeams) {
		this.assignedTeams = assignedTeams;
	}

	public List<AppUserObjectiveAssignmentRepresentor> getAssignedUsers() {
		return this.assignedUsers;
	}

	public void setAssignedUsers(List<AppUserObjectiveAssignmentRepresentor> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	@Override
	public String toString() {
		return "\nObjectiveRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority
				+ ", status=" + this.status + ", deadline=" + this.deadline + ", confidential=" + this.confidential + ", creator=" + this.creator
				+ ", creationDate=" + this.creationDate + ", modifier=" + this.modifier + ", modificationDate=" + this.modificationDate + ", projects="
				+ this.projects + ", tasks=" + this.tasks + ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers + "]\n";
	}

	public String toTextMessage() {
		return "ObjectiveRepresentor | [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority
				+ ", status=" + this.status + ", deadline=" + this.deadline + ", confidential=" + this.confidential + ", creator_id=" + this.creator.getId()
				+ ", creationDate=" + this.creationDate + ", modifier_id=" + this.modifier.getId() + "]";
	}

	public void addProject(ProjectRepresentor project) {
		this.projects.add(project);
	}

	public void addTask(TaskRepresentor task) {
		this.tasks.add(task);
	}

	public void addTeamAssignment(TeamObjectiveAssignmentRepresentor team) {
		this.assignedTeams.add(team);
	}

	public void addUserAssignment(AppUserObjectiveAssignmentRepresentor user) {
		this.assignedUsers.add(user);
	}

}
