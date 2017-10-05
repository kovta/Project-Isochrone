package com.kota.stratagem.ejbservice.statistics;

public interface ProbabilityCalculator {

	Double calculateExpectedDuration(Double pessimistic, Double realistic, Double optimistic);

	Double calculateVariance(Double pessimistic, Double realistic, Double optimistic);

	Double calculateCumulitiveNormalDistribution(Double deviationFromTarget, Double standardDeviation);

	Double calculateCumulitiveNormalDistribution(Double target, Double expectedValue, Double standardDeviation);

	Double estimateProbability(Double expectedValue, Double standardDeviation, Double confidenceLevel);

}
