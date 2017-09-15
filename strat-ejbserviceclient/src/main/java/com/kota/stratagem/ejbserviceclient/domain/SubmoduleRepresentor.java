package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	private List<TaskRepresentor> overdueTasks;
	private List<TaskRepresentor> ongoingTasks;
	private List<TaskRepresentor> completedTasks;

	public SubmoduleRepresentor() {
		this(null, "", "", new Date(), null);
	}

	public SubmoduleRepresentor(Long id, String name, String description, Date deadline, ProjectRepresentor project) {
		super(deadline != null ? deadline : new Date(), id);
		this.id = id;
		this.name = name;
		this.description = description;
		this.deadline = deadline;
		this.tasks = new ArrayList<>();
		this.assignedTeams = new ArrayList<>();
		this.assignedUsers = new ArrayList<>();
		this.project = project;
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

	public double getCompletion() {
		int progressSum = 0;
		for (final TaskRepresentor task : this.getTasks()) {
			progressSum += task.getCompletion();
		}
		return this.getTasks().size() != 0 ? progressSum / this.getTasks().size() : 0;
	}

	public Double getDurationSum() {
		Double durationSum = (double) 0;
		for (final TaskRepresentor task : this.getTasks()) {
			if (task.isEstimated()) {

			} else if (task.isDurationProvided()) {
				durationSum += task.getDuration();
			}
		}
		return durationSum;
	}

	public Double getCompletedDurationSum() {
		Double durationSum = (double) 0;
		for (final TaskRepresentor task : this.getTasks()) {
			if (task.isEstimated()) {

			} else if (task.isCompleted() && task.isDurationProvided()) {
				durationSum += task.getDuration();
			}
		}
		return durationSum;
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
		Collections.sort(this.overdueTasks, new Comparator<TaskRepresentor>() {
			@Override
			public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
				final int c = obj_a.getDeadline().compareTo(obj_b.getDeadline());
				if (c == 0) {
					return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
				}
				return c;
			}
		});
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
		Collections.sort(this.ongoingTasks, new Comparator<TaskRepresentor>() {
			@Override
			public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
				return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
			}
		});
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
		Collections.sort(this.completedTasks, new Comparator<TaskRepresentor>() {
			@Override
			public int compare(TaskRepresentor obj_a, TaskRepresentor obj_b) {
				return obj_a.getName().toLowerCase().compareTo(obj_b.getName().toLowerCase());
			}
		});
		return this.completedTasks;
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
