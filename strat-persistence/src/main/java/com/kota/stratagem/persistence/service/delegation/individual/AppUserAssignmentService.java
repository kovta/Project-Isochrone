package com.kota.stratagem.persistence.service.delegation.individual;

public interface AppUserAssignmentService {

	void create(Long entrustor, Long recipient, Long target);

	void delete(Long id);

}
