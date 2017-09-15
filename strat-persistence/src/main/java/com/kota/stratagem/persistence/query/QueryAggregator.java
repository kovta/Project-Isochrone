package com.kota.stratagem.persistence.query;

import java.util.Map;

import com.kota.stratagem.persistence.entity.AbstractMonitoredEntity;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Remedy;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;

public class QueryAggregator {

	public static final String getMonitoringQuery(Class<? extends AbstractMonitoredEntity> c) {
		return MONITORING_PROPERTY_QUERY.replaceAll(ALIAS_WILDCARD, aliasMap.get(c));
	}

	public static final Map<Class<? extends AbstractMonitoredEntity>, String> aliasMap = PopulateAliasMap();

	private static Map<Class<? extends AbstractMonitoredEntity>, String> PopulateAliasMap() {

		aliasMap.put(Objective.class, OBJECTIVE_ALIAS);

		aliasMap.put(Project.class, PROJECT_ALIAS);

		aliasMap.put(Submodule.class, SUBMODULE_ALIAS);

		aliasMap.put(Task.class, TASK_ALIAS);

		aliasMap.put(Impediment.class, IMPEDIMENT_ALIAS);

		aliasMap.put(Remedy.class, REMEDY_ALIAS);

		return aliasMap;
	}

	public static final String OBJECTIVE_ALIAS = "o";
	public static final String PROJECT_ALIAS = "p";
	public static final String SUBMODULE_ALIAS = "sm";
	public static final String TASK_ALIAS = "t";
	public static final String IMPEDIMENT_ALIAS = "i";
	public static final String REMEDY_ALIAS = "r";

	private static final String ALIAS_WILDCARD = "[ALIAS]";
	private static final String MONITORING_PROPERTY_QUERY = " LEFT JOIN FETCH " + ALIAS_WILDCARD + ".creator LEFT JOIN FETCH " + ALIAS_WILDCARD + ".modifier ";

}
