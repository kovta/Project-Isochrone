<%@page import="com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor"%>
<%@page import="com.kota.stratagem.weblayer.common.FormValue"%>
<%@page import="com.kota.stratagem.weblayer.common.project.ProjectListAttribute"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="style/page.css" />
<title>:: Projects ::</title>
</head>
<body>
    <h1>List of Projects</h1>
    <div>
    	<% String userName = (String) request.getUserPrincipal().getName(); %>
	    Welcome <strong><%= userName %></strong>! <a href="Logout">Logout</a>
	</div>
	<br/><br/>
	<form method="post" action="ProjectList">
		<div>
			<label for="status">Status: </label>
			<select name="status" id="status">
			    <% String statusName = (String) request.getAttribute(ProjectListAttribute.ATTR_STATUS); %>
				<option value="-1" <% out.print( FormValue.FILTER_ALL_CATEGORY.equals(statusName) ? "selected=\"selected\"" : "" ); %>>-</option>
			    <c:set var="statusValues" value="<%= ProjectStatusRepresentor.values() %>"/>
				<c:forEach items="${statusValues}" var="status">
					<option value="${status.name}" ${status.name eq requestScope.status ? "selected=\"selected\"" : ""}>${status.label}</option>
				</c:forEach>
			</select>
			<input type="submit" value="Filter" />
		</div>
	</form>
	<br/><br/>
	<c:choose>
	    <c:when test="${requestScope.projects.isEmpty()}">
            <span>Project list is <strong>empty</strong>.</span>
        </c:when>
        <c:otherwise>
            <table class="projectstable">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Tasks</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.projects}" var="project">
                        <tr>
                            <td><c:out value="${project.name}" /></td>
                            <td><c:out value="${project.status.label}" /></td>
                            <td><c:out value="${project.tasks.size()}" /></td>
                            <td><a href="ProjectAction?id=<c:out value="${project.id}" />">Details</a></td>
                            <td><a href="ProjectAction?id=<c:out value="${project.id}" />&edit=1">Edit</a></td>
                            <td><a href="ProjectDelete?id=<c:out value="${project.id}" />">Delete</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br/>
        </c:otherwise>
	</c:choose>
	<br/><br/>
	<div>
		<a href="ProjectAction?id=-1&edit=1">Create</a> new project.
	</div>
	<% if (request.isUserInRole("department_manager")) { %>
		<div>
		    Projects are up to date.
		</div>
	<% } %>
</body>
</html>