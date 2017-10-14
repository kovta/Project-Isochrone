package com.kota.stratagem.ejbservice.converter.evaluation;

import com.kota.stratagem.persistence.util.Constants;

public class AbstractCPMNodeConverter {

	protected static final String SUBMODULE_NODE_ID_PREFIX = "SMD-";
	protected static final String TASK_NODE_ID_PREFIX = "TSK-";

	protected String buildNodeId(Long id, String structureType) {
		String nodeId = null;
		switch(structureType) {
			case Constants.SUBMODULE_REPRESENTOR_DATA_LABEL:
				nodeId = SUBMODULE_NODE_ID_PREFIX;
				break;
			case Constants.TASK_REPRESENTOR_DATA_LABEL:
				nodeId = TASK_NODE_ID_PREFIX;
				break;
		}
		return(nodeId + id);
	}

	protected boolean isCorresponding(Long id, String nodeId, String structureType) {
		switch(structureType) {
			case Constants.SUBMODULE_REPRESENTOR_DATA_LABEL:
				return nodeId.replace(SUBMODULE_NODE_ID_PREFIX, Constants.EMPTY_DATA_LABEL).equals(id.toString());
			case Constants.TASK_REPRESENTOR_DATA_LABEL:
				return nodeId.replace(TASK_NODE_ID_PREFIX, Constants.EMPTY_DATA_LABEL).equals(id.toString());
		}
		return false;
	}

}
