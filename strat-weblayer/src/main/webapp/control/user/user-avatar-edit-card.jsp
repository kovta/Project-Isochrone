<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-lg-4">
	<br/><br/><br/>
	<div class="card wow fadeIn" data-wow-delay="0.2s">
    	<div class="card-block">
    		<div class="card-avatar">
    			<c:set var="selector" value="${count}" scope="request"/>
				<jsp:include page="user-avatar-selector.jsp"></jsp:include>
			</div>
        	<h4 class="card-title text-center">Avatar <c:out value="${requestScope.count}" /></h4>
            <hr/>
            <div class="full-width text-center">
				<a href="UserImage?id=<c:out value="${user.id}" />&image=<c:out value="${count}" />" class="btn btn-primary">Choose Avatar</a>
			</div>
		</div>
	</div>
	<br/><br/>	
</div>