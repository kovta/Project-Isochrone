<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${task.completion eq 100}">
	<div class="form-header success-color card-alert-header">
       	<i class="fa fa-check-circle"></i>
    </div>
</c:if>
<c:if test="${task.completion ne 100 and task.urgencyLevel eq 3}">
	<div class="form-header danger-color card-alert-header">
       	<i class="fa fa-exclamation-triangle"></i>
    </div>
</c:if>
<!--Title-->
<h4 class="card-title"><c:out value="${task.name}" /></h4>
<hr/>
<!--Text-->
<c:if test="${not empty task.deadline or task.isEstimated() or task.isDurationProvided()}">
	<c:if test="${not empty task.deadline}">
		<p class="card-text">
			Deadline:
			<c:choose>
				<c:when test="${task.urgencyLevel eq 3 and task.completion ne 100}">
					<span class="danger-text">
						<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
					</span>
				</c:when>
				<c:when test="${task.urgencyLevel eq 2 and task.completion ne 100}">
					<span class="heavy-warning-text">
						<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
					</span>
				</c:when>
				<c:when test="${task.urgencyLevel eq 1 and task.completion ne 100}">
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
	</c:if>
	<c:if test="${task.isDurationProvided()}">
		<p class="card-text">
			Duration: <fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${task.pessimistic}" /><c:out value="${task.duration eq 1 ? ' day' : ' days'}" />
		</p>
	</c:if>
	<c:if test="${task.isEstimated()}">
		<p class="card-text ">Duration: Estimated</p>
		<p class="card-text" style="margin-bottom: 2px;">
			Pessimistic: <fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${task.pessimistic}" /><c:out value="${task.pessimistic eq 1 ? ' day' : ' days'}" />
		</p>
		<p class="card-text" style="margin-bottom: 2px;">
			Realistic: <fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${task.realistic}" /><c:out value="${task.realistic eq 1 ? ' day' : ' days'}" />
		</p>
		<p class="card-text" style="margin-bottom: 2px;">
			Optimistic: <fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${task.optimistic}" /><c:out value="${task.optimistic eq 1 ? ' day' : ' days'}" />
		</p>
	</c:if>
	<hr/>
</c:if>
<p class="card-text"><c:out value="${task.completion} Completed" />%</p>
<div class="full-width text-center">
	<a href="Task?id=<c:out value="${task.id}" />" class="btn btn-primary">Inspect Task</a>
</div>
