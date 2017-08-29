<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Users</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:include page="../../partial/authority.jsp"></jsp:include>
	<br/><br/>
	
	<c:choose>
	    <c:when test="${requestScope.users.isEmpty()}">
            <div class="divider-new">
		        <h2 class="h2-responsive wow fadeIn">Workforce not yet defined</h2>
		    </div>
        </c:when>
        <c:otherwise>
    		<div class="divider-new">
		        <h2 class="h2-responsive wow fadeIn">Defined Workforce</h2>
		    </div>
        </c:otherwise>
    </c:choose>
    
	<div class="wrapper">
		<div class="container">
			<div class="small-padder"></div>
			<div class="card">
				<div class="card-block">
					<table class="table table-hover">
						<colgroup>
							<col span="1" style="width: 3%;">
							<col span="1" style="width: 5%;">
							<col span="1" style="width: 10%;">
							<col span="1" style="width: 23%;">
							<col span="1" style="width: 24%;">
							<col span="1" style="width: 7%;">
							<col span="1" style="width: 7%;">
							<col span="1" style="width: 7%;">
							<col span="1" style="width: 7%;">
							<col span="1" style="width: 7%;">
					    </colgroup>
					    <thead>
					        <tr>
						        <th>#</th>
						        <th></th>
						        <th>Name</th>
						        <th class="text-center">Email Address</th>
						        <th class="text-center">Role</th>
						        <th class="text-center">Assigned Objectives</th>
						        <th class="text-center">Assigned Projects</th>
						        <th class="text-center">Assigned Submodules</th>
						        <th class="text-center">Assigned Tasks</th>
						        <th class="text-center">Actions</th>
					        </tr>
					    </thead>
					    <tbody>
					    	<c:set var="count" value="0" scope="page" />
							<c:forEach items="${requestScope.users}" var="user">
									<c:set var="count" value="${count + 1}" scope="page"/>
			                        <tr>
			                        	<th scope="row"><c:out value="${count}" /></th>
			                        	<td>						
			                        		<img class="line-avatar rounded-circle img-responsive" src="https://www.filepicker.io/api/file/9dXFgbwRRlKXzHDItGEK" 
												style="border-bottom-left-radius: 50%; border-bottom-right-radius: 50%;">
										</td>
			                            <td><c:out value="${user.name}" /></td>
			                            <td class="text-center"><c:out value="${empty user.email ? 'Not specified' : user.email}" /></td>
			                            <td class="text-center"><c:out value="${user.role.label}" /></td>
			                            <td class="text-center"><c:out value="${user.objectives.size()}" /></td>
			                            <td class="text-center"><c:out value="${user.projects.size()}" /></td>
			                            <td class="text-center"><c:out value="${user.submodules.size()}" /></td>
			                            <td class="text-center"><c:out value="${user.tasks.size()}" /></td>
			                            <td class="text-center">
				                            <a href="User?id=<c:out value="${user.id}" />"><i class="fa fa-wpforms" aria-hidden="true"></i></a>
			                            	<c:if test="${operator eq user.name}">
												<a href="User?id=<c:out value="${user.id}" />&edit=1"><i class="fa fa-edit" aria-hidden="true"></i></a>
			                            	</c:if>
			                            </td>
			                        </tr>
							</c:forEach>
					    </tbody>
					</table>
				</div>
			</div>
			
			<!-- Modals -->
			<jsp:include page="../../modal/logout.jsp"></jsp:include>
			<!-- /Modals -->
			
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>