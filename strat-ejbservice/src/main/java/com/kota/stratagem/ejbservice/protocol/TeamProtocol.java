package com.kota.stratagem.ejbservice.protocol;

import java.util.List;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;

@Local
public interface TeamProtocol {

	TeamRepresentor getTeam(Long id) throws AdaptorException;

	List<TeamRepresentor> getAssignableTeams(ObjectiveRepresentor objective) throws AdaptorException;

	List<TeamRepresentor> getAssignableTeams(ProjectRepresentor project) throws AdaptorException;

	List<TeamRepresentor> getAssignableTeams(SubmoduleRepresentor submodule) throws AdaptorException;

	List<TeamRepresentor> getAssignableTeams(TaskRepresentor task) throws AdaptorException;

	List<TeamRepresentor> getAllTeams() throws AdaptorException;

	TeamRepresentor saveTeam(Long id, String name, String leader) throws AdaptorException;

	void removeTeam(Long id) throws AdaptorException;

	void saveTeamMemberships(Long id, String[] members) throws AdaptorException;

	void removeTeamMembership(Long id, String member) throws AdaptorException;

}
