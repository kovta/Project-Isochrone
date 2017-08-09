<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--Card-->
<br/><br/><br/>
<div class="card wow fadeIn" data-wow-delay="0.2s">
    <!--Card content-->
	<div class="card-block">
		<div class="card-avatar">
			<img class="rounded-circle img-responsive" src="https://www.filepicker.io/api/file/9dXFgbwRRlKXzHDItGEK" 
			style="border-bottom-left-radius: 50%; border-bottom-right-radius: 50%;">
		</div>
   		<!--Title-->
		<h4 class="card-title text-center"><c:out value="${assignment.recipient.name}" /></h4>
		<hr/>
		<!--Text-->
		<p class="card-text">Assigned by: <c:out value="${assignment.entrustor.name}" /></p>
		<p class="card-text">Assignment date: 
			<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd" />
		</p>
		<div class="full-width text-center">
			<a href="AppUserAssignmentDelete?id=<c:out value="${assignment.id}" />&objectiveId=<c:out value="${objective.id}" />">Unassign user</a>
    	</div>
	</div>
	<!--/.Card content-->
</div>
<br/><br/><br/><br/><br/>
<!--/.Card-->