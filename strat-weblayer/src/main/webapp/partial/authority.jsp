<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="operator" value="${pageContext.request.remoteUser}" scope="request" />
<% request.setAttribute("isCentralManager", request.isUserInRole("central_manager")); %>
<% request.setAttribute("isDepartmentManager", request.isUserInRole("department_manager")); %>
<% request.setAttribute("isGeneralManager", request.isUserInRole("general_manager")); %>
<% request.setAttribute("isGeneralUser", request.isUserInRole("general_user")); %>
<% request.setAttribute("isPristineUser", request.isUserInRole("pristine_user")); %>