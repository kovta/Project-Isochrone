<% request.setAttribute("isCentralManager", request.isUserInRole("central_manager")); %>
<% request.setAttribute("isDepartmentManager", request.isUserInRole("department_manager")); %>
<% request.setAttribute("isGeneralManager", request.isUserInRole("general_manager")); %>
<% request.setAttribute("isGeneralUser", request.isUserInRole("general_user")); %>