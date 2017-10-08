package com.kota.stratagem.ejbservice.converter.evaluation;

import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

public interface CPMNodeConverter {

	List<CPMNode> to(List<?> components);

}
