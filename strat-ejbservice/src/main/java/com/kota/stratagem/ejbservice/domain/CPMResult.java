package com.kota.stratagem.ejbservice.domain;

public class CPMResult {

	private final Double expectedDuration;
	private final Double standardDeviation;

	public CPMResult(Double expectedDuration, Double standardDeviation) {
		this.expectedDuration = expectedDuration;
		this.standardDeviation = standardDeviation;
	}

	public Double getExpectedDuration() {
		return this.expectedDuration;
	}

	public Double getStandardDeviation() {
		return this.standardDeviation;
	}

}