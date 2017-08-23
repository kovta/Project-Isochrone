package com.kota.stratagem.persistence.service.delegation;

public interface AppUserAssignmentService {

	void create(Long entrustor, Long recipient, Long target);

	void delete(Long id);

}
