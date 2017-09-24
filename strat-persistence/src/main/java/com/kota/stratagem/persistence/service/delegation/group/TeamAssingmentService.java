package com.kota.stratagem.persistence.service.delegation.group;

import com.kota.stratagem.persistence.entity.AbstractTeamAssignment;

public interface TeamAssingmentService {

	AbstractTeamAssignment create(Long entrustor, Long recipient, Long target);

	AbstractTeamAssignment read(Long id);

	void delete(Long id);

}
