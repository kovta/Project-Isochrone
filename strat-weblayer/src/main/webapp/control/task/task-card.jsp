<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${task.completion eq 100 or task.urgencyLevel eq 3}">
	<br/>
</c:if>
<!--Card-->
<div class="card wow fadeIn" data-wow-delay="0.2s">
    <!--Card content-->
	<div class="card-block">
		<jsp:include page="task-card-content.jsp"></jsp:include>
	</div>
	<!--/.Card content-->
</div>
<br/>
<!--/.Card-->