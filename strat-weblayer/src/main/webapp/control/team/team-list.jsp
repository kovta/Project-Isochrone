<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Teams</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:include page="../../partial/authority.jsp"></jsp:include>
	<br/><br/>
	
	<c:choose>
	    <c:when test="${requestScope.teams.isEmpty()}">
            <div class="divider-new">
		        <h2 class="h2-responsive wow fadeIn">No Teams defined yet</h2>
		    </div>
        </c:when>
        <c:otherwise>
    		<div class="divider-new">
		        <h2 class="h2-responsive wow fadeIn">List of Teams</h2>
		    </div>
        </c:otherwise>
    </c:choose>
    
	<div class="wrapper">
		<div class="container">
			<c:if test="${isCentralManager or isDepartmentManager or isGeneralManager}">
			    <button type="button" class="btn mdb-color darken-1" data-toggle="modal" data-target="#addTeam">
			    	<i class="fa fa-group right"></i><span class="icon-companion"> Assemble new Team</span>
				</button>
			</c:if>
			<div class="small-padder"></div>
			<div class="card">
				<div class="card-block">
					<table class="table table-hover table-responsive">
						<colgroup>
							<col span="1" style="width: 3%;">
							<col span="1" style="width: 32%;">
							<col span="1" style="width: 23%;">
							<col span="1" style="width: 7%;">
							<col span="1" style="width: 7%;">
							<col span="1" style="width: 7%;">
							<col span="1" style="width: 7%;">
							<col span="1" style="width: 7%;">
							<col span="1" style="width: 7%;">
					    </colgroup>
					    <thead>
					        <tr>
						        <th>#</th>
						        <th>Name</th>
						        <th class="text-center">Team leader</th>
						        <th class="text-center">Members</th>
						        <th class="text-center">Assigned Objectives</th>
						        <th class="text-center">Assigned Projects</th>
						        <th class="text-center">Assigned Submodules</th>
						        <th class="text-center">Assigned Tasks</th>
						        <th class="text-center">Actions</th>
					        </tr>
					    </thead>
					    <tbody>
					    	<c:set var="count" value="0" scope="page" />
							<c:forEach items="${requestScope.teams}" var="team">
									<c:set var="count" value="${count + 1}" scope="page"/>
			                        <tr>
			                        	<th scope="row"><c:out value="${count}" /></th>
			                            <td><c:out value="${team.name}" /></td>
			                            <td class="text-center"><a href="User?id=<c:out value="${team.leader.id}" />"><c:out value="${team.leader.name}" /></a></td>
			                            <td class="text-center"><c:out value="${team.members.size()}" /></td>
			                            <td class="text-center"><c:out value="${team.objectives.size()}" /></td>
			                            <td class="text-center"><c:out value="${team.projects.size()}" /></td>
			                            <td class="text-center"><c:out value="${team.submodules.size()}" /></td>
			                            <td class="text-center"><c:out value="${team.tasks.size()}" /></td>
			                            <td class="text-center">
				                            <a href="Team?id=<c:out value="${team.id}" />"><i class="fa fa-wpforms" aria-hidden="true"></i></a>
			                            	<c:if test="${operator eq team.name}">
												<a href="Team?id=<c:out value="${team.id}" />&edit=1"><i class="fa fa-edit" aria-hidden="true"></i></a>
			                            	</c:if>
			                            </td>
			                        </tr>
							</c:forEach>
					    </tbody>
					</table>
				</div>
			</div>
			
			<!-- Modals -->
			<jsp:include page="team-create.jsp"></jsp:include>
			<jsp:include page="team-alert.jsp"></jsp:include>
			<jsp:include page="../../modal/logout.jsp"></jsp:include>
			<!-- /Modals -->
			
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>