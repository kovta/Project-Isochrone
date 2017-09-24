package com.kota.stratagem.persistence.service;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;

@Local
public interface TeamService {

	Team create(String name, String leader, String creator);

	Team readElementary(Long id);

	Team readWiithLeaderAndMembers(Long id);

	Team readComplete(Long id);

	Set<Team> readAll();

	Team update(Long id, String name, String leader, String modifier);

	void delete(Long id) throws CoherentPersistenceServiceException;

	boolean exists(Long id);

	void createMembership(Long id, String member, Long operator);

	void deleteMembership(Long id, String member, Long operator);

}
