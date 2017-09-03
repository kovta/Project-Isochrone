GRANT SELECT, INSERT, UPDATE, DELETE ON 
app_users, roles, authorizations, teams, team_members TO stratagem_std_user;
GRANT USAGE, SELECT, UPDATE ON 
app_users_user_id_seq, roles_role_id_seq, authorizations_authorization_id_seq, teams_team_id_seq TO stratagem_std_user;

GRANT SELECT, INSERT, UPDATE, DELETE ON 
objectives, projects, submodules, tasks, task_dependencies, task_estimations,
objective_projects, objective_tasks, project_submodules, project_tasks, submodule_tasks TO stratagem_std_user;
GRANT USAGE, SELECT, UPDATE ON 
objectives_objective_id_seq, projects_project_id_seq, submodules_submodule_id_seq, tasks_task_id_seq,
objective_projects_objective_project_id_seq, objective_tasks_objective_task_id_seq, project_submodules_project_submodule_id_seq, 
project_tasks_project_task_id_seq, submodule_tasks_submodule_task_id_seq,
task_dependencies_dependency_id_seq, task_estimations_estimation_id_seq TO stratagem_std_user;

GRANT SELECT, INSERT, UPDATE, DELETE ON 
impediments, remedies, project_impediments, task_impediments TO stratagem_std_user;
GRANT USAGE, SELECT, UPDATE ON 
impediments_impediment_id_seq, remedies_remedy_id_seq TO stratagem_std_user;

GRANT SELECT, INSERT, UPDATE, DELETE ON 
team_objective_assignments, team_project_assignments, team_submodule_assignments, team_task_assignments, 
user_objective_assignments, user_project_assignments, user_submodule_assignments, user_task_assignments TO stratagem_std_user;
GRANT USAGE, SELECT, UPDATE ON 
team_objective_assignments_assignment_id_seq, user_objective_assignments_assignment_id_seq,
team_project_assignments_assignment_id_seq, user_project_assignments_assignment_id_seq,
team_submodule_assignments_assignment_id_seq, user_submodule_assignments_assignment_id_seq,
team_task_assignments_assignment_id_seq, user_task_assignments_assignment_id_seq TO stratagem_std_user;

GRANT SELECT, INSERT, UPDATE, DELETE ON 
notifications, user_notifications TO stratagem_std_user;
GRANT USAGE, SELECT, UPDATE ON 
notifications_notification_id_seq, user_notifications_user_notification_id_seq TO stratagem_std_user;