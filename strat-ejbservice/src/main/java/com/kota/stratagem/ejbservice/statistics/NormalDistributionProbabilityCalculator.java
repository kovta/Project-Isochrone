package com.kota.stratagem.ejbservice.statistics;

import com.kota.stratagem.ejbservice.qualifier.NormalDistributionBased;

@NormalDistributionBased
public class NormalDistributionProbabilityCalculator implements ProbabilityCalculator {

	@Override
	public Double calculateExpectedDuration(Double pessimistic, Double realistic, Double optimistic) {
		return (pessimistic + (4 * realistic) + optimistic) / 6;
	}

	@Override
	public Double calculateVariance(Double pessimistic, Double realistic, Double optimistic) {
		return Math.sqrt((pessimistic - optimistic) / 6);
	}

	@Override
	public Double calculateCumulitiveNormalDistribution(Double deviationFromTarget, Double standardDeviation) {
		return this.cumulitiveNormalDistribution(deviationFromTarget / standardDeviation);
	}

	@Override
	public Double calculateCumulitiveNormalDistribution(Double target, Double expectedValue, Double standardDeviation) {
		return this.cumulitiveNormalDistribution((target - expectedValue) / standardDeviation);
	}

	private Double cumulitiveNormalDistribution(double z) {
		final int neg = (z < 0d) ? 1 : 0;
		if (neg == 1) {
			z *= -1d;
		}
		final double k = (1d / (1d + (0.2316419 * z)));
		double y = ((((((((1.330274429 * k) - 1.821255978) * k) + 1.781477937) * k) - 0.356563782) * k) + 0.319381530) * k;
		y = 1.0 - (0.398942280401 * Math.exp(-0.5 * z * z) * y);
		return ((1d - neg) * y) + (neg * (1d - y));
	}

	@Override
	public Double estimateProbability(Double expectedValue, Double standardDeviation, Double confidenceLevel) {
		return (1.645 * standardDeviation) + expectedValue;
	}





}
