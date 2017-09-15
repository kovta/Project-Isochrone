package com.kota.stratagem.persistence.context;

import com.kota.stratagem.persistence.util.Constants;

public class PersistenceServiceConfiguration {

	public static final String PERSISTENCE_UNIT_NAME = "strat-persistence-unit";

	private static final String BEAN_DISTINGUISHER = "ejb";
	private static final String SERVICE_DISTINGUISHER = "Service";

	public static final String APP_USER_SERVICE_SIGNATURE = PersistenceServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.APP_USER_ATTRIBUTE_DATA_LABEL + PersistenceServiceConfiguration.SERVICE_DISTINGUISHER;
	public static final String TEAM_SERVICE_SIGNATURE = PersistenceServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.TEAM_ATTRIBUTE_DATA_LABEL + PersistenceServiceConfiguration.SERVICE_DISTINGUISHER;
	public static final String OBJECTIVE_SERVICE_SIGNATURE = PersistenceServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.OBJECTIVE_ATTRIBUTE_DATA_LABEL + PersistenceServiceConfiguration.SERVICE_DISTINGUISHER;
	public static final String PROJECT_SERVICE_SIGNATURE = PersistenceServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.PROJECT_ATTRIBUTE_DATA_LABEL + PersistenceServiceConfiguration.SERVICE_DISTINGUISHER;
	public static final String SUBMODULE_SERVICE_SIGNATURE = PersistenceServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.SUBMODULE_ATTRIBUTE_DATA_LABEL + PersistenceServiceConfiguration.SERVICE_DISTINGUISHER;
	public static final String TASK_SERVICE_SIGNATURE = PersistenceServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.TASK_ATTRIBUTE_DATA_LABEL + PersistenceServiceConfiguration.SERVICE_DISTINGUISHER;
	public static final String APP_USER_ASSIGNMENT_SERVICE_SIGNATURE = PersistenceServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.APP_USER_ATTRIBUTE_DATA_LABEL + Constants.ASSIGNMENT_DATA_LABEL + PersistenceServiceConfiguration.SERVICE_DISTINGUISHER;
	public static final String IMPEDIMENT_SERVICE_SIGNATURE = PersistenceServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.IMPEDIMENT_ATTRIBUTE_DATA_LABEL + PersistenceServiceConfiguration.SERVICE_DISTINGUISHER;
	public static final String REMEDY_SERVICE_SIGNATURE = PersistenceServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.REMEDY_ATTRIBUTE_DATA_LABEL + PersistenceServiceConfiguration.SERVICE_DISTINGUISHER;
	public static final String NOTIFICATION_SERVICE_SIGNATURE = PersistenceServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.NOTIFICATION_ATTRIBUTE_DATA_LABEL + PersistenceServiceConfiguration.SERVICE_DISTINGUISHER;
}
