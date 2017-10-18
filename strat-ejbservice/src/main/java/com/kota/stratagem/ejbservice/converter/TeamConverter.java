package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.persistence.entity.Team;

@Local
public interface TeamConverter {

	TeamRepresentor toElementary(Team team);

	TeamRepresentor toSubSimplified(Team team);

	TeamRepresentor toSimplified(Team team);

	TeamRepresentor toSubComplete(Team team);

	TeamRepresentor toComplete(Team team);

	Set<TeamRepresentor> toElementary(Set<Team> teams);

	Set<TeamRepresentor> toSubSimplified(Set<Team> teams);

	Set<TeamRepresentor> toSimplified(Set<Team> teams);

	Set<TeamRepresentor> toSubComplete(Set<Team> teams);

	Set<TeamRepresentor> toComplete(Set<Team> teams);

}
