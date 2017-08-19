package com.kota.stratagem.persistence.service.delegation;

public interface AppUserAssignmentService {

	Object create(Long entrustor, Long recipient, Long target);

	void delete(Long id);

}
