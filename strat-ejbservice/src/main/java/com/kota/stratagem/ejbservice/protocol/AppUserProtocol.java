package com.kota.stratagem.ejbservice.protocol;

import java.util.List;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;

@Local
public interface AppUserProtocol {

	int getAppUserNewNotificationCount(String username) throws AdaptorException;

	int getAppUserImageSelector(String username) throws AdaptorException;

	AppUserRepresentor getOperator() throws AdaptorException;

	AppUserRepresentor getAppUser(Long id) throws AdaptorException;

	AppUserRepresentor getAppUser(String username) throws AdaptorException;

	List<List<AppUserRepresentor>> getAssignableAppUserClusters(ObjectiveRepresentor objective) throws AdaptorException;

	List<List<AppUserRepresentor>> getAssignableAppUserClusters(ProjectRepresentor project) throws AdaptorException;

	List<List<AppUserRepresentor>> getAssignableAppUserClusters(SubmoduleRepresentor submodule) throws AdaptorException;

	List<List<AppUserRepresentor>> getAssignableAppUserClusters(TaskRepresentor task) throws AdaptorException;

	List<List<AppUserRepresentor>> getAssignableAppUserClusters(TeamRepresentor team) throws AdaptorException;

	List<AppUserRepresentor> getAssignableAppUsers() throws AdaptorException;

	List<AppUserRepresentor> getAllAppUsers() throws AdaptorException;

	AppUserRepresentor saveAppUser(Long id, String name, String password, String email, RoleRepresentor role, String operator) throws AdaptorException;

	void removeAppUser(Long id) throws AdaptorException;

	boolean isOperatorAccount(AppUserRepresentor appUser) throws AdaptorException;

	boolean isSubordinateUser(AppUserRepresentor appUser) throws AdaptorException;

	void equalizeViewedNotifications(AppUserRepresentor operator) throws AdaptorException;

	void saveImageSelector(int imageSelector) throws AdaptorException;

}
