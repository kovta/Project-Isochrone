<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.catalog.ProjectStatusRepresentor" %>
<%@ page import="com.kota.stratagem.weblayer.common.FormValue" %>
<%@ page import="com.kota.stratagem.weblayer.common.project.ProjectListAttribute" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Projects</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<br/><br/>
	
	<c:choose>
	    <c:when test="${requestScope.projects.isEmpty()}">
            <div class="divider-new">
		        <h2 class="h2-responsive wow fadeIn">No Projects have been initiated</h2>
		    </div><br/>
        </c:when>
        <c:otherwise>
    		<div class="divider-new">
		        <h2 class="h2-responsive wow fadeIn">List of Projects</h2>
		    </div><br/>
        </c:otherwise>
    </c:choose>
		
	<div class="wrapper">
		<div class="container">
			<c:forEach items="${requestScope.parentObjectives}" var="parentObjective">
				<h2 class="icon-companion"><a href="Objective?id=<c:out value="${parentObjective.id}" />">${parentObjective.name}</a></h2>				
				<table class="table table-hover fixed-table-layout">
					<colgroup>
						<col span="1" style="width: 3%;">
						<col span="1" style="width: 44%;">
						<col span="1" style="width: 25%;">
						<col span="1" style="width: 12%;">
						<col span="1" style="width: 8%;">
						<col span="1" style="width: 8%;">
				    </colgroup>
				    <thead>
				        <tr>
				        	<th>#</th>
					        <th>Name</th>
					        <th class="text-center">Status</th>
					        <th class="text-center">Submodules</th>
					        <th class="text-center">Tasks</th>
					        <th class="text-center">Actions</th>
				        </tr>
				    </thead>
				    <tbody>
				    	<c:set var="count" value="0" scope="page"></c:set>
						<c:forEach items="${parentObjective.projects}" var="project">
							<c:set var="count" value="${count + 1}" scope="page"/>
		                        <tr>
		                        	<th scope="row"><c:out value="${count}" /></th>
		                            <td><c:out value="${project.name}" /></td>
		                            <td class="text-center">
										<c:choose>
											<c:when test="${project.status.label == 'Proposed'}"><i class="fa fa-share-square-o"></i></c:when>
											<c:when test="${project.status.label == 'Pending'}"><i class="fa fa-ellipsis-h"></i></c:when>
											<c:when test="${project.status.label == 'Initiated'}"><i class="fa fa-arrow-circle-right"></i></c:when>
											<c:when test="${project.status.label == 'Under analysis'}"><i class="fa fa-pie-chart"></i></c:when>
											<c:when test="${project.status.label == 'In design'}"><i class="fa fa-crop"></i></c:when>
											<c:when test="${project.status.label == 'In development'}"><i class="fa fa-cogs"></i></c:when>
											<c:when test="${project.status.label == 'Canceled'}"><i class="fa fa-stop-circle-o"></i></c:when>
											<c:when test="${project.status.label == 'Testing'}"><i class="fa fa-flask"></i></c:when>
											<c:when test="${project.status.label == 'Validating'}"><i class="fa fa-bar-chart"></i></c:when>
											<c:when test="${project.status.label == 'Deploying'}"><i class="fa fa-arrow-circle-o-up"></i></c:when>
											<c:when test="${project.status.label == 'Implementing'}"><i class="fa fa-factory"></i></c:when>
											<c:when test="${project.status.label == 'Integrating'}"><i class="fa fa-cubes"></i></c:when>
											<c:when test="${project.status.label == 'Live'}"><i class="fa fa-feed"></i></c:when>
											<c:when test="${project.status.label == 'Maintained by operations'}"><i class="fa fa-dashboard"></i></c:when>
											<c:when test="${project.status.label == 'Upgrading'}"><i class="fa fa-angle-double-up"></i></c:when>
											<c:when test="${project.status.label == 'Disposed'}"><i class="fa fa-level-down"></i></c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
		                            	<span class="icon-companion"><c:out value="${project.status.label}" /></span>
		                            </td>
		                            <td class="text-center"><c:out value="${project.submodules.size()}" /></td>
		                            <td class="text-center"><c:out value="${project.tasks.size()}" /></td>
		                            <td class="text-center">
			                            <a href="Project?id=<c:out value="${project.id}" />"><i class="fa fa-wpforms" aria-hidden="true"></i></a>
		                            	<% if (request.isUserInRole("department_manager") || request.isUserInRole("central_manager") || request.isUserInRole("general_manager")) { %>
										<a href="Project?id=<c:out value="${project.id}" />&edit=1"><i class="fa fa-edit"  aria-hidden="true"></i></a>
		                            	<% } %>
		                            </td>
		                        </tr>
						</c:forEach>
				    </tbody>
				</table>
				<br/>
			</c:forEach>
			
			<!-- Modals -->
			<jsp:include page="project-alert.jsp"></jsp:include>
			<!-- /Modals -->
			
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>