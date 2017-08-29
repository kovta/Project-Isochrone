package com.kota.stratagem.weblayer.common;

public enum Page {

	HOME("home.jsp", "Home"), //
	ERROR("error.jsp", "Error"), //
	USER_LIST("/control/user/user-list.jsp", "UserList"), //
	USER_VIEW("/control/user/user-view.jsp", "User"), //
	USER_EDIT("/control/user/user-edit.jsp", "User"), //
	OBJECTIVE_LIST("/control/objective/objective-list.jsp", "ObjectiveList"), //
	OBJECTIVE_VIEW("/control/objective/objective-view.jsp", "Objective"), //
	OBJECTIVE_EDIT("/control/objective/objective-edit.jsp", "Objective"), //
	PROJECT_LIST("/control/project/project-list.jsp", "ProjectList"), //
	PROJECT_VIEW("/control/project/project-view.jsp", "Project"), //
	PROJECT_EDIT("/control/project/project-edit.jsp", "Project"), //
	SUBMODULE_VIEW("/control/submodule/submodule-view.jsp", "Submodule"), //
	SUBMODULE_EDIT("/control/submodule/submodule-edit.jsp", "Submodule"), //
	TASK_VIEW("/control/task/task-view.jsp", "Task"), //
	TASK_EDIT("/control/task/task-edit.jsp", "Task"); //

	private final String jspName;
	private final String url;

	public String getJspName() {
		return this.jspName;
	}

	public String getUrl() {
		return this.url;
	}

	private Page(final String jspName, final String url) {
		this.jspName = jspName;
		this.url = url;
	}
}
