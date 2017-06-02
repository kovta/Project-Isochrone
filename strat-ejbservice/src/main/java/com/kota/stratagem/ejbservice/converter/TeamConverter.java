package com.kota.stratagem.ejbservice.converter;

import java.util.List;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.persistence.entity.Team;

@Local
public interface TeamConverter {

	TeamRepresentor to(Team team);

	List<TeamRepresentor> to(List<Team> teams);

}
