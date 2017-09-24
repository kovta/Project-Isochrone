<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Card-->
<div class="card wow fadeIn" data-wow-delay="0.2s">
    <!--Card content-->
	<div class="card-block">
		<!--Title-->
		<h4 class="text-center">
			<c:out value="${team.name}" />
		</h4>
		<hr/>
		<p class="card-text">Team Leader: 
			<a href="User?id=<c:out value="${team.leader.id}" />"><c:out value="${team.leader.name}" /></a>
		</p>
		<div class="full-width text-center">
			<a href="Team?id=<c:out value="${team.id}" />" class="btn btn-primary">Inspect Team</a>
		</div>
	</div>
<!--/.Card content-->
</div>
<br/>
<!--/.Card-->