package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.persistence.entity.Team;

@Local
public interface TeamConverter {

	TeamRepresentor toElementary(Team team);

	TeamRepresentor toSimplified(Team team);

	TeamRepresentor toComplete(Team team);

	Set<TeamRepresentor> toElementary(Set<Team> teams);

	Set<TeamRepresentor> toSimplified(Set<Team> teams);

	Set<TeamRepresentor> toComplete(Set<Team> teams);

}
