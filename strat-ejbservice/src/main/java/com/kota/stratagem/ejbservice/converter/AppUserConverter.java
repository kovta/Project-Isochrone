package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;

@Local
public interface AppUserConverter {

	AppUserRepresentor to(AppUser user);

	Set<AppUserRepresentor> to(Set<AppUser> users);

}
