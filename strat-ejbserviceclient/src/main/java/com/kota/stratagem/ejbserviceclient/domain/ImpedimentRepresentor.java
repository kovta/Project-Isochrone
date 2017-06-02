package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpedimentRepresentor implements Serializable {

	private static final long serialVersionUID = 3043909154399999429L;

	private Long id;
	private final String name;
	private final String description;
	private final PriorityRepresentor priority;
	private final ImpedimentStatusRepresentor status;
	private final Date reportDate;
	private final AppUserRepresentor reporter;
	private final AppUserRepresentor processor;
	private final List<RemedyRepresentor> remedies;
	private final ProjectRepresentor project;
	private final TaskRepresentor task;

	public ImpedimentRepresentor() {
		this(null, "", "", PriorityRepresentor.MEDIUM, ImpedimentStatusRepresentor.OPEN, new Date(), null, null, null, null);
	}

	public ImpedimentRepresentor(Long id, String name, String description, PriorityRepresentor priority, ImpedimentStatusRepresentor status, Date reportDate,
			AppUserRepresentor reporter, AppUserRepresentor processor, ProjectRepresentor project, TaskRepresentor task) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.reportDate = reportDate;
		this.reporter = reporter;
		this.processor = processor;
		this.remedies = new ArrayList<>();
		this.project = project;
		this.task = task;
	}

	public ImpedimentRepresentor(String name, String description, PriorityRepresentor priority, ImpedimentStatusRepresentor status, Date reportDate,
			AppUserRepresentor reporter, AppUserRepresentor processor, ProjectRepresentor project, TaskRepresentor task) {
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.reportDate = reportDate;
		this.reporter = reporter;
		this.processor = processor;
		this.remedies = new ArrayList<>();
		this.project = project;
		this.task = task;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public PriorityRepresentor getPriority() {
		return this.priority;
	}

	public ImpedimentStatusRepresentor getStatus() {
		return this.status;
	}

	public Date getReportDate() {
		return this.reportDate;
	}

	public AppUserRepresentor getReporter() {
		return this.reporter;
	}

	public AppUserRepresentor getProcessor() {
		return this.processor;
	}

	public List<RemedyRepresentor> getRemedies() {
		return this.remedies;
	}

	public void addRemedy(RemedyRepresentor remedy) {
		this.remedies.add(remedy);
	}

	public ProjectRepresentor getProject() {
		return this.project;
	}

	public TaskRepresentor getTask() {
		return this.task;
	}

	@Override
	public String toString() {
		return "ImpedimentRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority
				+ ", status=" + this.status + ", reportDate=" + this.reportDate + ", reporter=" + this.reporter + ", processor=" + this.processor
				+ ", remedies=" + this.remedies + ", project=" + this.project + ", task=" + this.task + "]";
	}

}
