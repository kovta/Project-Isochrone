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
	private double completion;
	private List<ProjectRepresentor> overdueProjects;
	private List<ProjectRepresentor> ongoingProjects;
	private List<ProjectRepresentor> completedProjects;
	private List<TaskRepresentor> overdueTasks;
	private List<TaskRepresentor> ongoingTasks;
	private List<TaskRepresentor> completedTasks;

	public ObjectiveRepresentor() {
		this(null, "", "", 10, ObjectiveStatusRepresentor.PLANNED, null, false, null, new Date(), null, new Date());
	}

	public ObjectiveRepresentor(Long id, String name, String description, int priority, ObjectiveStatusRepresentor status, Date deadline, Boolean confidential,
			AppUserRepresentor creator, Date creationDate, AppUserRepresentor modifier, Date modificationDate) {
		super(deadline != null ? deadline : new Date(), id);
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
		super(deadline != null ? deadline : new Date(), null);
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

	public double getCompletion() {
		int progressSum = 0, taskCount = 0;
		for (final TaskRepresentor task : this.getTasks()) {
			progressSum += task.getCompletion();
			taskCount++;
		}
		for (final ProjectRepresentor project : this.getProjects()) {
			for (final TaskRepresentor projectTask : project.getTasks()) {
				progressSum += projectTask.getCompletion();
				taskCount++;
			}
			for (final SubmoduleRepresentor projectSubmodule : project.getSubmodules()) {
				for (final TaskRepresentor submoduleTask : projectSubmodule.getTasks()) {
					progressSum += submoduleTask.getCompletion();
					taskCount++;
				}
			}
		}
		return taskCount != 0 ? progressSum / taskCount : 0;
	}

	public List<ProjectRepresentor> getOverdueProjects() {
		if (this.overdueProjects == null) {
			this.overdueProjects = new ArrayList<ProjectRepresentor>();
		} else {
			this.overdueProjects.clear();
		}
		for (final ProjectRepresentor representor : this.getProjects()) {
			if ((representor.getUrgencyLevel() == 3) && (representor.getCompletion() != 100)) {
				this.overdueProjects.add(representor);
			}
		}
		return this.overdueProjects;
	}

	public List<ProjectRepresentor> getOngoingProjects() {
		if (this.ongoingProjects == null) {
			this.ongoingProjects = new ArrayList<ProjectRepresentor>();
		} else {
			this.ongoingProjects.clear();
		}
		for (final ProjectRepresentor representor : this.getProjects()) {
			if ((representor.getUrgencyLevel() != 3) && (representor.getCompletion() != 100)) {
				this.ongoingProjects.add(representor);
			}
		}
		return this.ongoingProjects;
	}

	public List<ProjectRepresentor> getCompletedProjects() {
		if (this.completedProjects == null) {
			this.completedProjects = new ArrayList<ProjectRepresentor>();
		} else {
			this.completedProjects.clear();
		}
		for (final ProjectRepresentor representor : this.getProjects()) {
			if (representor.getCompletion() == 100) {
				this.completedProjects.add(representor);
			}
		}
		return this.completedProjects;
	}

	public List<TaskRepresentor> getOverdueTasks() {
		if (this.overdueTasks == null) {
			this.overdueTasks = new ArrayList<TaskRepresentor>();
		} else {
			this.overdueTasks.clear();
		}
		for (final TaskRepresentor representor : this.getTasks()) {
			if ((representor.getUrgencyLevel() == 3) && (representor.getCompletion() != 100)) {
				this.overdueTasks.add(representor);
			}
		}
		return this.overdueTasks;
	}

	public List<TaskRepresentor> getOngoingTasks() {
		if (this.ongoingTasks == null) {
			this.ongoingTasks = new ArrayList<TaskRepresentor>();
		} else {
			this.ongoingTasks.clear();
		}
		for (final TaskRepresentor representor : this.getTasks()) {
			if ((representor.getUrgencyLevel() != 3) && (representor.getCompletion() != 100)) {
				this.ongoingTasks.add(representor);
			}
		}
		return this.ongoingTasks;
	}

	public List<TaskRepresentor> getCompletedTasks() {
		if (this.completedTasks == null) {
			this.completedTasks = new ArrayList<TaskRepresentor>();
		} else {
			this.completedTasks.clear();
		}
		for (final TaskRepresentor representor : this.getTasks()) {
			if (representor.getCompletion() == 100) {
				this.completedTasks.add(representor);
			}
		}
		return this.completedTasks;
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
