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
	<jsp:include page="../../partial/authority.jsp"></jsp:include>
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
				<div class="card">
					<div class="card-block">
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
					                            <c:set var="status" value="${project.status.label}" scope="request"/>
					                            <jsp:include page="project-status-icon-selector.jsp"></jsp:include>
					                            <span class="icon-companion"><c:out value="${project.status.label}" /></span>
				                            </td>
				                            <td class="text-center"><c:out value="${project.submodules.size()}" /></td>
				                            <td class="text-center"><c:out value="${project.tasks.size()}" /></td>
				                            <td class="text-center">
					                            <a href="Project?id=<c:out value="${project.id}" />"><i class="fa fa-wpforms" aria-hidden="true"></i></a>
				                            	<c:if test="${operator eq project.creator.name}">
													<a href="Project?id=<c:out value="${project.id}" />&edit=1"><i class="fa fa-edit"  aria-hidden="true"></i></a>
				                            	</c:if>
				                            </td>
				                        </tr>
								</c:forEach>
						    </tbody>
						</table>
					</div>
				</div>	
				<br/>
			</c:forEach>
			
			<!-- Modals -->
			<jsp:include page="project-alert.jsp"></jsp:include>
			<jsp:include page="../../modal/logout.jsp"></jsp:include>
			<!-- /Modals -->
			
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>