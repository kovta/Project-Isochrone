package com.kota.stratagem.security.util;

import java.util.HashMap;
import java.util.Map;

import com.kota.stratagem.security.domain.RestrictionLevel;

public class AuthorizationLevels {

	private final static Map<RestrictionLevel, String[]> authorityRestrictions = PopulateAuthorityRestrictions();

	private static Map<RestrictionLevel, String[]> PopulateAuthorityRestrictions() {

		Map<RestrictionLevel, String[]> restrictions = new HashMap();

		restrictions.put(RestrictionLevel.SYSTEM_ADMINISTRATOR_LEVEL, Authorizations.SYSTEM_ADMINISTRATOR_LEVEL);

		restrictions.put(RestrictionLevel.CENTRAL_MANAGER_LEVEL, Authorizations.CENTRAL_MANAGER_LEVEL);

		restrictions.put(RestrictionLevel.DEPARTMENT_MANAGER_LEVEL, Authorizations.DEPARTMENT_MANAGER_LEVEL);

		restrictions.put(RestrictionLevel.GENERAL_MANAGER_LEVEL, Authorizations.GENERAL_MANAGER_LEVEL);

		restrictions.put(RestrictionLevel.GENERAL_USER_LEVEL, Authorizations.GENERAL_USER_LEVEL);

		return restrictions;

	}

	public static Map<RestrictionLevel, String[]> getAuthorityRestrictions() {
		return authorityRestrictions;
	}

}
