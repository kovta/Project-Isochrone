package com.kota.stratagem.ejbservice.preparation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kota.stratagem.ejbservice.comparison.congregated.ObjectiveClusterComparator;
import com.kota.stratagem.ejbservice.comparison.congregated.ProjectSummaryComparator;
import com.kota.stratagem.ejbservice.comparison.congregated.SubmoduleSummaryComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.SubmoduleCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TeamAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueSubmoduleComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueTaskComparator;
import com.kota.stratagem.ejbservice.qualifier.ProjectOriented;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

@ProjectOriented
public class ProjectExtensionManager extends AbstractDTOExtensionManager {

	ProjectRepresentor representor;
	List<ObjectiveRepresentor> representors;

	@Override
	public ProjectRepresentor prepare(ProjectRepresentor representor) {
		this.representor = representor;
		return super.prepare(representor);
	}

	@Override
	public List<ObjectiveRepresentor> prepareProjectClusters(List<ObjectiveRepresentor> representors) {
		this.representors = representors;
		return super.prepareProjectClusters(representors);
	}

	@Override
	protected void addRepresentorSpecificProperties() {
		this.provideCompletion();
		this.provideOverdueSubmodules();
		this.provideOngoingSubmodules();
		this.provideCompletedSubmodules();
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
		Collections.sort(this.representor.getOverdueSubmodules(), new OverdueSubmoduleComparator());
		Collections.sort(this.representor.getOngoingSubmodules(), new SubmoduleCompletionComparator());
		Collections.sort(this.representor.getCompletedSubmodules(), new SubmoduleSummaryComparator());
		Collections.sort(this.representor.getOverdueTasks(), new OverdueTaskComparator());
		Collections.sort(this.representor.getOngoingTasks(), new TaskCompletionComparator());
		Collections.sort(this.representor.getCompletedTasks(), new TaskNameComparator());
	}

	@Override
	protected void sortJointCollection() {
		Collections.sort(this.representors, new ObjectiveClusterComparator());
		for (final ObjectiveRepresentor objective : this.representors) {
			Collections.sort(objective.getProjects(), new ProjectSummaryComparator());
		}
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
		for (final SubmoduleRepresentor submodule : this.representor.getSubmodules()) {
			int submoduleProgress = 0, submoduleTaskCount = 0;
			for (final TaskRepresentor submoduleTask : submodule.getTasks()) {
				progressSum += submoduleTask.getCompletion();
				submoduleProgress += submoduleTask.getCompletion();
				taskCount++;
				submoduleTaskCount++;
			}
			submodule.setCompletion(submoduleTaskCount != 0 ? submoduleProgress / submoduleTaskCount : 0);
		}
		this.representor.setCompletion(taskCount != 0 ? progressSum / taskCount : 0);
	}

	private void provideOverdueSubmodules() {
		final List<SubmoduleRepresentor> submodules = new ArrayList<>();
		for (final SubmoduleRepresentor representor : this.representor.getSubmodules()) {
			if ((representor.getUrgencyLevel() == 3) && (!representor.isCompleted())) {
				submodules.add(representor);
			}
		}
		this.representor.setOverdueSubmodules(submodules);
	}

	private void provideOngoingSubmodules() {
		final List<SubmoduleRepresentor> submodules = new ArrayList<>();
		for (final SubmoduleRepresentor representor : this.representor.getSubmodules()) {
			if ((representor.getUrgencyLevel() != 3) && (representor.getCompletion() != 100)) {
				submodules.add(representor);
			}
		}
		this.representor.setOngoingSubmodules(submodules);
	}

	private void provideCompletedSubmodules() {
		final List<SubmoduleRepresentor> submodules = new ArrayList<>();
		for (final SubmoduleRepresentor representor : this.representor.getSubmodules()) {
			if (representor.isCompleted()) {
				submodules.add(representor);
			}
		}
		this.representor.setCompletedSubmodules(submodules);
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
