package com.kota.stratagem.ejbservice.preparation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserAssignmentCreatorNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskNameComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueTaskComparator;
import com.kota.stratagem.ejbservice.qualifier.SubmoduleOriented;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

@SubmoduleOriented
public class SubmoduleExtensionManager extends AbstractDTOExtensionManager {

	SubmoduleRepresentor representor;

	@Override
	public SubmoduleRepresentor prepare(SubmoduleRepresentor representor) {
		this.representor = representor;
		return super.prepare(representor);
	}

	@Override
	protected void addRepresentorSpecificProperties() {
		this.provideCompletion();
		this.provideDurationSum();
		this.provideCompletedDurationSum();
		this.provideOverdueTasks();
		this.provideOngoingTasks();
		this.provideCompletedTasks();
	}

	@Override
	protected void sortSpecializedCollections() {
		Collections.sort(this.representor.getOverdueTasks(), new OverdueTaskComparator());
		Collections.sort(this.representor.getOngoingTasks(), new TaskCompletionComparator());
		Collections.sort(this.representor.getCompletedTasks(), new TaskNameComparator());
	}

	@Override
	protected void sortBaseCollections() {
		Collections.sort(this.representor.getAssignedUsers(), new AppUserAssignmentCreatorNameComparator());
	}

	@Override
	protected void sortJointCollection() {

	}

	private void provideDurationSum() {
		Double durationSum = (double) 0;
		for (final TaskRepresentor task : this.representor.getTasks()) {
			if (task.isEstimated()) {

			} else if (task.isDurationProvided()) {
				durationSum += task.getDuration();
			}
		}
		this.representor.setDurationSum(durationSum);
	}

	private void provideCompletedDurationSum() {
		Double durationSum = (double) 0;
		for (final TaskRepresentor task : this.representor.getTasks()) {
			if (task.isEstimated()) {

			} else if (task.isCompleted() && task.isDurationProvided()) {
				durationSum += task.getDuration();
			}
		}
		this.representor.setCompletedDurationSum(durationSum);
	}

	private void provideCompletion() {
		int progressSum = 0;
		for (final TaskRepresentor task : this.representor.getTasks()) {
			progressSum += task.getCompletion();
		}
		this.representor.setCompletion(this.representor.getTasks().size() != 0 ? progressSum / this.representor.getTasks().size() : 0);
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
