package com.kota.stratagem.persistence.service.delegation;

import com.kota.stratagem.persistence.entity.AppUserProjectAssignment;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

public interface AppUserProjectAssignmentService {

	AppUserProjectAssignment create(Long entrustor, Long recipient, Long objective) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

}
