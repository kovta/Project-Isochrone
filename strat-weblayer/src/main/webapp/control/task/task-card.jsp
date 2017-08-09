<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--Card-->
<div class="card wow fadeIn" data-wow-delay="0.2s">
    <!--Card content-->
	<div class="card-block">
		<c:if test="${task.completion == 100}">
			<div class="form-header success-color success-header">
		       	<i class="fa fa-check-circle"></i>
	        </div>
	    </c:if>
	    <!--Title-->
	    <h4 class="card-title"><c:out value="${task.name}" /></h4>
		<hr/>
		<!--Text-->
		<c:if test="${not empty task.deadline}">
	          	<p class="card-text">
	          		Deadline:
	          		<c:choose>
					<c:when test="${task.urgencyLevel == 3}">
						<span class="danger-text">
							<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${task.urgencyLevel == 2}">
						<span class="heavy-warning-text">
							<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${task.urgencyLevel == 1}">
						<span class="warning-text">
							<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
				    <c:otherwise>
				     	<span class="success-text">
				     		<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
				       	</span>
				    </c:otherwise>
				</c:choose>
			</p>
		<hr/>
	    </c:if>
	    <p class="card-text"><c:out value="${task.completion} Completed" />%</p>
		<a href="Task?id=<c:out value="${task.id}" />" class="btn btn-primary">Inspect task</a>
	</div>
<!--/.Card content-->
</div>
<br/>
<!--/.Card-->