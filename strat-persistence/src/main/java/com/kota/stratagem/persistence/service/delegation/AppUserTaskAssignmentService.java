package com.kota.stratagem.persistence.service.delegation;

import com.kota.stratagem.persistence.entity.AppUserTaskAssignment;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

public interface AppUserTaskAssignmentService {

	AppUserTaskAssignment create(Long entrustor, Long recipient, Long objective) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

}
