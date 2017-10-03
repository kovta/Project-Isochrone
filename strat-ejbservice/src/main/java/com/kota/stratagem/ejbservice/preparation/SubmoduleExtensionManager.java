package com.kota.stratagem.ejbservice.preparation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TeamAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueTaskComparator;
import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.converter.evaluation.CPMNodeConverter;
import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbservice.evaluation.DependencyNetworkEvaluator;
import com.kota.stratagem.ejbservice.exception.CyclicDependencyException;
import com.kota.stratagem.ejbservice.exception.InvalidNodeTypeException;
import com.kota.stratagem.ejbservice.qualifier.Definitive;
import com.kota.stratagem.ejbservice.qualifier.Estimated;
import com.kota.stratagem.ejbservice.qualifier.SubmoduleOriented;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.service.TaskService;

@SubmoduleOriented
public class SubmoduleExtensionManager extends AbstractDTOExtensionManager {

	@EJB
	private TaskService taskService;

	@Inject
	private TaskConverter taskConverter;

	@Inject
	private CPMNodeConverter cpmNodeConverter;

	@Inject
	@Estimated
	private DependencyNetworkEvaluator estimatedEvaluator;

	@Inject
	@Definitive
	private DependencyNetworkEvaluator definitiveEvaluator;

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
		this.provideEvaluationDetails();
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
		Collections.sort(this.representor.getOverdueTasks(), new OverdueTaskComparator());
		Collections.sort(this.representor.getOngoingTasks(), new TaskCompletionComparator());
		Collections.sort(this.representor.getCompletedTasks(), new TaskNameComparator());
	}

	@Override
	protected void sortBaseCollections() {
		Collections.sort(this.representor.getAssignedUsers(), new AppUserAssignmentRecipientNameComparator());
		Collections.sort(this.representor.getAssignedTeams(), new TeamAssignmentRecipientNameComparator());
	}

	@Override
	protected void sortJointCollection() {

	}

	private void provideCompletion() {
		int progressSum = 0;
		for (final TaskRepresentor task : this.representor.getTasks()) {
			progressSum += task.getCompletion();
		}
		this.representor.setCompletion(this.representor.getTasks().size() != 0 ? progressSum / this.representor.getTasks().size() : 0);
	}

	private void provideDurationSum() {
		Double durationSum = (double) 0;
		for (final TaskRepresentor task : this.representor.getTasks()) {
			if (task.isEstimated()) {
				durationSum += ((task.getPessimistic() + (4 * task.getRealistic()) + task.getOptimistic()) / 6);
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
				durationSum += ((task.getPessimistic() + (4 * task.getRealistic()) + task.getOptimistic()) / 6) * (task.getCompletion() / 100);
			} else if (task.isDurationProvided()) {
				durationSum += task.getDuration() * (task.getCompletion() / 100);
			}
		}
		this.representor.setCompletedDurationSum(durationSum);
	}

	private void provideEvaluationDetails() {
		if (!this.representor.isCompleted()) {
			Boolean estimated = false, configured = false;
			final List<TaskRepresentor> components = new ArrayList<>();
			for (final TaskRepresentor task : this.representor.getTasks()) {
				if (task.isEstimated() && !task.isCompleted()) {
					estimated = true;
					configured = true;
					components.add(this.taskConverter.toSimplified(this.taskService.readWithDirectDependencies(task.getId())));
				} else if (task.isDurationProvided() && !task.isCompleted()) {
					configured = true;
					components.add(this.taskConverter.toSimplified(this.taskService.readWithDirectDependencies(task.getId())));
				}
			}
			CPMResult result = null;
			try {
				if (configured) {
					if (estimated) {
						for (final TaskRepresentor component : components) {
							final double completionRatio = ((100 - component.getCompletion()) / 100);
							component.setPessimistic(component.getPessimistic() * completionRatio);
							component.setRealistic(component.getRealistic() * completionRatio);
							component.setOptimistic(component.getOptimistic() * completionRatio);
						}
						result = this.estimatedEvaluator.evaluate(this.cpmNodeConverter.to(components));
					} else {
						for (final TaskRepresentor component : components) {
							component.setDuration(component.getDuration() / ((100 - component.getCompletion()) / 100));
						}
						result = this.definitiveEvaluator.evaluate(this.cpmNodeConverter.to(components));
					}
				}
			} catch (InvalidNodeTypeException | CyclicDependencyException e) {
				LOGGER.error(e, e);
			}
			if (result != null) {
				this.provideEstimations(result);
			}
		}
	}

	private void provideEstimations(CPMResult result) {
		final Calendar calendar = Calendar.getInstance();
		final double estimatedDuration = ((1.645 * result.getStandardDeviation()) + result.getExpectedDuration());
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, (int) estimatedDuration);
		this.representor.setEstimatedCompletionDate(calendar.getTime());
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, result.getExpectedDuration().intValue());
		this.representor.setExpectedCompletionDate(calendar.getTime());
		if (this.representor.isDeadlineProvided()) {
			final Long difference = TimeUnit.DAYS.convert(this.representor.getDeadline().getTime() - calendar.getTime().getTime(), TimeUnit.DAYS) / 86400000;
			this.representor.setTargetDeviation(difference.doubleValue());
			this.representor.setEarlyFinishEstimation(this.cumulitiveNormalDistribution(difference.doubleValue() / result.getStandardDeviation()));
		}
	}

	private double cumulitiveNormalDistribution(double z) {
		final int neg = (z < 0d) ? 1 : 0;
		if (neg == 1) {
			z *= -1d;
		}
		final double k = (1d / (1d + (0.2316419 * z)));
		double y = ((((((((1.330274429 * k) - 1.821255978) * k) + 1.781477937) * k) - 0.356563782) * k) + 0.319381530) * k;
		y = 1.0 - (0.398942280401 * Math.exp(-0.5 * z * z) * y);
		return ((1d - neg) * y) + (neg * (1d - y));
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
