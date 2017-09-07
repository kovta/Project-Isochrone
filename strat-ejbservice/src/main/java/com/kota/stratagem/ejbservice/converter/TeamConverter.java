package com.kota.stratagem.ejbservice.converter;

import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.persistence.entity.Team;

public interface TeamConverter {

	TeamRepresentor to(Team team);

	TeamRepresentor toElementary(Team team);

	List<TeamRepresentor> to(List<Team> teams);

}
