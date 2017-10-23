package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;
import com.kota.stratagem.ejbserviceclient.domain.designation.DefinitiveCPMNode;
import com.kota.stratagem.ejbserviceclient.domain.designation.EstimatedCPMNode;

public class SubmoduleRepresentor extends AbstractProgressionRepresentor implements Serializable, DefinitiveCPMNode, EstimatedCPMNode {

	private static final long serialVersionUID = -7646277745869655229L;

	private Long id;
	private final String name;
	private final String description;
	private final List<TaskRepresentor> tasks;
	private final List<TeamSubmoduleAssignmentRepresentor> assignedTeams;
	private final List<AppUserSubmoduleAssignmentRepresentor> assignedUsers;
	private final List<SubmoduleRepresentor> dependantSubmodules;
	private final List<SubmoduleRepresentor> submoduleDependencies;
	private ProjectRepresentor project;

	private List<TaskRepresentor> overdueTasks;
	private List<TaskRepresentor> ongoingTasks;
	private List<TaskRepresentor> completedTasks;
	private List<List<SubmoduleRepresentor>> dependantChain;
	private List<List<SubmoduleRepresentor>> dependencyChain;
	private int dependencyLevel;
	private int dependantCount;
	private int dependencyCount;

	public SubmoduleRepresentor() {
		this(null, "", "", new Date(), null);
	}

	public SubmoduleRepresentor(Long id, String name, String description, Date deadline, ProjectRepresentor project) {
		this(name, description, deadline, project);
		this.id = id;
	}

	public SubmoduleRepresentor(String name, String description, Date deadline, ProjectRepresentor project) {
		super(deadline);
		this.name = name;
		this.description = description;
		this.tasks = new ArrayList<>();
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.dependantSubmodules = new ArrayList<>();
		this.submoduleDependencies = new ArrayList<>();
		this.dependantChain = new ArrayList<>();
		this.dependencyChain = new ArrayList<>();
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

	public List<TaskRepresentor> getTasks() {
		return this.tasks;
	}

	public List<TeamSubmoduleAssignmentRepresentor> getAssignedTeams() {
		return this.assignedTeams;
	}

	public List<AppUserSubmoduleAssignmentRepresentor> getAssignedUsers() {
		return this.assignedUsers;
	}

	public List<SubmoduleRepresentor> getDependantSubmodules() {
		return this.dependantSubmodules;
	}

	public List<SubmoduleRepresentor> getSubmoduleDependencies() {
		return this.submoduleDependencies;
	}

	@Override
	public List<CPMNode> getDependencies() {
		return new ArrayList<CPMNode>(this.submoduleDependencies);
	}

	@Override
	public List<CPMNode> getDependants() {
		return new ArrayList<CPMNode>(this.dependantSubmodules);
	}

	public ProjectRepresentor getProject() {
		return this.project;
	}

	public void setProject(ProjectRepresentor project) {
		this.project = project;
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
	public Double getDuration() {
		return this.getExpectedDuration();
	}

	public List<List<SubmoduleRepresentor>> getDependantChain() {
		return this.dependantChain;
	}

	public void setDependantChain(List<List<SubmoduleRepresentor>> dependantChain) {
		this.dependantChain = dependantChain;
	}

	public List<List<SubmoduleRepresentor>> getDependencyChain() {
		return this.dependencyChain;
	}

	public void setDependencyChain(List<List<SubmoduleRepresentor>> dependencyChain) {
		this.dependencyChain = dependencyChain;
	}

	public int getDependencyLevel() {
		return dependencyLevel;
	}

	public void setDependencyLevel(int dependencyLevel) {
		this.dependencyLevel = dependencyLevel;
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
		return super.getExpectedDuration();
	}

	@Override
	public Double getVariance() {
		return super.getVariance();
	}

	@Override
	public String toString() {
		return "\nSubmoduleRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", deadline=" + this.deadline + ", creator=" + this.creator + ", creationDate="
				+ this.creationDate + ", modifier=" + this.modifier + ", modificationDate=" + this.modificationDate + ", tasks=" + this.tasks + ", assignedTeams=" + this.assignedTeams
				+ ", assignedUsers=" + this.assignedUsers + ", project=" + this.project + "]";
	}

	@Override
	public String toTextMessage() {
		return "SubmoduleRepresentor | [id=" + this.id + ", name=" + this.name + ", description=" + (((this.description != "") || (this.description != null)) ? this.description : "not_specified")
				+ ", deadline=" + this.deadline + ", creator_id=" + this.creator.getId() + ", creationDate=" + this.creationDate + ", modifier_id=" + this.modifier.getId() + ", project_id="
				+ this.project.getId() + "]";
	}

	@Override
	public void addDependency(CPMNode dependency) {
		this.submoduleDependencies.add((SubmoduleRepresentor) dependency);
	}

	@Override
	public void addDependant(CPMNode dependant) {
		this.dependantSubmodules.add((SubmoduleRepresentor) dependant);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(this.getClass() != obj.getClass()) {
			return false;
		}
		SubmoduleRepresentor other = (SubmoduleRepresentor) obj;
		if(this.id == null) {
			if(other.id != null) {
				return false;
			}
		} else if(!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
