INSERT INTO priorities (priority_id, priority_name) VALUES
(0, 'LOW'),
(1, 'MEDIUM'),
(2, 'HIGH'),
(3, 'VERY_HIGH');

INSERT INTO mission_stages (stage_id, stage_name) VALUES
(0, 'OPEN'),
(1, 'ADRESSED'),
(2, 'IN_PROGRESS'),
(3, 'DISCARDED'),
(4, 'ACCOMPLISHED');

-- ###########################################################################################

INSERT INTO roles (role_id, role_name) VALUES 
(0, 'pristine_user'),
(1, 'general_user'),
(2, 'general_manager'),
(3, 'department_manager'),
(4, 'central_manager'),
(5, 'system_administrator');
SELECT SETVAL('roles_role_id_seq', COALESCE(MAX(role_id), 0) ) FROM roles;

INSERT INTO app_users (user_id, user_name, user_password_hash, user_role, user_registration_date, user_account_modification_date, user_account_modifier, user_notification_view_count, user_image_selector) VALUES 
(0, 'adam', '$2a$10$i2mSCec8pR886PFgaydyauLocGUCavrQMVcs7I6jJcu6v.0hye.3a', 4, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 1),
(1, 'brent', '$2a$10$at/2hcd0Lv3s20BIsPYPbeHjY0CBAsNhupFITrlPS6qzMB3g3lRW.', 3, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 13),
(2, 'chris', '$2a$10$UxYObgg/mDYWu77wCF8V7.XqCdmdG19BOAaPCzbzvY601Sg1of7Mq', 1, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 9),
(3, 'dennis', '$2a$10$g5DOPhsfuQLqzB6yd1g.2eYswE00Zh8q77fPSc5dKjwWXV/pWMGS6', 1, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 5),
(4, 'ellie', '$2a$10$MHLMnRaQkoybGpdvMY.F0e7jIN8IYj6hozDc12EZPOFTfoTypLZnu', 1, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 6),
(5, 'frank', '$2a$10$7m8VIy8YWdu2p6MkXjhrfOTPzHZ0NiNBjACkqCVWjH8D8D4YaJ8DO', 1, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 3),
(6, 'gabrielle', '$2a$10$ARgEy3y3fYOzoupqwWRqzeASy5KllWKLixDWId57Dl5whjV9i888W', 1, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 14),
(7, 'holly', '$2a$10$nExDjhpB1s/c8TKAI3U5tuVQHMCI2vr7Z0vz4CKs/I.PV5lM16wmy', 1, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 16),
(8, 'ike', '$2a$10$k5v48h9nA410N4ULAn.PnOZX2VudxIqR9I4FKUjn9qZIBlNqTqdwW', 1, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 7),
(9, 'jenny', '$2a$10$v0hqFIXP3PIUq1vx8wXGFehCjdah5qaCBO0sHgSTiZsVJBDze/lAy', 1, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 2),
(10, 'kevin', '$2a$10$zkz3vmAw/GKfiAp.R8xHDOg.3VsZJGz4rzW2Cb62W55W8mqC7sRtS', 2, '2015/01/01 00:00:00', '2015/01/01 00:00:00', 0, 0, 15);
SELECT SETVAL('app_users_user_id_seq', COALESCE(MAX(user_id), 0) ) FROM app_users;

INSERT INTO authorizations (authorization_user_id, authorization_role_id) VALUES 
(0, 4),
(1, 3),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 2);

