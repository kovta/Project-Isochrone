package com.kota.stratagem.ejbserviceclient.domain;

public class ProjectCriteria {
	private String id;
	private ProjectStatusRepresentor status;

	public ProjectCriteria() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProjectStatusRepresentor getStatus() {
		return status;
	}

	public void setStatus(ProjectStatusRepresentor status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ProjectCriteria [id=" + id + ", status=" + status + "]";
	}

}
