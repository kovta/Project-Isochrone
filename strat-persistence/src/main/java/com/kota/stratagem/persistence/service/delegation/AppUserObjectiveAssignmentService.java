package com.kota.stratagem.persistence.service.delegation;

import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

public interface AppUserObjectiveAssignmentService {

	AppUserObjectiveAssignment create(Long entrustor, Long recipient, Long objective) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

}
