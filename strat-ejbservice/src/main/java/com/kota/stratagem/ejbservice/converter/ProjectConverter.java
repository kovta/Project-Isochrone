package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.persistence.entity.Project;

@Local
public interface ProjectConverter {

	ProjectRepresentor toElementary(Project project);

	ProjectRepresentor toSimplified(Project project);

	ProjectRepresentor toComplete(Project project);

	Set<ProjectRepresentor> toElementary(Set<Project> projects);

	Set<ProjectRepresentor> toSimplified(Set<Project> projects);

	Set<ProjectRepresentor> toComplete(Set<Project> projects);

}