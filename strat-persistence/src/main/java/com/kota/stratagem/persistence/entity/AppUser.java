package com.kota.stratagem.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kota.stratagem.persistence.entity.trunk.Role;
import com.kota.stratagem.persistence.parameter.AppUserParameter;
import com.kota.stratagem.persistence.query.AppUserQuery;

@Entity
@Table(name = "app_users")
@NamedQueries(value = { //
		@NamedQuery(name = AppUserQuery.COUNT_BY_ID, query = "SELECT COUNT(u) FROM AppUser u WHERE u.id=:" + AppUserParameter.ID),
		@NamedQuery(name = AppUserQuery.GET_BY_ID, query = "SELECT u FROM AppUser u WHERE u.id=:" + AppUserParameter.ID),
		@NamedQuery(name = AppUserQuery.GET_BY_USERNAME, query = "SELECT u FROM AppUser u WHERE u.name=:" + AppUserParameter.USERNAME),
		@NamedQuery(name = AppUserQuery.GET_BY_ID_COMPLETE, query = "SELECT u FROM AppUser u LEFT JOIN FETCH u.objectives o LEFT JOIN FETCH u.projects p LEFT JOIN FETCH u.submodules sm LEFT JOIN FETCH u.tasks t WHERE u.id=:"
				+ AppUserParameter.ID),
		@NamedQuery(name = AppUserQuery.GET_BY_USERNAME_COMPLETE, query = "SELECT u FROM AppUser u LEFT JOIN FETCH u.objectives o LEFT JOIN FETCH u.projects p LEFT JOIN FETCH u.submodules sm LEFT JOIN FETCH u.tasks t WHERE u.name=:"
				+ AppUserParameter.USERNAME),
		@NamedQuery(name = AppUserQuery.GET_ALL_BY_ROLE, query = "SELECT u FROM AppUser u WHERE u.role=:" + AppUserParameter.ROLE + " ORDER BY u.name"),
		@NamedQuery(name = AppUserQuery.GET_ALL_USERS, query = "SELECT u FROM AppUser u ORDER BY u.name"),
		@NamedQuery(name = AppUserQuery.REMOVE_BY_ID, query = "DELETE FROM AppUser u WHERE u.id=:" + AppUserParameter.ID)
		//
})
@SequenceGenerator(name = "appUserGenerator", sequenceName = "app_users_user_id_seq", allocationSize = 1)
public class AppUser implements Serializable {

	private static final long serialVersionUID = -2320296399311475905L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appUserGenerator")
	@Column(name = "user_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@Column(name = "user_name", nullable = false)
	private String name;

	@Column(name = "user_password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "user_email", nullable = false)
	private String email;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "user_role", nullable = false)
	private Role role;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "user_registration_date", nullable = false)
	private Date registrationDate;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "user_account_modifier", referencedColumnName = "user_id", nullable = true)
	private AppUser accountModifier;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "user_account_modification_date", nullable = false)
	private Date acountModificationDate;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUserObjectiveAssignment.class, mappedBy = "recipient")
	private Set<AppUserObjectiveAssignment> objectives;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUserProjectAssignment.class, mappedBy = "recipient")
	private Set<AppUserProjectAssignment> projects;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUserSubmoduleAssignment.class, mappedBy = "recipient")
	private Set<AppUserSubmoduleAssignment> submodules;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AppUserTaskAssignment.class, mappedBy = "recipient")
	private Set<AppUserTaskAssignment> tasks;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Impediment.class, mappedBy = "reporter")
	private Set<Impediment> reportedImpediments;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Impediment.class, mappedBy = "processor")
	private Set<Impediment> processedImpediments;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Team.class, mappedBy = "leader")
	private Set<Team> supervisedTeams;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Team.class)
	@JoinTable(name = "team_members", joinColumns = @JoinColumn(name = "team_member_user_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "team_member_team_id", nullable = false))
	private Set<Team> teamMemberships;

	public AppUser() {
		this.objectives = new HashSet<>();
		this.projects = new HashSet<>();
		this.submodules = new HashSet<>();
		this.tasks = new HashSet<>();
		this.reportedImpediments = new HashSet<>();
		this.processedImpediments = new HashSet<>();
		this.supervisedTeams = new HashSet<>();
		this.teamMemberships = new HashSet<>();
	}

	public AppUser(Long id, String name, String passwordHash, String email, Role role, Date registrationDate, AppUser accountModifier,
			Date acountModificationDate) {
		super();
		this.id = id;
		this.name = name;
		this.passwordHash = passwordHash;
		this.email = email;
		this.role = role;
		this.registrationDate = registrationDate;
		this.accountModifier = accountModifier;
		this.acountModificationDate = acountModificationDate;
	}

	public AppUser(String name, String passwordHash, String email, Role role, Date registrationDate, AppUser accountModifier, Date acountModificationDate) {
		super();
		this.name = name;
		this.passwordHash = passwordHash;
		this.email = email;
		this.role = role;
		this.registrationDate = registrationDate;
		this.accountModifier = accountModifier;
		this.acountModificationDate = acountModificationDate;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public AppUser getAccountModifier() {
		return this.accountModifier;
	}

	public void setAccountModifier(AppUser accountModifier) {
		this.accountModifier = accountModifier;
	}

	public Date getAcountModificationDate() {
		return this.acountModificationDate;
	}

	public void setAcountModificationDate(Date acountModificationDate) {
		this.acountModificationDate = acountModificationDate;
	}

	public Set<AppUserObjectiveAssignment> getObjectives() {
		return this.objectives;
	}

	public void setObjectives(Set<AppUserObjectiveAssignment> objectives) {
		this.objectives = objectives;
	}

	public Set<AppUserProjectAssignment> getProjects() {
		return this.projects;
	}

	public void setProjects(Set<AppUserProjectAssignment> projects) {
		this.projects = projects;
	}

	public Set<AppUserSubmoduleAssignment> getSubmodules() {
		return this.submodules;
	}

	public void setSubmodules(Set<AppUserSubmoduleAssignment> submodules) {
		this.submodules = submodules;
	}

	public Set<AppUserTaskAssignment> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<AppUserTaskAssignment> tasks) {
		this.tasks = tasks;
	}

	public Set<Impediment> getReportedImpediments() {
		return this.reportedImpediments;
	}

	public void setReportedImpediments(Set<Impediment> reportedImpediments) {
		this.reportedImpediments = reportedImpediments;
	}

	public Set<Impediment> getProcessedImpediments() {
		return this.processedImpediments;
	}

	public void setProcessedImpediments(Set<Impediment> processedImpediments) {
		this.processedImpediments = processedImpediments;
	}

	public Set<Team> getSupervisedTeams() {
		return this.supervisedTeams;
	}

	public void setSupervisedTeams(Set<Team> supervisedTeams) {
		this.supervisedTeams = supervisedTeams;
	}

	public Set<Team> getTeamMemberships() {
		return this.teamMemberships;
	}

	public void setTeamMemberships(Set<Team> teamMemberships) {
		this.teamMemberships = teamMemberships;
	}

	@Override
	public String toString() {
		return "AppUser [id=" + this.id + ", name=" + this.name + ", passwordHash=" + this.passwordHash + ", email=" + this.email + ", role=" + this.role
				+ ", registrationDate=" + this.registrationDate + ", acountModificationDate=" + this.acountModificationDate + "]";
	}

}
