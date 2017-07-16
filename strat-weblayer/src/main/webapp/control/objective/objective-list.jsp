<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Objectives</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<br/><br/>
	
	<c:choose>
	    <c:when test="${requestScope.objectives.isEmpty()}">
            <div class="divider-new">
		        <h2 class="h2-responsive wow fadeIn">No Objectives have been set</h2>
		    </div>
        </c:when>
        <c:otherwise>
    		<div class="divider-new">
		        <h2 class="h2-responsive wow fadeIn">List of Objectives</h2>
		    </div>    
        </c:otherwise>
    </c:choose>
    
	<div class="wrapper">
		<div class="container">
	       	<% if (request.isUserInRole("department_manager") || request.isUserInRole("central_manager")) { %>
			<div class="space-bottom">
			    <button type="button" class="btn mdb-color darken-1" data-toggle="modal" data-target="#addObjective">
			    	<i class="fa fa-plus right"></i><span class="icon-companion"> Set new Objective</span>
				</button>
			</div>
			<% } %>
			<table class="table table-hover">
				<colgroup>
					<col span="1" style="width: 3%;">
					<col span="1" style="width: 45%;">
					<col span="1" style="width: 15%;">
					<col span="1" style="width: 8%;">
					<col span="1" style="width: 8%;">
					<col span="1" style="width: 8%;">
					<col span="1" style="width: 8%;">
			    </colgroup>
			    <thead>
			        <tr>
				        <th>#</th>
				        <th>Name</th>
				        <th class="center-text">Status</th>
				        <th class="center-text">Priority</th>
				        <th class="center-text">Projects</th>
				        <th class="center-text">Tasks</th>
				        <th class="center-text">Actions</th>
			        </tr>
			    </thead>
			    <tbody>
			    	<c:set var="count" value="0" scope="page" />
					<c:forEach items="${requestScope.objectives}" var="objective">
							<c:set var="count" value="${count + 1}" scope="page"/>
	                        <tr>
	                        	<th scope="row"><c:out value="${count}" /></th>
	                            <td><c:out value="${objective.name}" /></td>
	                            <td class="center-text"><c:out value="${objective.status.label}" /></td>
	                            <td class="center-text"><c:out value="${objective.priority}" /></td>
	                            <td class="center-text"><c:out value="${objective.projects.size()}" /></td>
	                            <td class="center-text"><c:out value="${objective.tasks.size()}" /></td>
	                            <td class="center-text">
		                            <a href="ObjectiveAction?id=<c:out value="${objective.id}" />"><i class="fa fa-wpforms" aria-hidden="true"></i></a>
	                            	<% if (request.isUserInRole("department_manager") || request.isUserInRole("central_manager")) { %>
									<a href="ObjectiveAction?id=<c:out value="${objective.id}" />&edit=1"><i class="fa fa-edit"  aria-hidden="true"></i></a>
		                            <a href="ObjectiveDelete?id=<c:out value="${objective.id}" />"><i class="fa fa-trash"  aria-hidden="true"></i></a>
	                            	<% } %>
	                            </td>
	                        </tr>
					</c:forEach>
			    </tbody>
			</table>
			
			<!-- Modals -->
			<jsp:include page="objective-create.jsp"></jsp:include>
			<jsp:include page="../../partial/alert.jsp"></jsp:include>
			<!-- /Modals -->
			
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>