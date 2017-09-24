package com.kota.stratagem.ejbservice.context;

import com.kota.stratagem.persistence.util.Constants;

public class EJBServiceConfiguration {

	private static final String BEAN_DISTINGUISHER = "ejb";
	private static final String PROTOCOL_DISTINGUISHER = "Protocol";

	public static final String APP_USER_PROTOCOL_SIGNATURE = EJBServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.APP_USER_ATTRIBUTE_DATA_LABEL + EJBServiceConfiguration.PROTOCOL_DISTINGUISHER;
	public static final String TEAM_PROTOCOL_SIGNATURE = EJBServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.TEAM_ATTRIBUTE_DATA_LABEL + EJBServiceConfiguration.PROTOCOL_DISTINGUISHER;
	public static final String OBJECTIVE_PROTOCOL_SIGNATURE = EJBServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.OBJECTIVE_ATTRIBUTE_DATA_LABEL + EJBServiceConfiguration.PROTOCOL_DISTINGUISHER;
	public static final String PROJECT_PROTOCOL_SIGNATURE = EJBServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.PROJECT_ATTRIBUTE_DATA_LABEL + EJBServiceConfiguration.PROTOCOL_DISTINGUISHER;
	public static final String SUBMODULE_PROTOCOL_SIGNATURE = EJBServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.SUBMODULE_ATTRIBUTE_DATA_LABEL + EJBServiceConfiguration.PROTOCOL_DISTINGUISHER;
	public static final String TASK_PROTOCOL_SIGNATURE = EJBServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.TASK_ATTRIBUTE_DATA_LABEL + EJBServiceConfiguration.PROTOCOL_DISTINGUISHER;
	public static final String APP_USER_ASSIGNMENT_PROTOCOL_SIGNATURE = EJBServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.APP_USER_ATTRIBUTE_DATA_LABEL + Constants.ASSIGNMENT_DATA_LABEL + EJBServiceConfiguration.PROTOCOL_DISTINGUISHER;
	public static final String TEAM_ASSIGNMENT_PROTOCOL_SIGNATURE = EJBServiceConfiguration.BEAN_DISTINGUISHER + Constants.CONCERN_ASSOCIATOR
			+ Constants.TEAM_ATTRIBUTE_DATA_LABEL + Constants.ASSIGNMENT_DATA_LABEL + EJBServiceConfiguration.PROTOCOL_DISTINGUISHER;

}
