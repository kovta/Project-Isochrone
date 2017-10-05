package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;
import com.kota.stratagem.ejbserviceclient.domain.designation.DefinitiveCPMNode;
import com.kota.stratagem.ejbserviceclient.domain.designation.EstimatedCPMNode;

public class TaskRepresentor extends AbstractTimeConstrainedProgressionRepresentor implements Serializable, DefinitiveCPMNode, EstimatedCPMNode {

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
	private Double expectedDuration;
	private Double variance;

	public TaskRepresentor() {
		this(null, "", "", 5, 0, new Date(), null, false);
	}

	public TaskRepresentor(Long id, String name, String description, int priority, double completion, Date deadline, Double duration, Boolean admittance) {
		this(name, description, priority, completion, deadline, duration, admittance);
		this.id = id;
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

	@Override
	public Boolean isCompleted() {
		return this.completion == 100;
	}

	@Override
	public Boolean isOngoing() {
		return (this.completion < 100) && (this.completion > 0);
	}

	@Override
	public Boolean isUnstarted() {
		return this.completion == 0;
	}

	@Override
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

	@Override
	public List<CPMNode> getDependencies() {
		return new ArrayList<CPMNode>(this.taskDependencies);
	}

	@Override
	public List<CPMNode> getDependants() {
		return new ArrayList<CPMNode>(this.dependantTasks);
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

	@Override
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
		return this.dependantCount;
	}

	public void setDependantCount(int dependantCount) {
		this.dependantCount = dependantCount;
	}

	public int getDependencyCount() {
		return this.dependencyCount;
	}

	public void setDependencyCount(int dependencyCount) {
		this.dependencyCount = dependencyCount;
	}

	@Override
	public Double getExpectedDuration() {
		return this.expectedDuration;
	}

	public void setExpectedDuration(Double expectedDuration) {
		this.expectedDuration = expectedDuration;
	}

	@Override
	public Double getVariance() {
		return this.variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
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

	@Override
	public void addDependency(CPMNode dependency) {
		this.taskDependencies.add((TaskRepresentor) dependency);
	}

	@Override
	public void addDependant(CPMNode dependant) {
		this.dependantTasks.add((TaskRepresentor) dependant);
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

}