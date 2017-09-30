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

import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.query.TeamTaskAssignmentQuery;

@Entity
@Table(name = "team_task_assignments")
@NamedQueries(value = { //
		@NamedQuery(name = TeamTaskAssignmentQuery.GET_BY_ID, query = "SELECT a FROM TeamTaskAssignment a LEFT JOIN a.recipient.members LEFT JOIN a.recipient.leader WHERE a.id=:"
				+ AssignmentParameter.ID),
		@NamedQuery(name = TeamTaskAssignmentQuery.REMOVE_BY_ID, query = "DELETE FROM TeamTaskAssignment a WHERE a.id=:" + AssignmentParameter.ID)
		//
})
@SequenceGenerator(name = "teamTaskAssignmentGenerator", sequenceName = "team_task_assignments_assignment_id_seq", allocationSize = 1)
public class TeamTaskAssignment extends AbstractTeamAssignment implements Serializable {

	private static final long serialVersionUID = -3305132249066347071L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamTaskAssignmentGenerator")
	@Column(name = "assignment_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = Task.class)
	@JoinColumn(name = "assignment_task", referencedColumnName = "task_id", nullable = false)
	private Task task;

	public TeamTaskAssignment() {
		super();
		this.creationDate = new Date();
	}

	public TeamTaskAssignment(Task task, Date creationDate) {
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
		return "TeamTaskAssignment [id=" + this.id + ", task=" + this.task + "]";
	}

}
