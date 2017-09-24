package com.kota.stratagem.persistence.service.delegation.individual;

import com.kota.stratagem.persistence.exception.PersistenceServiceException;

public interface AppUserTaskAssignmentService {

	void create(Long entrustor, Long recipient, Long objective) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

}