INSERT INTO teams (team_id, team_name, team_leader, team_creator, team_creation_date, team_modifier, team_modification_date) VALUES
(0, 'Management', 0, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(1, 'Dev Ops', 2, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(2, 'Back-end development', 3, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(3, 'Front-end development', 8, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(4, 'Quality assurance', 3, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00');
SELECT SETVAL('teams_team_id_seq', COALESCE(MAX(team_id), 0) ) FROM teams;

INSERT INTO team_members (team_member_team_id, team_member_user_id) VALUES
(0, 0),
(0, 1),
(1, 2),
(1, 4),
(1, 5),
(2, 3),
(2, 6),
(2, 7),
(3, 8),
(3, 9),
(4, 3),
(4, 7);

-- ###########################################################################################

INSERT INTO objective_statuses (status_id, status_name) VALUES 
(0, 'PLANNED'),
(1, 'DESIGNATED'),
(2, 'CONTINUOUS'),
(3, 'DISCONTINUED'),
(4, 'COMPLETED');

INSERT INTO objectives (objective_id, objective_name, objective_description, objective_priority, objective_status_id, objective_deadline, objective_confidentiality, objective_creator, objective_creation_date, objective_modifier, objective_modification_date) VALUES 
(0, 'Integration with augmented reality', '', 2, 1, NULL, TRUE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(1, 'Increase influence in market', 'Completing projects for our esteemed contacts', 1, 1, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(2, 'Processing of product incidents', 'The continuous fixing of occurring reported product problems', 3, 2, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(3, 'Upskilling of new colleagues', 'The newcomers must be involved in project work as soon as possible, therfore trainings are absolutely neccesary', 5, 0, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(4, 'Develop cross-platfrom worflow management system', 'Due to competitors lack in flexibility and structure handling the creation of revisioned management system could give us a leading edge on the market', 1, 1, NULL, TRUE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(5, 'Clone specific customer architectures into a more flexible microservice-based solution', 'Efforts to follow new trends', 4, 3, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(6, 'Recreating designated legacy control systems into the cloud', 'Software preconditions and multiple high end requests have led to the descision to move solutions to a cloud platfrom', 6, 0, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(7, 'Test Objective', '', 10, 0, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(8, 'Test Objective 2', '', 10, 0, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00');
SELECT SETVAL('objectives_objective_id_seq', COALESCE(MAX(objective_id), 0) ) FROM objectives;

INSERT INTO objective_missions (mission_id, mission_name, mission_description, mission_objective, mission_stage_id) VALUES
(0, 'Steps towards growing technologies', '', 0, 4);

INSERT INTO objective_supervisors (objective_supervisor_objective_id, objective_supervisor_user_id) VALUES
(0, 1);

INSERT INTO objective_status_alterations (alteration_id, alteration_objective_id, alteration_status_id, alteration_date) VALUES
(0, 0, 0, '2016/03/20 15:45:00'),
(1, 0, 1, '2016/06/05 11:50:00'),
(2, 1, 0, '2015/02/19 16:20:00'),
(3, 1, 2, '2015/04/27 10:30:00');

INSERT INTO objective_appointments(appointment_user_id, appointment_objective_id) VALUES
(0, 1);

-- ###########################################################################################

INSERT INTO project_statuses (status_id, status_name) VALUES 
(0, 'PROPOSED'),
(1, 'PENDING'),
(2, 'INITIATED'),
(3, 'UNDER_ANALYSIS'),
(4, 'IN_DESIGN'),
(5, 'IN_DEVELOPMENT'),
(6, 'CANCELED'),
(7, 'TESTING'),
(8, 'IN_CORRECTION'),
(9, 'VALIDATING'),
(10, 'DEPLOYING'),
(11, 'IMPLEMENTING'),
(12, 'INTEGRATING'),
(13, 'LIVE'),
(14, 'MAINTAINED_BY_OPERATIONS'),
(15, 'UPGRADING'),
(16, 'DISPOSED');

INSERT INTO projects (project_id, project_name, project_description, project_status_id, project_deadline, project_confidentiality, project_creator, project_creation_date, project_modifier, project_modification_date) VALUES 
(0, 'Ceraphis data deployment', 'Deployment of Ceraphis Solutions buisness data to data warehouse in India', 10, '2015/11/30 00:00:00', TRUE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(1, 'QuickExtract app', 'Develop mobile app for QuickExtract', 7, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(2, 'Grove BI outsourcing', '', 0, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(3, 'Codename -NOVA-', 'Augmented reality utility tool for enterprise management', 5, '2017/05/26 00:00:00', TRUE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(4, 'Reopening unconfirmed tickets', 'Revisioning questionable tickets', 0, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(5, 'Resolving of Very High level tickets due to schema migration', 'Our last service migration caused problems in push sub-module. The problem is more complex than just a quick refactor', 0, NULL, TRUE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(6, 'Back-end training', 'Training for the future developers', 0, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(7, 'Codename -ISOCHRONE-', 'Recursive inlay of project system implementation', 5, NULL, TRUE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(8, 'Test Project 1', '', 0, NULL, TRUE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(9, 'Codename -CONLINK-', 'Mobile app for CPM system implementation', 6, NULL, TRUE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(10, 'Test Project 2', '', 0, NULL, TRUE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(11, 'Test Project 3', '', 0, NULL, TRUE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00');
SELECT SETVAL('projects_project_id_seq', COALESCE(MAX(project_id), 0) ) FROM projects;

INSERT INTO project_missions (mission_id, mission_name, mission_description, mission_project, mission_stage_id) VALUES
(0, 'Employee data visualization requests', '', 3, 2);

INSERT INTO project_managers (project_manager_project_id, project_manager_user_id) VALUES
(1, 1),
(2, 2),
(2, 3),
(3, 0),
(7, 0);

INSERT INTO product_owners (product_owner_project_id, product_owner_user_id) VALUES
(0, 0),
(3, 10),
(1, 0),
(7, 0);

INSERT INTO project_status_alterations (alteration_id, alteration_project_id, alteration_status_id, alteration_user_id, alteration_date) VALUES 
(0, 0, 1, 2, '2015/06/03 10:15:00'),
(1, 0, 3, 4, '2015/06/09 16:30:00'),
(2, 0, 4, 2, '2015/06/12 10:15:00'),
(3, 0, 5, 0, '2015/06/19 17:15:00'),
(4, 0, 7, 1, '2015/06/25 11:15:00'),
(5, 0, 9, 1, '2015/07/02 09:15:00'),
(6, 0, 10, 3, '2015/07/15 10:15:00'),
(7, 1, 5, 5, '2015/08/07 17:15:00'),
(8, 1, 7, 1, '2016/10/23 15:15:00'),
(9, 2, 0, 4, '2016/11/26 10:15:00'),
(10, 3, 2, 2, '2016/09/18 09:15:00'),
(11, 3, 4, 1, '2016/10/01 15:15:00'),
(12, 3, 6, 0, '2016/10/22 10:15:00'),
(13, 3, 5, 3, '2016/12/12 09:15:00');

INSERT INTO objective_projects (objective_project_objective, objective_project_project) VALUES
(0, 3),
(1, 0),
(1, 1),
(1, 2),
(2, 4),
(2, 5),
(3, 6),
(4, 7),
(4, 9),
(7, 8),
(7, 10),
(7, 11);

-- ###########################################################################################

INSERT INTO submodules (submodule_id, submodule_name, submodule_description, submodule_deadline, submodule_creator, submodule_creation_date, submodule_modifier, submodule_modification_date) VALUES 
(0, 'Submodule structure implementation', 'Adding submodules as a new level into the CPM stack', '2017/12/01 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(1, 'User assignment implementation', 'User and team level assignment functionality is imperative for proper delegation of responsibility', '2017/12/01 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(2, 'Notification subscription implementation', 'Invested users must be notified on key item modifications', '2017/12/01 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(3, 'Estimation, and analysis for Task dependency chain', 'Critical time, slack, and overall statistic calculus for Submodule level Task dependency configurations', '2017/12/01 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(4, 'Task dependency chain implementation', 'Dependency configurations must be added for proper critical point method calculations', '2017/12/01 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(5, 'Deadline based alerting system', 'Upcoming deadlines must trigger warinings in the system, or possibly correctional advice', '2017/12/01 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(6, 'Test Submodule 1', '', '2017/09/25 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(7, 'User profile view implementation', 'Seperate view must be added to view user level responsibiliies', '2017/12/01 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(8, 'Team management implementation', 'Control must be made for team creation, modification, member and leader selection', '2017/12/01 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(9, 'Stratagem service implementation wrap-up', 'Remaining functionality must be implemented', '2017/09/25 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(10, 'User authentication', 'Client must authenticate with service based identity provider', '2017/10/02 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(11, 'Shared service communication functionality implementation', 'Client must be able to retrieve and modify the respected service structures', '2017/10/02 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(12, 'Native Android platform implementation', 'Creation of Android specific client app', '2017/10/02 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(13, 'Native iOS platform implementation', 'Creation of iOS specific client app', '2017/10/02 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(14, 'Client testing', 'Developing testing scenarios for the mobile client', '2017/10/02 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(15, 'Service testing', 'Developing testing scenarios for the back-end service', '2017/09/25 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'), 	
(16, 'Test Submodule 2', '', '2018/03/09 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(17, 'Test Submodule 3', '', '2018/03/09 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(18, 'Test Submodule 4', '', '2018/03/09 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(19, 'Test Submodule 5', '', '2018/03/09 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(20, 'Test Submodule 6', '', '2018/03/09 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(21, 'Test Submodule 7', '', '2018/03/09 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(22, 'Not started', '', '2018/03/09 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(23, 'In Progress', '', '2018/03/09 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(24, 'Validating', '', '2018/03/09 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(25, 'Finished', '', '2018/03/09 00:00:00', 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00');
SELECT SETVAL('submodules_submodule_id_seq', COALESCE(MAX(submodule_id), 0) ) FROM submodules;

INSERT INTO project_submodules (project_submodule_project, project_submodule_submodule) VALUES 
(7, 0),
(7, 1),
(7, 2),
(7, 3),
(7, 4),
(7, 5),
(8, 6),
(7, 7),
(7, 8),
(7, 9),
(9, 10),
(9, 11),
(9, 12),
(9, 13),
(9, 14),
(7, 15),
(8, 16),
(8, 17),
(8, 18),
(8, 19),
(10, 20),
(10, 21),
(11, 22),
(11, 23),
(11, 24),
(11, 25);

INSERT INTO submodule_dependencies (dependency_satiator, dependency_maintainer) VALUES
(6, 16),
(6, 17),
(16, 18),
(16, 19);

-- ###########################################################################################

INSERT INTO tasks (task_id, task_name, task_description, task_priority, task_completion_percentage, task_deadline, task_duration, task_admittance, task_creator, task_creation_date, task_modifier, task_modification_date) VALUES 
(0, 'Use-case test tool', 'Creating tool for efficient use-case testing', 2, 30, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(1, 'Print matching', 'Matching watermark prints', 1, 85, '2016/08/14 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(2, 'Extraction planning', 'Planning extraction approach', 0, 60, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(3, 'Backup system allocation', 'Allocating backup systems for overload evasion', 1, 100, '2016/09/10 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(4, 'Incident wrap up', 'Closing all end-to-end test incidents', 1, 0, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(5, 'Stable build', 'Create maintainable build on CI server for ease of rollback', 1, 100, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(6, 'Tab-like modal navigation', 'Investigate possibility to navigate between login and registration forms, in a tab-like manner. This might be a possible solution to the noticed UI bug.', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(7, 'REST', 'Create REST API module', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(8, 'RMI', 'Create Remote EJB module', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(9, 'Cross service navigator malfunction', 'Certain inputs return us to null zone', 1, 0, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(10, 'Olingo error', 'Default implementation in client system not sufficient', 2, 0, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(11, 'Corrupt VM auth', 'Authorizational rights have been tangled', 1, 0, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(12, 'Broken sequence generator', 'Our custom generator calculate incorrect keys, possibly because of third party interference', 0, 0, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(13, 'UI response bug', 'Responsive forms of client software not working after component update', 2, 0, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(14, 'Add default sorting to all lists', 'Collection sizes have higher priority', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(15, 'Project level test Task 1', '', 10, 0, NULL, 1, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(16, 'Add submodules to database', 'Add definitions and initial data, and extend existing tables', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(17, 'Update persistence layer', 'Add Submodule entity, and expand existing ones with associations', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(18, 'Add submodule representors', 'Domain objects must be updated', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(19, 'Add submodule protocols', 'Expand business layer for submodules', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(20, 'Add submodule views', 'Controllers and pages must be added alike', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(21, 'Implement crud operations', 'Full range crud operations must work on target associations as well', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(22, 'Repair submodule completion indicator', 'Getter deletion might have interfered', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(23, 'Submodule level test Task 1', '', 10, 0, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(24, 'Redesign objective addition button', 'Button should be centered, possibly on an icon', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(25, 'Create header for specified structure cards', 'Tag should depend on deadline completion or status. This would require completion to be added to higher level structures as well.', 2, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(26, 'Objective level test Task 1', '', 10, 0, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(27, 'Add links to profile view from all refrences', 'View must be accessible at all questionable points of delegation and responsibility', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(28, 'Add profile inspection button to navbar', 'Signed in user must have access to own profile information at all times', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(29, 'Add form for profile editing, with possible extension for future avatar selection', 'User data modification must be made possible on email level for now, and later for name', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(30, 'Add main control level assignment structures due to additionally stored attributes', 'Assignment entruster, and creation date storage is paramount for tracability', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(31, 'Add assignment distribution control to respective views', 'Asignments can only be distributed according to authorization level', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(32, 'Assignment removal implementation', 'Asignment deletion does not need to be traced only reported with notification to the unassigned parties in later builds', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(33, 'Assignment listing in main control structures and profile views', 'All assignments must be displayed in an orderly manor', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(34, 'Assignment distribution should also be implemented on team level', 'This requires manager level manipulation of team structures. Assignment would be tied to team leader authorization level', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(35, 'Structure wiring', 'User and team subscriptions must be added for all major structures', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(36, 'Assignment level triggering', 'Assignments must always trigger notifications', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(37, 'Update level triggering', 'Updates trigger notification creations for effected parties', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(38, 'Association strategy', 'Only single Notification must be created at all times, and multiple join records will attach users and teams', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(39, 'List based presentation', 'In profile view Notifications are ordered by team memberships and individual subscriptions', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(40, 'Dependency presentation', 'Dependencies may be inspected in task view', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(41, 'Inspection of dependency levels', 'All direct dependencies and dependants must be represented grouped by the dependency level', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(42, 'Representor constroction', 'Upon assembling Task representor we must loop through dependency and dependant lists recursively and must return a list of task lists and an interview indicating dependency level', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(43, 'Dependency addition control', 'When adding dependency and dependant tasks, the possibilities must be provided only at the parent structure level. Removal should work similarly as assignment removal. Duplicate or cycle creators must not be provided', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(44, 'Deadline warnings', 'Deadlines that are due in a week have warnings in orange, overdue ones in red', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(45, 'Correctional action generation', 'Investigate possibiblity by which: on navigating to control view on which signed in user is assigned business logic must discern deadline time reserve and remaining unfinished task count ratio. If the number surpasses the threshold (Configurable strategy) then business logic must assess all assignable workforces, and suggest the resolving of impediments', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(46, 'Structure generation in background job', 'Investigation of asynchronous notification creation', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(47, 'Submodule level test Task 2', '', 10, 0, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(48, 'Submodule level test Task 3', '', 10, 0, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(49, 'Submodule level test Task 4', '', 10, 0, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(50, 'Discerning ongoing and completed assignemnts', 'At assignment listing based on structure completion state, the assignments should be ordered into 2 or 3 groups. (Unstarted, ongoing, completed)', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(51, 'Calculate remaining work days for submodule completion', 'If even one task completion time is given, be it estimated or not, it will be added to the submodule completion time. Expected duration in case of estimations. This value is the sum of all complete task durations, placed into contrast with the grand sum of all task durations in the cluster', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(52, 'Calculate critical completion time', 'Calculate submodule expected completion time with critical path method. If no dependency configurations are given. The generated start and finish tasks will connect all activities, making the longest task the critical time. Otherwise, dependency tree must bee traversed and processed', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(53, 'Estimate submodule duration with 95% confidence level', 'Using beta distribution, expected duration, variance and standard deviation, give reliable submodule duration estimate', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(54, 'Give probability of submodule finishing before given deadline', 'If submodule deadline is registered, produce probability with PERT methodology whether the remaining time is sufficient for completion', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(55, 'Implement adaptable model for duration, and pre-deadline completion estimations', 'Upon making both calculations the algorithm must adapt to the current level of progress. This involves excluding completed tasks from the dependency chain and building the network model with only the unfinished entries of the list', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(56, 'Estimation summary for higher level structures', 'Calculated durational estimates can be summed up for Project, and Objective levels', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(57, 'Present calculated values on respected views', 'Values to be added are: 1) Remaining work days and progression. 2) Critical/Expected completion date. 3) If values are estimated completion date estimation with high confidence level. 4) If deadline is registered pre-deadline finish forcasting. If values are set expected completion time subtracted from deadline. If value is negative we are behind schedule, otherwise we are ahead of schedule, in case of 0 we are on schedule. If values are estimates otherwise calculated chance of finishing before deadline must be given.', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(58, 'Development documentation', 'Full range detailed documentation of finished work', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(59, 'Investigate testing possibilities', 'The requested scope is Selenium, JUnit, integration and unit testing', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(60, 'Implement service level Team management control functionality', 'Team control services and protocols must be implemented for web layer usage', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(61, 'Implement client views for team management', 'Control views must be added for team creation and member modification', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(62, 'User Role control implementation', 'Higher level managers must be able to assign authorization levels underneath theirs to defines user base', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(63, 'Evaluation view wrap up', 'Task level dependencies must be displayed on project and Objective levels', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(64, 'Evaluate time needed for Submodule level dependency configurations', 'Remaining Tasks retain higher priority, yet generic dependency evaluation would be a powerful documentation tool', 1, 100, '2017/12/01 00:00:00', NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(65, 'Objective level test Task 2', '', 10, 0, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(66, 'Objective level test Task 3', '', 10, 0, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(67, 'Objective level test Task 4', '', 10, 0, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(68, 'Project level test Task 2', '', 10, 0, NULL, 1, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(69, 'Project level test Task 3', '', 10, 0, NULL, 2, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(70, 'Project level test Task 4', '', 10, 0, NULL, 4, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(71, 'Project level test Task 5', '', 10, 0, NULL, 3, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(72, 'Project level test Task 6', '', 10, 0, NULL, 4, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(73, 'Project level test Task 7', '', 10, 0, NULL, 2, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(74, 'Project level test Task 8', '', 10, 0, NULL, 1, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(75, 'Project level test Task 9', '', 10, 0, NULL, 3, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(76, 'Project level test Task 10', '', 10, 0, NULL, 0, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(77, 'Project level test Task 11', '', 10, 0, NULL, 3, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(78, 'Project level test Task 12', '', 10, 0, NULL, 2, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(79, 'Project level test Task 13', '', 10, 0, NULL, 1, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(80, 'Project level test Task 14', '', 10, 0, NULL, 1, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(81, 'Phase tester', '', 10, 0, NULL, NULL, FALSE, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00');
SELECT SETVAL('tasks_task_id_seq', COALESCE(MAX(task_id), 0) ) FROM tasks;

-- INSERT INTO task_alterations

INSERT INTO objective_tasks (objective_task_objective_id, objective_task_task_id) VALUES
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(7, 26),
(7, 65),
(7, 66),
(7, 67),
(4, 58);

INSERT INTO project_tasks (project_task_project_id, project_task_task_id) VALUES
(0, 1),
(2, 2),
(2, 0),
(2, 3),
(1, 4),
(3, 5),
(7, 6),
(7, 7),
(7, 8),
(7, 14),
(8, 15),
(8, 68),
(8, 69),
(8, 70),
(8, 71),
(8, 72),
(8, 73),
(8, 74),
(8, 75),
(8, 76),
(8, 77),
(8, 78),
(8, 79),
(7, 22),
(7, 24),
(7, 25),
(10, 80);

INSERT INTO submodule_tasks (submodule_task_submodule_id, submodule_task_task_id) VALUES
(0, 16),
(0, 17),
(0, 18),
(0, 19),
(0, 20),
(0, 21),
(6, 23),
(6, 47),
(6, 48),
(6, 49),
(7, 27),
(7, 28),
(7, 29),
(1, 30),
(1, 31),
(1, 32),
(1, 33),
(1, 34),
(1, 50),
(2, 35),
(2, 36),
(2, 37),
(2, 38),
(2, 39),
(4, 40),
(4, 41),
(4, 42),
(4, 43),
(5, 44),
(5, 45),
(5, 46),
(3, 51),
(3, 52),
(3, 53),
(3, 54),
(3, 55),
(3, 56),
(3, 57),
(15, 59),
(8, 60),
(8, 61),
(9, 62),
(9, 63),
(9, 64),
(22, 81);

INSERT INTO task_dependencies (dependency_satiator, dependency_maintainer) VALUES
(23, 47),
(23, 48),
(47, 49),
(48, 49),
(15, 68),
(15, 69),
(68, 75),
(69, 70),
(69, 71),
(70, 76),
(71, 72),
(72, 73),
(72, 74),
(73, 76),
(75, 77),
(76, 77),
(77, 78),
(78, 79),
(74, 79),
(26, 65),
(26, 66),
(66, 67);

INSERT INTO task_estimations (estimation_id, estimation_task, estimation_pessimist, estimation_realist, estimation_optimist) VALUES
(0, 4, '5', '4', '3'),
(1, 5, '3', '2', '1'),
(2, 23, '7', '3', '2'),
(3, 47, '5', '4', '3'),
(4, 48, '6', '3', '1'),
(5, 49, '3', '2', '1'),
(6, 26, '8', '6', '5'),
(7, 65, '2', '1', '0'),
(8, 66, '4', '2', '1'),
(9, 67, '7', '5', '4');
SELECT SETVAL('task_estimations_estimation_id_seq', COALESCE(MAX(estimation_id), 0) ) FROM task_estimations;

-- ###########################################################################################

INSERT INTO impediment_statuses (status_id, status_name) VALUES
(0, 'OPEN'),
(1, 'ADRESSED'),
(2, 'BEING_PROCESSED'),
(3, 'SOLUTION_PROVIDED'),
(4, 'DUPLICATE'),
(5, 'DISMISSED'),
(6, 'CONFIRMED');

INSERT INTO impediments (impediment_id, impediment_name, impediment_description, impediment_priority_id, impediment_status_id, impediment_report_date, impediment_reporter, impediment_processor, impediment_creator, impediment_creation_date, impediment_modifier, impediment_modification_date) VALUES
(0, 'Insufficient tools', 'ARPA SDK would serve as a huge advantage with development', 2, 1, '2016/10/25 14:45:00', 3, 5, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(1, 'Data connection', 'Unable to establish connection with service endpoint with OData componenet', 3, 6, '2015/12/11 16:10:00', 4, 1, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(2, 'Lingering numpad', 'A High level incident is not recreatable with our build, and transition at this point is not possible', 0, 2, '2016/09/03 10:40:00', 9, 0, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00');
SELECT SETVAL('impediments_impediment_id_seq', COALESCE(MAX(impediment_id), 0) ) FROM impediments;

INSERT INTO project_impediments (project_impediment_project_id, project_impediment_impediment_id) VALUES
(3, 0),
(1, 1);

INSERT INTO task_impediments (task_impediment_task_id, task_impediment_impediment_id) VALUES
(4, 2);

INSERT INTO remedies (remedy_id, remedy_description, remedy_impediment_id, remedy_submission_date, remedy_provider, remedy_creator, remedy_creation_date, remedy_modifier, remedy_modification_date) VALUES
(0, 'Use service control tool to generate project structure from existing endpoint meta data', 1, '2015/12/13 14:20:00', 1, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00'),
(1, 'Request authorization key and administrative user for base64 auth parameter when connecting', 2, '2016/04/20 17:35:00', 4, 0, '2015/01/01 00:00:00', 0, '2015/01/01 00:00:00');
SELECT SETVAL('remedies_remedy_id_seq', COALESCE(MAX(remedy_id), 0) ) FROM remedies;

-- ###########################################################################################

INSERT INTO team_objective_assignments (assignment_entrustor, assignment_recipient, assignment_objective, assignment_creation_date) VALUES
(1, 1, 1, '2015/12/13 14:20:00');
SELECT SETVAL('team_objective_assignments_assignment_id_seq', COALESCE(MAX(assignment_id), 0) ) FROM team_objective_assignments;
INSERT INTO user_objective_assignments (assignment_entrustor, assignment_recipient, assignment_objective, assignment_creation_date) VALUES
(2, 2, 3, '2015/12/13 14:20:00');
SELECT SETVAL('user_objective_assignments_assignment_id_seq', COALESCE(MAX(assignment_id), 0) ) FROM user_objective_assignments;
INSERT INTO team_project_assignments (assignment_entrustor, assignment_recipient, assignment_project, assignment_creation_date) VALUES
(2, 2, 3, '2015/12/13 14:20:00');
SELECT SETVAL('team_project_assignments_assignment_id_seq', COALESCE(MAX(assignment_id), 0) ) FROM team_project_assignments;
INSERT INTO user_project_assignments (assignment_entrustor, assignment_recipient, assignment_project, assignment_creation_date) VALUES
(0, 3, 2, '2015/12/13 14:20:00');
SELECT SETVAL('user_project_assignments_assignment_id_seq', COALESCE(MAX(assignment_id), 0) ) FROM user_project_assignments;
INSERT INTO team_task_assignments (assignment_entrustor, assignment_recipient, assignment_task, assignment_creation_date) VALUES
(1, 2, 0, '2015/12/13 14:20:00');
SELECT SETVAL('team_task_assignments_assignment_id_seq', COALESCE(MAX(assignment_id), 0) ) FROM team_task_assignments;
INSERT INTO user_task_assignments (assignment_entrustor, assignment_recipient, assignment_task, assignment_creation_date) VALUES
(0, 7, 1, '2015/12/13 14:20:00');
SELECT SETVAL('user_task_assignments_assignment_id_seq', COALESCE(MAX(assignment_id), 0) ) FROM user_task_assignments;

-- ###########################################################################################

INSERT INTO reviews (review_id, review_name, review_description, review_organizer, review_date) VALUES
(0, 'ARPA integration code review', 'Let us analyze our progress made with the new AR SDK', 0, '2016/09/15 14:00:00'),
(1, 'Sprint review', 'Let us see what we have accomplished in our bi-weekly retrospective', 1, '2016/11/03 10:30:00');
SELECT SETVAL('reviews_review_id_seq', COALESCE(MAX(review_id), 0) ) FROM reviews;

INSERT INTO review_invitations (invitaion_review, invitation_recipiant) VALUES
(0, 0),
(0, 3),
(0, 6),
(0, 7);

-- ###########################################################################################

INSERT INTO notifications (notification_id, notification_inducer, notification_message, notification_creation_date) VALUES
(0, 0, 'Welcome to stratagem!', '2015/01/01 00:00:00');
SELECT SETVAL('notifications_notification_id_seq', 1) FROM notifications;

INSERT INTO user_notifications (user_notification_user_id, user_notification_notification_id) VALUES
(0, 0),
(1, 0),
(2, 0),
(3, 0),
(4, 0),
(5, 0),
(6, 0),
(7, 0),
(8, 0),
(9, 0),
(10, 0);
