package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.persistence.entity.Project;

@Local
public interface ProjectConverter {

	ProjectRepresentor to(Project project);

	ProjectRepresentor toElementary(Project project);

	Set<ProjectRepresentor> to(Set<Project> projects);

}