<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Card-->
<div class="card wow fadeIn" data-wow-delay="0.2s">
    <!--Card content-->
	<div class="card-block">
		<div class="card-avatar">
		 	<c:set var="selector" value="${user.imageSelector}" scope="request"/>
			<jsp:include page="../user/user-avatar-selector.jsp"></jsp:include>
		</div>
		<!--Title-->
		<h4 class="text-center">
			<a href="User?id=<c:out value="${user.id}" />"><c:out value="${user.name}" /></a>
		</h4>
		<hr/>
		<p class="text-center grey-text">${user.role.label}</p>
		<c:if test="${operator eq requestScope.team.leader.name or operator eq requestScope.team.creator.name}">
			<hr/>
			<div class="full-width text-center">
				<a href="TeamMembershipDelete?id=<c:out value="${team.id}" />
					&member=<c:out value="${user.name}" />">Remove Team Member</a>
	    	</div>
    	</c:if>
	</div>
<!--/.Card content-->
</div>
<!--/.Card-->