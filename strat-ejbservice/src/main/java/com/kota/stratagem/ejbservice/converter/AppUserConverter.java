package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;

@Local
public interface AppUserConverter {

	AppUserRepresentor toElementary(AppUser user);

	AppUserRepresentor toSimplified(AppUser user);

	AppUserRepresentor toComplete(AppUser user);

	Set<AppUserRepresentor> toElementary(Set<AppUser> users);

	Set<AppUserRepresentor> toComplete(Set<AppUser> users);

}
