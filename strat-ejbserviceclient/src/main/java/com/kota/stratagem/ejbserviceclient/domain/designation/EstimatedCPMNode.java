package com.kota.stratagem.ejbserviceclient.domain.designation;

public interface EstimatedCPMNode extends CPMNode {

	Double getPessimistic();

	Double getRealistic();

	Double getOptimistic();

}
