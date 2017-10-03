package com.kota.stratagem.ejbservice.preparation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kota.stratagem.ejbservice.comparison.congregated.ObjectiveSummaryComparator;
import com.kota.stratagem.ejbservice.comparison.congregated.ProjectSummaryComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.ProjectCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TeamAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueProjectComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueTaskComparator;
import com.kota.stratagem.ejbservice.qualifier.ObjectiveOriented;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

@ObjectiveOriented
public class ObjectiveExtensionManager extends AbstractDTOExtensionManager {

	ObjectiveRepresentor representor;
	List<ObjectiveRepresentor> representors;

	@Override
	public ObjectiveRepresentor prepare(ObjectiveRepresentor representor) {
		this.representor = representor;
		return super.prepare(representor);
	}

	@Override
	public List<ObjectiveRepresentor> prepareObjectives(List<ObjectiveRepresentor> representors) {
		this.representors = representors;
		return super.prepareObjectives(representors);
	}

	@Override
	protected void addRepresentorSpecificProperties() {
		this.provideCompletion();
		this.provideOverdueProjects();
		this.provideOngoingProjects();
		this.provideCompletedProjects();
		this.provideOverdueTasks();
		this.provideOngoingTasks();
		this.provideCompletedTasks();
	}

	@Override
	protected void addParentDependantProperties() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void sortSpecializedCollections() {
		Collections.sort(this.representor.getOverdueProjects(), new OverdueProjectComparator());
		Collections.sort(this.representor.getOngoingProjects(), new ProjectCompletionComparator());
		Collections.sort(this.representor.getCompletedProjects(), new ProjectSummaryComparator());
		Collections.sort(this.representor.getOverdueTasks(), new OverdueTaskComparator());
		Collections.sort(this.representor.getOngoingTasks(), new TaskCompletionComparator());
		Collections.sort(this.representor.getCompletedTasks(), new TaskNameComparator());
	}

	@Override
	protected void sortJointCollection() {
		Collections.sort(this.representors, new ObjectiveSummaryComparator());
	}

	@Override
	protected void sortBaseCollections() {
		Collections.sort(this.representor.getAssignedUsers(), new AppUserAssignmentRecipientNameComparator());
		Collections.sort(this.representor.getAssignedTeams(), new TeamAssignmentRecipientNameComparator());
	}

	private void provideCompletion() {
		int progressSum = 0, taskCount = 0;
		for (final TaskRepresentor task : this.representor.getTasks()) {
			progressSum += task.getCompletion();
			taskCount++;
		}
		for (final ProjectRepresentor project : this.representor.getProjects()) {
			int projectProgress = 0, projectTaskCount = 0;
			for (final TaskRepresentor projectTask : project.getTasks()) {
				progressSum += projectTask.getCompletion();
				projectProgress += projectTask.getCompletion();
				taskCount++;
				projectTaskCount++;
			}
			for (final SubmoduleRepresentor projectSubmodule : project.getSubmodules()) {
				for (final TaskRepresentor submoduleTask : projectSubmodule.getTasks()) {
					progressSum += submoduleTask.getCompletion();
					projectProgress += submoduleTask.getCompletion();
					taskCount++;
					projectTaskCount++;
				}
			}
			project.setCompletion(projectTaskCount != 0 ? projectProgress / projectTaskCount : 0);
		}
		this.representor.setCompletion(taskCount != 0 ? progressSum / taskCount : 0);
	}

	private void provideOverdueProjects() {
		final List<ProjectRepresentor> projects = new ArrayList<>();
		for (final ProjectRepresentor representor : this.representor.getProjects()) {
			if ((representor.getUrgencyLevel() == 3) && (!representor.isCompleted())) {
				projects.add(representor);
			}
		}
		this.representor.setOverdueProjects(projects);
	}

	private void provideOngoingProjects() {
		final List<ProjectRepresentor> projects = new ArrayList<>();
		for (final ProjectRepresentor representor : this.representor.getProjects()) {
			if ((representor.getUrgencyLevel() != 3) && (representor.getCompletion() != 100)) {
				projects.add(representor);
			}
		}
		this.representor.setOngoingProjects(projects);
	}

	private void provideCompletedProjects() {
		final List<ProjectRepresentor> projects = new ArrayList<>();
		for (final ProjectRepresentor representor : this.representor.getProjects()) {
			if (representor.isCompleted()) {
				projects.add(representor);
			}
		}
		this.representor.setCompletedProjects(projects);
	}

	private void provideOverdueTasks() {
		final List<TaskRepresentor> tasks = new ArrayList<>();
		for (final TaskRepresentor representor : this.representor.getTasks()) {
			if ((representor.getUrgencyLevel() == 3) && (!representor.isCompleted())) {
				tasks.add(representor);
			}
		}
		this.representor.setOverdueTasks(tasks);
	}

	private void provideOngoingTasks() {
		final List<TaskRepresentor> tasks = new ArrayList<>();
		for (final TaskRepresentor representor : this.representor.getTasks()) {
			if ((representor.getUrgencyLevel() != 3) && (!representor.isCompleted())) {
				tasks.add(representor);
			}
		}
		this.representor.setOngoingTasks(tasks);
	}

	private void provideCompletedTasks() {
		final List<TaskRepresentor> tasks = new ArrayList<>();
		for (final TaskRepresentor representor : this.representor.getTasks()) {
			if (representor.isCompleted()) {
				tasks.add(representor);
			}
		}
		this.representor.setCompletedTasks(tasks);
	}

}
