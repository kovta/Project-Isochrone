package com.kota.stratagem.ejbservice.preparation;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbservice.qualifier.NormalDistributionBased;
import com.kota.stratagem.ejbservice.qualifier.Shared;
import com.kota.stratagem.ejbservice.statistics.ProbabilityCalculator;
import com.kota.stratagem.ejbserviceclient.domain.AbstractProgressionRepresentor;

@Shared
public class SharedExtensionProvider implements ExtensionProvider {

	@Inject
	@NormalDistributionBased
	private ProbabilityCalculator calculator;

	@Override
	public void provideEstimations(CPMResult result, AbstractProgressionRepresentor representor) {
		final Calendar calendar = Calendar.getInstance();
		final double estimatedDuration = this.calculator.estimateProbability(result.getExpectedDuration(), result.getStandardDeviation(), (double) 95);
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

}
