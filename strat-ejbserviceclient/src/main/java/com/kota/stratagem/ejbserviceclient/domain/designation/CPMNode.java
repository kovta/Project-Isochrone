package com.kota.stratagem.ejbserviceclient.domain.designation;

import java.util.List;

public interface CPMNode {

	List<CPMNode> getDependencies();

	List<CPMNode> getDependants();

	void addDependency(CPMNode dependency);

	void addDependant(CPMNode dependant);

}
