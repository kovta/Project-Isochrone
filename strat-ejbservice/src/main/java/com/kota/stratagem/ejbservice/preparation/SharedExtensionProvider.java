package com.kota.stratagem.ejbservice.preparation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.converter.evaluation.CPMNodeConverter;
import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbservice.evaluation.DependencyNetworkEvaluator;
import com.kota.stratagem.ejbservice.exception.CyclicDependencyException;
import com.kota.stratagem.ejbservice.exception.InvalidNodeTypeException;
import com.kota.stratagem.ejbservice.qualifier.Definitive;
import com.kota.stratagem.ejbservice.qualifier.Estimated;
import com.kota.stratagem.ejbservice.qualifier.NormalDistributionBased;
import com.kota.stratagem.ejbservice.qualifier.Shared;
import com.kota.stratagem.ejbservice.statistics.ProbabilityCalculator;
import com.kota.stratagem.ejbserviceclient.domain.AbstractProgressionRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;
import com.kota.stratagem.persistence.qualifier.TaskFocused;
import com.kota.stratagem.persistence.service.TaskService;

@Shared
public class SharedExtensionProvider implements ExtensionProvider {

	protected static final Logger LOGGER = Logger.getLogger(SharedExtensionProvider.class);

	@EJB
	private TaskService taskService;

	@Inject
	private TaskConverter taskConverter;

	@Inject
	@TaskFocused
	private CPMNodeConverter cpmNodeConverter;

	@Inject
	@Estimated
	private DependencyNetworkEvaluator estimatedEvaluator;

	@Inject
	@Definitive
	private DependencyNetworkEvaluator definitiveEvaluator;

	@Inject
	@NormalDistributionBased
	private ProbabilityCalculator calculator;

	@Override
	public void provideBaseEstimationDetails(TaskRepresentor baseComponent) {
		baseComponent.setExpectedDuration(baseComponent.isEstimated()
				? this.calculator.calculateExpectedDuration(baseComponent.getPessimistic(), baseComponent.getRealistic(), baseComponent.getOptimistic())
				: baseComponent.isDurationProvided() ? baseComponent.getDuration() : null);
		baseComponent.setVariance(baseComponent.isEstimated()
				? this.calculator.calculateVariance(baseComponent.getPessimistic(), baseComponent.getRealistic(), baseComponent.getOptimistic()) : 0);
	}

	@Override
	public void addCompletionAdaptedComponent(List<CPMNode> components, TaskRepresentor task) {
		components.add(this.adaptEstimationsToCompletion(this.taskConverter.toSimplified(this.taskService.readWithDirectDependencies(task.getId()))));
	}

	@Override
	public TaskRepresentor adaptEstimationsToCompletion(TaskRepresentor baseComponent) {
		final double completionRatio = ((100 - baseComponent.getCompletion()) / 100);
		if (baseComponent.isEstimated()) {
			baseComponent.setPessimistic(baseComponent.getPessimistic() * completionRatio);
			baseComponent.setRealistic(baseComponent.getRealistic() * completionRatio);
			baseComponent.setOptimistic(baseComponent.getOptimistic() * completionRatio);
		} else {
			baseComponent.setDuration(baseComponent.getDuration() * completionRatio);
		}
		return baseComponent;
	}

	@Override
	public CPMResult evaluateDependencyNetwork(List<CPMNode> network, boolean estimated) {
		try {
			return estimated ? this.estimatedEvaluator.evaluate(network) : this.definitiveEvaluator.evaluate(network);
		} catch (InvalidNodeTypeException | CyclicDependencyException e) {
			LOGGER.error(e, e);
		}
		return null;
	}

	@Override
	public void provideEstimations(CPMResult result, AbstractProgressionRepresentor representor) {
		representor.setExpectedDuration(result.getExpectedDuration());
		representor.setVariance(Math.pow(result.getStandardDeviation(), 2));
		final Calendar calendar = Calendar.getInstance();
		final double estimatedDuration = this.calculator.estimateProbability(result.getExpectedDuration(), result.getStandardDeviation(), 95.0);
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, (int) estimatedDuration);
		representor.setEstimatedCompletionDate(calendar.getTime());
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, result.getExpectedDuration().intValue());
		representor.setExpectedCompletionDate(calendar.getTime());
		if (representor.isDeadlineProvided()) {
			final Long difference = TimeUnit.DAYS.convert(representor.getDeadline().getTime() - calendar.getTime().getTime(), TimeUnit.DAYS) / 86400000;
			representor.setTargetDeviation(difference.doubleValue());
			representor
					.setEarlyFinishEstimation(this.calculator.calculateCumulitiveNormalDistribution(difference.doubleValue(), result.getStandardDeviation()));
		}
	}

	@Override
	public void provideBlankEstimations(AbstractProgressionRepresentor representor) {
		representor.setExpectedDuration(0.0);
		representor.setVariance(0.0);
	}

}
