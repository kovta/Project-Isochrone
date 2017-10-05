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
import com.kota.stratagem.ejbservice.interceptor.Certified;
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
	@Certified(ObjectiveRepresentor.class)
	public Object prepare(Object representor) {
		this.representor = (ObjectiveRepresentor) representor;
		return super.prepare(representor);
	}

	@Override
	public Object prepareForOwner(Object representor) {
		return super.prepareForOwner(representor);
	}

	@Override
	@Certified(ObjectiveRepresentor.class)
	public List<?> prepareBatch(List<?> representors) {
		this.representors = (List<ObjectiveRepresentor>) representors;
		return super.prepareBatch(representors);
	}

	@Override
	protected void addRepresentorSpecificProperties() {
		this.provideCompletion();
		this.provideCategorizedProjects();
		this.provideCategorizedTasks();
	}

	@Override
	protected void addOwnerDependantProperties() {

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

	private void provideCategorizedProjects() {
		final List<ProjectRepresentor> overdueProjects = new ArrayList<>(), ongoingProjects = new ArrayList<>(), completedProjects = new ArrayList<>();
		for (final ProjectRepresentor representor : this.representor.getProjects()) {
			if ((representor.getUrgencyLevel() == 3) && (!representor.isCompleted())) {
				overdueProjects.add(representor);
			} else if ((representor.getUrgencyLevel() != 3) && (!representor.isCompleted())) {
				ongoingProjects.add(representor);
			} else if (representor.isCompleted()) {
				completedProjects.add(representor);
			}
		}
		this.representor.setOverdueProjects(overdueProjects);
		this.representor.setOngoingProjects(ongoingProjects);
		this.representor.setCompletedProjects(completedProjects);
	}

	private void provideCategorizedTasks() {
		final List<TaskRepresentor> overdueTasks = new ArrayList<>(), ongoingTasks = new ArrayList<>(), completedTasks = new ArrayList<>();
		for (final TaskRepresentor representor : this.representor.getTasks()) {
			if ((representor.getUrgencyLevel() == 3) && (!representor.isCompleted())) {
				overdueTasks.add(representor);
			} else if ((representor.getUrgencyLevel() != 3) && (!representor.isCompleted())) {
				ongoingTasks.add(representor);
			} else if (representor.isCompleted()) {
				completedTasks.add(representor);
			}
		}
		this.representor.setOverdueTasks(overdueTasks);
		this.representor.setOngoingTasks(ongoingTasks);
		this.representor.setCompletedTasks(completedTasks);
	}

}
