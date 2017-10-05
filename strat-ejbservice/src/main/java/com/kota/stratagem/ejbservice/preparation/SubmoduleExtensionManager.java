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
import com.kota.stratagem.ejbservice.interceptor.Certified;
import com.kota.stratagem.ejbservice.qualifier.Definitive;
import com.kota.stratagem.ejbservice.qualifier.Estimated;
import com.kota.stratagem.ejbservice.qualifier.NormalDistributionBased;
import com.kota.stratagem.ejbservice.qualifier.SubmoduleOriented;
import com.kota.stratagem.ejbservice.statistics.ProbabilityCalculator;
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
	@NormalDistributionBased
	private ProbabilityCalculator calculator;

	@Inject
	@Estimated
	private DependencyNetworkEvaluator estimatedEvaluator;

	@Inject
	@Definitive
	private DependencyNetworkEvaluator definitiveEvaluator;

	SubmoduleRepresentor representor;

	@Override
	@Certified(SubmoduleRepresentor.class)
	public Object prepare(Object representor) {
		this.representor = (SubmoduleRepresentor) representor;
		return super.prepare(representor);
	}

	@Override
	@Certified(SubmoduleRepresentor.class)
	public Object prepareForOwner(Object representor) {
		this.representor = (SubmoduleRepresentor) representor;
		return super.prepareForOwner(representor);
	}

	@Override
	public List<?> prepareBatch(List<?> representors) {
		return super.prepareBatch(representors);
	}

	@Override
	protected void addRepresentorSpecificProperties() {
		this.provideProgressionDetials();
		this.provideEvaluationDetails();
		this.provideCategorizedTasks();
	}

	@Override
	protected void addOwnerDependantProperties() {
		this.provideProgressionDetials();
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

	private void provideProgressionDetials() {
		int progressSum = 0;
		double durationSum = 0, completedDurationSum = 0;
		for (final TaskRepresentor task : this.representor.getTasks()) {
			progressSum += task.getCompletion();
			if (task.isEstimated()) {
				double expectedDuration = this.calculator.calculateExpectedDuration(task.getPessimistic(), task.getRealistic(), task.getOptimistic());
				durationSum += expectedDuration;
				completedDurationSum += expectedDuration * (task.getCompletion() / 100);
			} else if (task.isDurationProvided()) {
				durationSum += task.getDuration();
				completedDurationSum += task.getDuration() * (task.getCompletion() / 100);
			}
		}
		this.representor.setCompletion(this.representor.getTasks().size() != 0 ? progressSum / this.representor.getTasks().size() : 0);
		this.representor.setDurationSum(durationSum);
		this.representor.setCompletedDurationSum(completedDurationSum);
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
		final double estimatedDuration = this.calculator.estimateProbability(result.getExpectedDuration(), result.getStandardDeviation(), (double) 95);
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, (int) estimatedDuration);
		this.representor.setEstimatedCompletionDate(calendar.getTime());
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, result.getExpectedDuration().intValue());
		this.representor.setExpectedCompletionDate(calendar.getTime());
		if (this.representor.isDeadlineProvided()) {
			final Long difference = TimeUnit.DAYS.convert(this.representor.getDeadline().getTime() - calendar.getTime().getTime(), TimeUnit.DAYS) / 86400000;
			this.representor.setTargetDeviation(difference.doubleValue());
			this.representor.setEarlyFinishEstimation(this.calculator.calculateCumulitiveNormalDistribution(difference.doubleValue(), result.getStandardDeviation()));
		}
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
