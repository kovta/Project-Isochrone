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

	public ProjectRepresentor(Long id, String name, String description, ProjectStatusRepresentor status, Date deadline, Boolean confidentiality,
			ObjectiveRepresentor objective) {
		super(deadline != null ? deadline : new Date(), id);
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.deadline = deadline;
		this.confidential = confidentiality;
		this.submodules = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.impediments = new ArrayList<>();
		this.objective = objective;
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

	public double getCompletion() {
		int progressSum = 0, taskCount = 0;
		for (final TaskRepresentor task : this.getTasks()) {
			progressSum += task.getCompletion();
			taskCount++;
		}
		for (final SubmoduleRepresentor submodule : this.getSubmodules()) {
			for (final TaskRepresentor submoduleTask : submodule.getTasks()) {
				progressSum += submoduleTask.getCompletion();
				taskCount++;
			}
		}
		return taskCount != 0 ? progressSum / taskCount : 0;
	}

	public List<SubmoduleRepresentor> getOverdueSubmodules() {
		if (this.overdueSubmodules == null) {
			this.overdueSubmodules = new ArrayList<SubmoduleRepresentor>();
		} else {
			this.overdueSubmodules.clear();
		}
		for (final SubmoduleRepresentor representor : this.getSubmodules()) {
			if ((representor.getUrgencyLevel() == 3) && (representor.getCompletion() != 100)) {
				this.overdueSubmodules.add(representor);
			}
		}
		return this.overdueSubmodules;
	}

	public List<SubmoduleRepresentor> getOngoingSubmodules() {
		if (this.ongoingSubmodules == null) {
			this.ongoingSubmodules = new ArrayList<SubmoduleRepresentor>();
		} else {
			this.ongoingSubmodules.clear();
		}
		for (final SubmoduleRepresentor representor : this.getSubmodules()) {
			if ((representor.getUrgencyLevel() != 3) && (representor.getCompletion() != 100)) {
				this.ongoingSubmodules.add(representor);
			}
		}
		return this.ongoingSubmodules;
	}

	public List<SubmoduleRepresentor> getCompletedSubmodules() {
		if (this.completedSubmodules == null) {
			this.completedSubmodules = new ArrayList<SubmoduleRepresentor>();
		} else {
			this.completedSubmodules.clear();
		}
		for (final SubmoduleRepresentor representor : this.getSubmodules()) {
			if (representor.getCompletion() == 100) {
				this.completedSubmodules.add(representor);
			}
		}
		return this.completedSubmodules;
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