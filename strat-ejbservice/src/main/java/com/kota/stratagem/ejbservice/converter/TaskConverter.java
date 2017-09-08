package com.kota.stratagem.ejbservice.converter;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.entity.Task;

@Local
public interface TaskConverter {

	TaskRepresentor toElementary(Task task);

	TaskRepresentor toSimplified(Task task);

	TaskRepresentor toComplete(Task task);

	Set<TaskRepresentor> toElementary(Set<Task> tasks);

	Set<TaskRepresentor> toSimplified(Set<Task> tasks);

	Set<TaskRepresentor> toComplete(Set<Task> tasks);

}