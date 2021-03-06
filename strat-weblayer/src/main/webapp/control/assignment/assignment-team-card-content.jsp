<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
																
 <!--Title-->
<h4 class="text-center">
	<a href="Team?id=<c:out value="${assignmentItem.recipient.id}" />"><c:out value="${assignmentItem.recipient.name}" /></a>
</h4>
<hr/>
<!--Text-->
<p class="card-text">Assigned by: 
	<a href="User?id=<c:out value="${assignmentItem.entrustor.id}" />"><c:out value="${assignmentItem.entrustor.name}" /></a>
</p>
<p class="card-text">Date: <fmt:formatDate type="date" value="${assignmentItem.creationDate}" pattern="yyyy-MM-dd hh:mm" /></p>