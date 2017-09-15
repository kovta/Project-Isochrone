package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepresentor extends AbstractTimeConstraintRepresentor implements Serializable, Comparable<TaskRepresentor> {

	private static final long serialVersionUID = -552279169521037564L;

	private Long id;
	private final String name;
	private final String description;
	private final int priority;
	private final double completion;
	private final Date deadline;
	private Double duration;
	private Double pessimistic;
	private Double realistic;
	private Double optimistic;
	private final Boolean admittance;
	private final List<TeamTaskAssignmentRepresentor> assignedTeams;
	private final List<AppUserTaskAssignmentRepresentor> assignedUsers;
	private final List<ImpedimentRepresentor> impediments;
	private final List<TaskRepresentor> dependantTasks;
	private final List<TaskRepresentor> taskDependencies;
	private ObjectiveRepresentor objective;
	private ProjectRepresentor project;
	private SubmoduleRepresentor submodule;
	private List<List<TaskRepresentor>> dependantChain;
	private List<List<TaskRepresentor>> dependencyChain;
	private int dependantCount;
	private int dependencyCount;

	public TaskRepresentor() {
		this(null, "", "", 5, 0, new Date(), null, false);
	}

	public TaskRepresentor(Long id, String name, String description, int priority, double completion, Date deadline, Double duration, Boolean admittance) {
		super(deadline != null ? deadline : new Date(), id);
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.completion = completion;
		this.deadline = deadline;
		this.duration = duration;
		this.admittance = admittance;
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.impediments = new ArrayList<>();
		this.dependantTasks = new ArrayList<>();
		this.taskDependencies = new ArrayList<>();
		this.dependantChain = new ArrayList<>();
		this.dependencyChain = new ArrayList<>();
	}

	public TaskRepresentor(String name, String description, int priority, double completion, Date deadline, Double duration, Boolean admittance) {
		super(deadline != null ? deadline : new Date(), null);
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.completion = completion;
		this.deadline = deadline;
		this.duration = duration;
		this.admittance = admittance;
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.impediments = new ArrayList<>();
		this.dependantTasks = new ArrayList<>();
		this.taskDependencies = new ArrayList<>();
		this.dependantChain = new ArrayList<>();
		this.dependencyChain = new ArrayList<>();
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

	public int getPriority() {
		return this.priority;
	}

	public Boolean isCompleted() {
		return this.completion == 100;
	}

	public Boolean isOngoing() {
		return (this.completion < 100) && (this.completion > 0);
	}

	public Boolean isUnstarted() {
		return this.completion == 0;
	}

	public double getCompletion() {
		return this.completion;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public Boolean getAdmittance() {
		return this.admittance;
	}

	public List<TeamTaskAssignmentRepresentor> getAssignedTeams() {
		return this.assignedTeams;
	}

	public List<AppUserTaskAssignmentRepresentor> getAssignedUsers() {
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

	public SubmoduleRepresentor getSubmodule() {
		return this.submodule;
	}

	public void setSubmodule(SubmoduleRepresentor submodule) {
		this.submodule = submodule;
	}

	public Boolean isDurationProvided() {
		return (this.duration != null);
	}

	public Boolean isEstimated() {
		return ((this.pessimistic != null) && (this.realistic != null) && (this.optimistic != null));
	}

	public Double getDuration() {
		return this.duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Double getPessimistic() {
		return this.pessimistic;
	}

	public void setPessimistic(Double pessimistic) {
		this.pessimistic = pessimistic;
	}

	public Double getRealistic() {
		return this.realistic;
	}

	public void setRealistic(Double realistic) {
		this.realistic = realistic;
	}

	public Double getOptimistic() {
		return this.optimistic;
	}

	public void setOptimistic(Double optimistic) {
		this.optimistic = optimistic;
	}

	public List<List<TaskRepresentor>> getDependantChain() {
		return this.dependantChain;
	}

	public void setDependantChain(List<List<TaskRepresentor>> dependantChain) {
		this.dependantChain = dependantChain;
	}

	public List<List<TaskRepresentor>> getDependencyChain() {
		return this.dependencyChain;
	}

	public void setDependencyChain(List<List<TaskRepresentor>> dependencyChain) {
		this.dependencyChain = dependencyChain;
	}

	public int getDependantCount() {
		int total = 0;
		for (final List<TaskRepresentor> dependantLevel : this.dependantChain) {
			total += dependantLevel.size();
		}
		return total;
	}

	public int getDependencyCount() {
		int total = 0;
		for (final List<TaskRepresentor> dependencyLevel : this.dependencyChain) {
			total += dependencyLevel.size();
		}
		return total;
	}

	@Override
	public String toString() {
		return "TaskRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority + ", completion="
				+ this.completion + ", deadline=" + this.deadline + ", estimated=" + this.isEstimated() + ", duration=" + this.duration + ", pessimistic="
				+ this.pessimistic + ", realistic=" + this.realistic + ", optimistic=" + this.optimistic + ", admittance=" + this.admittance + ", creator="
				+ this.creator + ", creationDate=" + this.creationDate + ", modifier=" + this.modifier + ", modificationDate=" + this.modificationDate
				+ ", assignedTeams=" + this.assignedTeams + ", assignedUsers=" + this.assignedUsers + ", impediments=" + this.impediments + ", dependantTasks="
				+ this.dependantTasks + ", taskDependencies=" + this.taskDependencies + ", objective=" + this.objective + ", project=" + this.project
				+ ", submodule=" + this.submodule + ", dependantChain=" + this.dependantChain + ", dependencyChain=" + this.dependencyChain
				+ ", dependantCount=" + this.dependantCount + ", dependencyCount=" + this.dependencyCount + "]";
	}

	@Override
	public String toTextMessage() {
		return "TaskRepresentor | [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority
				+ ", completion=" + this.completion + ", deadline=" + this.deadline + ", admittance=" + this.admittance + ", creator_id=" + this.creator.getId()
				+ ", creationDate=" + this.creationDate + ", modifier_id=" + this.modifier.getId() + ", objective_id="
				+ (this.objective != null ? this.objective.getId().toString() : "null") + ", project_id="
				+ (this.project != null ? this.project.getId().toString() : "null") + ", submodule_id="
				+ (this.submodule != null ? this.submodule.getId().toString() : "null") + ", duration=" + this.duration + ", pessimistic=" + this.pessimistic
				+ ", realistic=" + this.realistic + ", optimistic=" + this.optimistic + "]";
	}

	public void addTaskDependency(TaskRepresentor dependency) {
		this.taskDependencies.add(dependency);
	}

	public void addDependantTask(TaskRepresentor dependant) {
		this.dependantTasks.add(dependant);
	}

	public void addTeam(TeamTaskAssignmentRepresentor team) {
		this.assignedTeams.add(team);
	}

	public void addUser(AppUserTaskAssignmentRepresentor user) {
		this.assignedUsers.add(user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final TaskRepresentor other = (TaskRepresentor) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(TaskRepresentor o) {
		// TODO Auto-generated method stub
		return 0;
	}

}