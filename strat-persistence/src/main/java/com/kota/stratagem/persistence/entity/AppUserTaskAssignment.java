package com.kota.stratagem.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kota.stratagem.persistence.parameter.AppUserParameter;
import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.query.AppUserTaskAssignmentQuery;

@Entity
@Table(name = "user_task_assignments")
@NamedQueries(value = { //
		@NamedQuery(name = AppUserTaskAssignmentQuery.GET_BY_ID, query = "SELECT a FROM AppUserTaskAssignment a WHERE a.id=:" + AssignmentParameter.ID),
		@NamedQuery(name = AppUserTaskAssignmentQuery.REMOVE_BY_ID, query = "DELETE FROM AppUserTaskAssignment a WHERE a.id=:" + AssignmentParameter.ID),
		@NamedQuery(name = AppUserTaskAssignmentQuery.GET_ALL_BY_APP_USER_ID, query = "Select a FROM AppUserTaskAssignment a WHERE a.recipient.id=:"
				+ AppUserParameter.ID)
		//
})
@SequenceGenerator(name = "userTaskAssignmentGenerator", sequenceName = "user_task_assignments_assignment_id_seq", allocationSize = 1)
public class AppUserTaskAssignment extends AbstractAppUserAssignment implements Serializable {

	private static final long serialVersionUID = -7689042201929946465L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userTaskAssignmentGenerator")
	@Column(name = "assignment_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = Task.class)
	@JoinColumn(name = "assignment_task", referencedColumnName = "task_id", nullable = false)
	private Task task;

	public AppUserTaskAssignment() {
		super();
		this.creationDate = new Date();
	}

	public AppUserTaskAssignment(Task task, Date creationDate) {
		super();
		this.task = task;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "UserTaskAssignment [id=" + this.id + ", task=" + this.task + "]";
	}

}
