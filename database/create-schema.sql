CREATE TABLE priorities (
	priority_id SERIAL NOT NULL,
	priority_name CHARACTER VARYING(100) NOT NULL,
	CONSTRAINT PK_PRIORITY_ID PRIMARY KEY (priority_id) 
);
CREATE TABLE mission_stages (
	stage_id SERIAL NOT NULL,
	stage_name CHARACTER VARYING(100) NOT NULL,
	CONSTRAINT PK_MISSION_STAGE_ID PRIMARY KEY (stage_id)
);

-- ###########################################################################################

CREATE TABLE roles (
	role_id SERIAL NOT NULL,
	role_name CHARACTER VARYING(100) NOT NULL,
	CONSTRAINT PK_ROLE_ID PRIMARY KEY (role_id) 
);
CREATE TABLE app_users (
	user_id SERIAL NOT NULL,
	user_name CHARACTER VARYING(100) NOT NULL,
	user_password_hash CHARACTER VARYING(500) NOT NULL,
	user_email CHARACTER VARYING(100) NULL,
	user_role INTEGER NOT NULL,
	user_registration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	user_account_modifier INTEGER NULL,
	user_account_modification_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	user_notification_view_count INTEGER NULL,
	user_image_selector INTEGER NULL,
	CONSTRAINT PK_USER_ID PRIMARY KEY (user_id),
	CONSTRAINT FK_USER_ROLE FOREIGN KEY (user_role)
		REFERENCES roles (role_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_USER_ACCOUNT_MODIFIER FOREIGN KEY (user_account_modifier)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE UNIQUE INDEX UI_USER_NAME ON app_users USING btree (user_name);
CREATE TABLE authorizations (
	authorization_id SERIAL NOT NULL,
	authorization_user_id INTEGER NOT NULL,
	authorization_role_id INTEGER NOT NULL,
	CONSTRAINT PK_AUTHORIZATION_ID PRIMARY KEY (authorization_id),
	CONSTRAINT FK_AUTHORIZATION_USER FOREIGN KEY (authorization_user_id)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_AUTHORIZATION_ROLE FOREIGN KEY (authorization_role_id)
		REFERENCES roles (role_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE teams (
	team_id SERIAL NOT NULL,
	team_name CHARACTER VARYING(100) NOT NULL,
	team_leader INTEGER NOT NULL,
	team_creator INTEGER NOT NULL,
	team_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	team_modifier INTEGER NOT NULL,
	team_modification_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_TEAM_ID PRIMARY KEY (team_id),
	CONSTRAINT FK_TEAM_LEADER FOREIGN KEY (team_leader)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_CREATOR FOREIGN KEY (team_creator)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_MODIFIER FOREIGN KEY (team_modifier)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE team_members (
	team_member_id SERIAL NOT NULL,
	team_member_team_id INTEGER NOT NULL,
	team_member_user_id INTEGER NOT NULL,
	CONSTRAINT PK_TEAM_MEMBER_ID PRIMARY KEY (team_member_id),
	CONSTRAINT FK_TEAM_MEMBER_TEAM_ID FOREIGN KEY (team_member_user_id)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_MEMBER_USER_ID FOREIGN KEY (team_member_team_id)
		REFERENCES teams (team_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- ###########################################################################################

CREATE TABLE objective_statuses (
	status_id SERIAL NOT NULL,
	status_name CHARACTER VARYING(100) NOT NULL,
	CONSTRAINT PK_OBJECTIVE_STATUS_ID PRIMARY KEY (status_id)
);
CREATE TABLE objectives (
	objective_id SERIAL NOT NULL,
	objective_name CHARACTER VARYING(100) NOT NULL,
	objective_description CHARACTER VARYING(1000) NULL,
	objective_priority INTEGER NOT NULL,
	objective_status_id INTEGER NOT NULL,
	objective_deadline TIMESTAMP WITHOUT TIME ZONE NULL,
	objective_confidentiality BOOLEAN NOT NULL,
	objective_creator INTEGER NOT NULL,
	objective_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	objective_modifier INTEGER NOT NULL,
	objective_modification_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_OBJECTIVE_ID PRIMARY KEY (objective_id),
	CONSTRAINT FK_OBJECTIVE_STATUS_ID FOREIGN KEY (objective_status_id)
		REFERENCES objective_statuses (status_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_OBJECTIVE_CREATOR FOREIGN KEY (objective_creator)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_OBJECTIVE_MODIFIER FOREIGN KEY (objective_modifier)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE objective_missions (
	mission_id SERIAL NOT NULL,
	mission_name CHARACTER VARYING(100) NOT NULL,
	mission_description CHARACTER VARYING(2000) NULL,
	mission_objective INTEGER NOT NULL,
	mission_stage_id INTEGER NOT NULL,
	CONSTRAINT PK_OBJECTIVE_MISSION_ID PRIMARY KEY (mission_id),
	CONSTRAINT FK_OBJECTIVE_MISSION_OBJECTIVE FOREIGN KEY (mission_objective)
		REFERENCES objectives (objective_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_OBJECTIVE_MISSION_STAGE FOREIGN KEY (mission_stage_id)
		REFERENCES mission_stages (stage_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE objective_supervisors (
	objective_supervisor_id SERIAL NOT NULL,
	objective_supervisor_objective_id INTEGER NOT NULL,
	objective_supervisor_user_id INTEGER NOT NULL,
	CONSTRAINT PK_OBJECTIVE_SUPERVISOR_ID PRIMARY KEY (objective_supervisor_id),
	CONSTRAINT FK_OBJECTIVE_SUPERVISOR_OBJECTIVE FOREIGN KEY (objective_supervisor_objective_id)
	  REFERENCES objectives (objective_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_OBJECTIVE_SUPERVISOR_USER FOREIGN KEY (objective_supervisor_user_id)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE objective_status_alterations (
	alteration_id SERIAL NOT NULL,
	alteration_objective_id INTEGER NOT NULL,
	alteration_status_id INTEGER NOT NULL,
	alteration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_OBJECTIVE_ALTERATION_ID PRIMARY KEY (alteration_id),
	CONSTRAINT FK_OBJECTIVE_ALTERATION_STATUS_ID FOREIGN KEY (alteration_status_id)
		REFERENCES objective_statuses (status_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_OBJECTIVE_ALTERATION_OBJECTIVE_ID FOREIGN KEY (alteration_objective_id)
		REFERENCES objectives (objective_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE objective_appointments (
	appointment_id SERIAL NOT NULL,
	appointment_user_id INTEGER NOT NULL,
	appointment_objective_id INTEGER NOT NULL,
	CONSTRAINT PK_OBJECTIVE_APPOINTMENT_ID PRIMARY KEY (appointment_id),
	CONSTRAINT FK_OBJECTIVE_APPOINTMENT_OBJECTIVE_ID FOREIGN KEY (appointment_objective_id)
		REFERENCES objectives (objective_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_OBJECTIVE_APPOINTMENT_USER_ID FOREIGN KEY (appointment_user_id)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- ###########################################################################################

CREATE TABLE project_statuses (
	status_id INTEGER NOT NULL,
	status_name CHARACTER VARYING(100) NOT NULL,
	CONSTRAINT PK_PROJECT_STATUS_ID PRIMARY KEY (status_id)
);
CREATE TABLE projects (
	project_id SERIAL NOT NULL,
	project_name CHARACTER VARYING(100) NOT NULL,
	project_description CHARACTER VARYING(1000) NULL,
	project_status_id INTEGER NOT NULL,
	project_deadline TIMESTAMP WITHOUT TIME ZONE NULL,
	project_confidentiality BOOLEAN NOT NULL,
	project_creator INTEGER NOT NULL,
	project_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	project_modifier INTEGER NOT NULL,
	project_modification_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_PROJECT_ID PRIMARY KEY (project_id),
	CONSTRAINT FK_PROJECT_STATUS FOREIGN KEY (project_status_id)
		REFERENCES project_statuses (status_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_PROJECT_CREATOR FOREIGN KEY (project_creator)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_PROJECT_MODIFIER FOREIGN KEY (project_modifier)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE project_missions (
	mission_id SERIAL NOT NULL,
	mission_name CHARACTER VARYING(100) NOT NULL,
	mission_description CHARACTER VARYING(2000) NULL,
	mission_project INTEGER NOT NULL,
	mission_stage_id INTEGER NOT NULL,
	CONSTRAINT PK_PROJECT_MISSION_ID PRIMARY KEY (mission_id),
	CONSTRAINT FK_PROJECT_MISSION_PROJECT FOREIGN KEY (mission_project)
	  REFERENCES projects (project_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_PROJECT_MISSION_STAGE FOREIGN KEY (mission_stage_id)
	  REFERENCES mission_stages (stage_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE project_managers (
	project_manager_id SERIAL NOT NULL,
	project_manager_project_id INTEGER NOT NULL,
	project_manager_user_id INTEGER NOT NULL,
	CONSTRAINT PK_PROJECT_MANAGER_ID PRIMARY KEY (project_manager_id),
	CONSTRAINT FK_PROJECT_MANAGER_PROJECT FOREIGN KEY (project_manager_project_id)
	  REFERENCES projects (project_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_PROJECT_MANAGER_USER FOREIGN KEY (project_manager_user_id)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE product_owners (
	product_owner_id SERIAL NOT NULL,
	product_owner_project_id INTEGER NOT NULL,
	product_owner_user_id INTEGER NOT NULL,
	CONSTRAINT PK_PRODUCT_OWNER_ID PRIMARY KEY (product_owner_id),
	CONSTRAINT FK_PRODUCT_OWNER_PROJECT FOREIGN KEY (product_owner_project_id)
	  REFERENCES projects (project_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_PRODUCT_OWNER_USER FOREIGN KEY (product_owner_user_id)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE project_status_alterations (
	alteration_id SERIAL NOT NULL,
	alteration_project_id INTEGER NOT NULL,
	alteration_status_id INTEGER NOT NULL,
	alteration_user_id INTEGER NOT NULL,
	alteration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_PROJECT_ALTERATION_ID PRIMARY KEY (alteration_id),
	CONSTRAINT FK_PROJECT_ALTERATION_STATUS_ID FOREIGN KEY (alteration_status_id)
	  REFERENCES project_statuses (status_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_PROJECT_ALTERATION_PROJECT_ID FOREIGN KEY (alteration_project_id)
	  REFERENCES projects (project_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_PROJECT_ALTERATION_USER_ID FOREIGN KEY (alteration_user_id)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE objective_projects (
	objective_project_id SERIAL NOT NULL,
	objective_project_objective INTEGER NOT NULL,
	objective_project_project INTEGER NOT NULL,
	CONSTRAINT PK_OBJECTIVE_PROJECT_ID PRIMARY KEY (objective_project_id),
	CONSTRAINT FK_OBJECTIVE_PROJECT_OBJECTIVE FOREIGN KEY (objective_project_objective)
	  REFERENCES objectives (objective_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_OBJECTIVE_PROJECT_PROJECT FOREIGN KEY (objective_project_project)
	  REFERENCES projects (project_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- ###########################################################################################

CREATE TABLE submodules (
	submodule_id SERIAL NOT NULL,
	submodule_name CHARACTER VARYING(100) NOT NULL,
	submodule_description CHARACTER VARYING(1000) NULL,
	submodule_deadline TIMESTAMP WITHOUT TIME ZONE NULL,
	submodule_creator INTEGER NOT NULL,
	submodule_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	submodule_modifier INTEGER NOT NULL,
	submodule_modification_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_SUBMODULE_ID PRIMARY KEY (submodule_id),
	CONSTRAINT FK_SUBMODULE_CREATOR FOREIGN KEY (submodule_creator)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_SUBMODULE_MODIFIER FOREIGN KEY (submodule_modifier)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE project_submodules (
	project_submodule_id SERIAL NOT NULL,
	project_submodule_project INTEGER NOT NULL,
	project_submodule_submodule INTEGER NOT NULL,
	CONSTRAINT PK_PROJECT_SUBMODULE_ID PRIMARY KEY (project_submodule_id),
	CONSTRAINT FK_PROJECT_SUBMODULE_PROJECT FOREIGN KEY (project_submodule_project)
	  REFERENCES projects (project_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_PROJECT_SUBMODULE_SUBMODULE FOREIGN KEY (project_submodule_submodule)
	  REFERENCES submodules (submodule_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE submodule_dependencies (
	dependency_id SERIAL NOT NULL,
	dependency_satiator INTEGER NOT NULL,
	dependency_maintainer INTEGER NOT NULL,
	CONSTRAINT PK_SUBMODULE_DEPENDENCY_ID PRIMARY KEY (dependency_id),
	CONSTRAINT FK_SUBMODULE_DEPENDENCY_SATIATOR FOREIGN KEY (dependency_satiator)
	  REFERENCES submodules (submodule_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE CASCADE,
	CONSTRAINT FK_SUBMODULE_DEPENDENCY_MAINTAINER FOREIGN KEY (dependency_maintainer)
	  REFERENCES submodules (submodule_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE CASCADE
);

-- ###########################################################################################

CREATE TABLE tasks (
	task_id SERIAL NOT NULL,
	task_name CHARACTER VARYING(100) NOT NULL,
	task_description CHARACTER VARYING(1000) NULL,
	task_priority INTEGER NOT NULL,
	task_completion_percentage INTEGER NOT NULL,
	task_deadline TIMESTAMP WITHOUT TIME ZONE NULL,
	task_duration INTEGER NULL,
	task_admittance BOOLEAN NOT NULL,
	task_creator INTEGER NOT NULL,
	task_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	task_modifier INTEGER NOT NULL,
	task_modification_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_TASK_ID PRIMARY KEY (task_id),
	CONSTRAINT FK_TASK_CREATOR FOREIGN KEY (task_creator)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TASK_MODIFIER FOREIGN KEY (task_modifier)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE task_alterations (
	alteration_id SERIAL NOT NULL,
	alteration_task_id INTEGER NOT NULL,
	alteration_user_id INTEGER NOT NULL,
	alteration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	alteration_completion_percentage INTEGER NOT NULL,
	CONSTRAINT PK_TASK_ALTERATION_ID PRIMARY KEY (alteration_id),
	CONSTRAINT FK_TASK_ALTERATION_TASK FOREIGN KEY (alteration_task_id)
	  REFERENCES tasks (task_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TASK_ALTERATION_USER FOREIGN KEY (alteration_user_id)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE objective_tasks (
	objective_task_id SERIAL NOT NULL,
	objective_task_objective_id INTEGER NOT NULL,
	objective_task_task_id INTEGER NOT NULL,
	CONSTRAINT PK_OBJECTIVE_TASK_ID PRIMARY KEY (objective_task_id),
	CONSTRAINT FK_OBJECTIVE_TASK_OBJECTIVE FOREIGN KEY (objective_task_objective_id)
	  REFERENCES objectives (objective_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_OBJECTIVE_TASK_TASK FOREIGN KEY (objective_task_task_id)
	  REFERENCES tasks (task_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE project_tasks (
	project_task_id SERIAL NOT NULL,
	project_task_project_id INTEGER NOT NULL,
	project_task_task_id INTEGER NOT NULL,
	CONSTRAINT PK_PROJECT_TASK_ID PRIMARY KEY (project_task_id),
	CONSTRAINT FK_PROJECT_TASK_PROJECT FOREIGN KEY (project_task_project_id)
	  REFERENCES projects (project_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_PROJECT_TASK_TASK FOREIGN KEY (project_task_task_id)
	  REFERENCES tasks (task_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE submodule_tasks (
	submodule_task_id SERIAL NOT NULL,
	submodule_task_submodule_id INTEGER NOT NULL,
	submodule_task_task_id INTEGER NOT NULL,
	CONSTRAINT PK_SUBMODULE_TASK_ID PRIMARY KEY (submodule_task_id),
	CONSTRAINT FK_SUBMODULE_TASK_SUBMODULE FOREIGN KEY (submodule_task_submodule_id)
	  REFERENCES submodules (submodule_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_SUBMODULE_TASK_TASK FOREIGN KEY (submodule_task_task_id)
	  REFERENCES tasks (task_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE task_dependencies (
	dependency_id SERIAL NOT NULL,
	dependency_satiator INTEGER NOT NULL,
	dependency_maintainer INTEGER NOT NULL,
	CONSTRAINT PK_TASK_DEPENDENCY_ID PRIMARY KEY (dependency_id),
	CONSTRAINT FK_TASK_DEPENDENCY_SATIATOR FOREIGN KEY (dependency_satiator)
	  REFERENCES tasks (task_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE CASCADE,
	CONSTRAINT FK_TASK_DEPENDENCY_MAINTAINER FOREIGN KEY (dependency_maintainer)
	  REFERENCES tasks (task_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE CASCADE
);
CREATE TABLE task_estimations (
	estimation_id SERIAL NOT NULL,
	estimation_task INTEGER NOT NULL,
	estimation_pessimist INTEGER NOT NULL,
	estimation_realist INTEGER NOT NULL,
	estimation_optimist INTEGER NOT NULL,
	CONSTRAINT PK_TASK_ESTIMATION_ID PRIMARY KEY (estimation_id),
	CONSTRAINT FK_TASK_ESTIMATION_TASK FOREIGN KEY (estimation_task)
	  REFERENCES tasks (task_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- ###########################################################################################

CREATE TABLE impediment_statuses (
	status_id SERIAL NOT NULL,
	status_name CHARACTER VARYING(100),
	CONSTRAINT PK_IMPEDIMENT_STATUS_ID PRIMARY KEY (status_id)
);
CREATE TABLE impediments (
	impediment_id SERIAL NOT NULL,
	impediment_name CHARACTER VARYING(100) NOT NULL,
	impediment_description CHARACTER VARYING(2000) NULL,
	impediment_priority_id INTEGER NOT NULL,
	impediment_status_id INTEGER NOT NULL,
	impediment_report_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	impediment_reporter INTEGER NULL,
	impediment_processor INTEGER NULL,
	impediment_creator INTEGER NOT NULL,
	impediment_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	impediment_modifier INTEGER NOT NULL,
	impediment_modification_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_IMPEDIMENT_ID PRIMARY KEY (impediment_id),
	CONSTRAINT FK_IMPEDIMENT_PRIORITY_ID FOREIGN KEY (impediment_priority_id)
		REFERENCES priorities (priority_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_IMPEDIMENT_STATUS_ID FOREIGN KEY (impediment_status_id)
		REFERENCES impediment_statuses (status_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_IMPEDIMENT_REPORTER FOREIGN KEY (impediment_reporter)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_IMPEDIMENT_PROCESSOR FOREIGN KEY (impediment_processor)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_IMPEDIMENT_CREATOR FOREIGN KEY (impediment_creator)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_IMPEDIMENT_MODIFIER FOREIGN KEY (impediment_modifier)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE project_impediments (
	project_impediment_id SERIAL NOT NULL,
	project_impediment_project_id INTEGER NOT NULL,
	project_impediment_impediment_id INTEGER NOT NULL,
	CONSTRAINT PK_PROJECT_IMPEDIMENT_ID PRIMARY KEY (project_impediment_id),
	CONSTRAINT FK_PROJECT_IMPEDIMENT_PROJECT_ID FOREIGN KEY (project_impediment_project_id)
	  REFERENCES projects (project_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_PROJECT_IMPEDIMENT_IMPEDIMENT_ID FOREIGN KEY (project_impediment_impediment_id)
	  REFERENCES impediments (impediment_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE task_impediments (
	task_impediment_id SERIAL NOT NULL,
	task_impediment_task_id INTEGER NOT NULL,
	task_impediment_impediment_id INTEGER NOT NULL,
	CONSTRAINT PK_TASK_IMPEDIMENT_ID PRIMARY KEY (task_impediment_id),
	CONSTRAINT FK_TASK_IMPEDIMENT_TASK_ID FOREIGN KEY (task_impediment_task_id)
	  REFERENCES tasks (task_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TASK_IMPEDIMENT_IMPEDIMENT_ID FOREIGN KEY (task_impediment_impediment_id)
	  REFERENCES impediments (impediment_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE remedies (
	remedy_id SERIAL NOT NULL,
	remedy_description CHARACTER VARYING(2000) NOT NULL,
	remedy_impediment_id INTEGER NOT NULL,
	remedy_submission_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	remedy_provider INTEGER NOT NULL,
	remedy_creator INTEGER NOT NULL,
	remedy_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	remedy_modifier INTEGER NOT NULL,
	remedy_modification_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_REMEDY_ID PRIMARY KEY (remedy_id),
	CONSTRAINT FK_REMEDY_IMPEDIMENT FOREIGN KEY (remedy_impediment_id)
		REFERENCES impediments (impediment_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_REMEDY_PROVIDER FOREIGN KEY (remedy_provider)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_REMEDY_CREATOR FOREIGN KEY (remedy_creator)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_REMEDY_MODIFIER FOREIGN KEY (remedy_modifier)
		REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
	
-- ###########################################################################################

CREATE TABLE team_objective_assignments (
	assignment_id SERIAL NOT NULL,
	assignment_entrustor INTEGER NOT NULL,
	assignment_recipient INTEGER NOT NULL,
	assignment_objective INTEGER NOT NULL,
	assignment_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_TEAM_OBJECTIVE_ASSIGNMENTS_ID PRIMARY KEY (assignment_id),
	CONSTRAINT FK_TEAM_OBJECTIVE_ASSIGNMENTS_ENTRUSTOR FOREIGN KEY (assignment_entrustor)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_OBJECTIVE_ASSIGNMENTS_RECIPIENT FOREIGN KEY (assignment_recipient)
	  REFERENCES teams (team_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_OBJECTIVE_ASSIGNMENTS_OBJECTIVE FOREIGN KEY (assignment_objective)
	  REFERENCES objectives (objective_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE user_objective_assignments (
	assignment_id SERIAL NOT NULL,
	assignment_entrustor INTEGER NOT NULL,
	assignment_recipient INTEGER NOT NULL,
	assignment_objective INTEGER NOT NULL,
	assignment_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_USER_OBJECTIVE_ASSIGNMENTS_ID PRIMARY KEY (assignment_id),
	CONSTRAINT FK_USER_OBJECTIVE_ASSIGNMENTS_ENTRUSTOR FOREIGN KEY (assignment_entrustor)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_USER_OBJECTIVE_ASSIGNMENTS_RECIPIENT FOREIGN KEY (assignment_recipient)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_USER_OBJECTIVE_ASSIGNMENTS_OBJECTIVE FOREIGN KEY (assignment_objective)
	  REFERENCES objectives (objective_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE team_project_assignments (
	assignment_id SERIAL NOT NULL,
	assignment_entrustor INTEGER NOT NULL,
	assignment_recipient INTEGER NOT NULL,
	assignment_project INTEGER NOT NULL,
	assignment_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_TEAM_PROJECT_ASSIGNMENTS_ID PRIMARY KEY (assignment_id),
	CONSTRAINT FK_TEAM_PROJECT_ASSIGNMENTS_ENTRUSTOR FOREIGN KEY (assignment_entrustor)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_PROJECT_ASSIGNMENTS_RECIPIENT FOREIGN KEY (assignment_recipient)
	  REFERENCES teams (team_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_PROJECT_ASSIGNMENTS_PROJECT FOREIGN KEY (assignment_project)
	  REFERENCES projects (project_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE user_project_assignments (
	assignment_id SERIAL NOT NULL,
	assignment_entrustor INTEGER NOT NULL,
	assignment_recipient INTEGER NOT NULL,
	assignment_project INTEGER NOT NULL,
	assignment_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_USER_PROJECT_ASSIGNMENTS_ID PRIMARY KEY (assignment_id),
	CONSTRAINT FK_USER_PROJECT_ASSIGNMENTS_ENTRUSTOR FOREIGN KEY (assignment_entrustor)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_USER_PROJECT_ASSIGNMENTS_RECIPIENT FOREIGN KEY (assignment_recipient)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_USER_PROJECT_ASSIGNMENTS_PROJECT FOREIGN KEY (assignment_project)
	  REFERENCES projects (project_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE team_submodule_assignments (
	assignment_id SERIAL NOT NULL,
	assignment_entrustor INTEGER NOT NULL,
	assignment_recipient INTEGER NOT NULL,
	assignment_submodule INTEGER NOT NULL,
	assignment_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_TEAM_SUBMODULE_ASSIGNMENTS_ID PRIMARY KEY (assignment_id),
	CONSTRAINT FK_TEAM_SUBMODULE_ASSIGNMENTS_ENTRUSTOR FOREIGN KEY (assignment_entrustor)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_SUBMODULE_ASSIGNMENTS_RECIPIENT FOREIGN KEY (assignment_recipient)
	  REFERENCES teams (team_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_SUBMODULE_ASSIGNMENTS_SUBMODULE FOREIGN KEY (assignment_submodule)
	  REFERENCES submodules (submodule_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE user_submodule_assignments (
	assignment_id SERIAL NOT NULL,
	assignment_entrustor INTEGER NOT NULL,
	assignment_recipient INTEGER NOT NULL,
	assignment_submodule INTEGER NOT NULL,
	assignment_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_USER_SUBMODULE_ASSIGNMENTS_ID PRIMARY KEY (assignment_id),
	CONSTRAINT FK_USER_SUBMODULE_ASSIGNMENTS_ENTRUSTOR FOREIGN KEY (assignment_entrustor)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_USER_SUBMODULE_ASSIGNMENTS_RECIPIENT FOREIGN KEY (assignment_recipient)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_USER_SUBMODULE_ASSIGNMENTS_SUBMODULE FOREIGN KEY (assignment_submodule)
	  REFERENCES submodules (submodule_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE team_task_assignments (
	assignment_id SERIAL NOT NULL,
	assignment_entrustor INTEGER NOT NULL,
	assignment_recipient INTEGER NOT NULL,
	assignment_task INTEGER NOT NULL,
	assignment_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_TEAM_TASK_ASSIGNMENTS_ID PRIMARY KEY (assignment_id),
	CONSTRAINT FK_TEAM_TASK_ASSIGNMENTS_ENTRUSTOR FOREIGN KEY (assignment_entrustor)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_TASK_ASSIGNMENTS_RECIPIENT FOREIGN KEY (assignment_recipient)
	  REFERENCES teams (team_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_TEAM_TASK_ASSIGNMENTS_TASK FOREIGN KEY (assignment_task)
	  REFERENCES tasks (task_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE user_task_assignments (
	assignment_id SERIAL NOT NULL,
	assignment_entrustor INTEGER NOT NULL,
	assignment_recipient INTEGER NOT NULL,
	assignment_task INTEGER NOT NULL,
	assignment_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_USER_TASK_ASSIGNMENTS_ID PRIMARY KEY (assignment_id),
	CONSTRAINT FK_USER_TASK_ASSIGNMENTS_ENTRUSTOR FOREIGN KEY (assignment_entrustor)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_USER_TASK_ASSIGNMENTS_RECIPIENT FOREIGN KEY (assignment_recipient)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_USER_TASK_ASSIGNMENTS_TASK FOREIGN KEY (assignment_task)
	  REFERENCES tasks (task_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- ###########################################################################################

CREATE TABLE reviews (
	review_id SERIAL NOT NULL,
	review_name CHARACTER VARYING(100) NOT NULL,
	review_description CHARACTER VARYING(2000) NULL,
	review_organizer INTEGER NOT NULL,
	review_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_REVIEW_ID PRIMARY KEY (review_id),
	CONSTRAINT FK_REVIEW_ORGANIZER FOREIGN KEY (review_organizer)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE review_invitations (
	invitation_id SERIAL NOT NULL,
	invitaion_review INTEGER NOT NULL,
	invitation_recipiant INTEGER NOT NULL,
	CONSTRAINT PK_INVITATION_ID PRIMARY KEY (invitation_id),
	CONSTRAINT FK_INVITATION_REVIEW FOREIGN KEY (invitaion_review)
	  REFERENCES reviews (review_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_INVITATION_RECIPIENT FOREIGN KEY (invitation_recipiant)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- ###########################################################################################

CREATE TABLE notifications (
	notification_id SERIAL NOT NULL,
	notification_inducer INTEGER NOT NULL,
	notification_message CHARACTER VARYING(2000) NULL,
	notification_creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT PK_NOTIFICATION_ID PRIMARY KEY (notification_id),
	CONSTRAINT FK_NOTIFICATION_INDUCER FOREIGN KEY (notification_inducer)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE user_notifications (
	user_notification_id SERIAL NOT NULL,
	user_notification_user_id INTEGER NOT NULL,
	user_notification_notification_id INTEGER NOT NULL,
	CONSTRAINT PK_USER_NOTIFICATION_ID PRIMARY KEY (user_notification_id),
	CONSTRAINT FK_USER_NOTIFICATION_USER_ID FOREIGN KEY (user_notification_user_id)
	  REFERENCES app_users (user_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT FK_USER_NOTIFICATION_NOTIFICATION_ID FOREIGN KEY (user_notification_notification_id)
	  REFERENCES notifications (notification_id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- ###########################################################################################

ALTER TABLE priorities OWNER TO postgres;
ALTER TABLE mission_stages OWNER TO postgres;
ALTER TABLE roles OWNER TO postgres;
ALTER TABLE app_users OWNER TO postgres;
ALTER TABLE authorizations OWNER TO postgres;
ALTER TABLE teams OWNER TO postgres;
ALTER TABLE team_members OWNER TO postgres;
ALTER TABLE objective_statuses OWNER TO postgres;
ALTER TABLE objectives OWNER TO postgres;
ALTER TABLE objective_missions OWNER TO postgres;
ALTER TABLE objective_supervisors OWNER TO postgres;
ALTER TABLE objective_status_alterations OWNER TO postgres;
ALTER TABLE objective_appointments OWNER TO postgres;
ALTER TABLE project_statuses OWNER TO postgres;
ALTER TABLE projects OWNER TO postgres;
ALTER TABLE project_missions OWNER TO postgres;
ALTER TABLE project_managers OWNER TO postgres;
ALTER TABLE product_owners OWNER TO postgres;
ALTER TABLE project_status_alterations OWNER TO postgres;
ALTER TABLE objective_projects OWNER TO postgres;
ALTER TABLE submodules OWNER TO postgres;
ALTER TABLE project_submodules OWNER TO postgres;
ALTER TABLE submodule_dependencies OWNER TO postgres;
ALTER TABLE tasks OWNER TO postgres;
ALTER TABLE task_alterations OWNER TO postgres;
ALTER TABLE objective_tasks OWNER TO postgres;
ALTER TABLE project_tasks OWNER TO postgres;
ALTER TABLE submodule_tasks OWNER TO postgres;
ALTER TABLE impediment_statuses OWNER TO postgres;
ALTER TABLE impediments OWNER TO postgres;
ALTER TABLE project_impediments OWNER TO postgres;
ALTER TABLE task_impediments OWNER TO postgres;
ALTER TABLE remedies OWNER TO postgres;
ALTER TABLE task_dependencies OWNER TO postgres;
ALTER TABLE task_estimations OWNER TO postgres;
ALTER TABLE user_objective_assignments OWNER TO postgres;
ALTER TABLE user_project_assignments OWNER TO postgres;
ALTER TABLE user_submodule_assignments OWNER TO postgres;
ALTER TABLE user_task_assignments OWNER TO postgres;
ALTER TABLE team_objective_assignments OWNER TO postgres;
ALTER TABLE team_project_assignments OWNER TO postgres;
ALTER TABLE team_submodule_assignments OWNER TO postgres;
ALTER TABLE team_task_assignments OWNER TO postgres;
ALTER TABLE reviews OWNER TO postgres;
ALTER TABLE review_invitations OWNER TO postgres;
ALTER TABLE notifications OWNER TO postgres;
ALTER TABLE user_notifications OWNER TO postgres;