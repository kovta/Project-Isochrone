<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--Card-->
<div class="card wow fadeIn" data-wow-delay="0.2s">
    <!--Card content-->
	<div class="card-block">
		<c:if test="${submodule.completion == 100}">
			<div class="form-header success-color success-header">
	       		<i class="fa fa-check-circle"></i>
	        </div>
	   	</c:if>
       	<!--Title-->
		<h4 class="card-title"><c:out value="${submodule.name}" /></h4>
		<hr/>
		<!--Text-->
		<c:if test="${not empty submodule.deadline}">
           	<p class="card-text">
				Deadline:
				<c:choose>
					<c:when test="${submodule.urgencyLevel == 3}">
						<span class="danger-text">
							<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${submodule.urgencyLevel == 2}">
						<span class="heavy-warning-text">
							<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${submodule.urgencyLevel == 1}">
						<span class="warning-text">
							<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:otherwise>
				     	<span class="success-text">
				     		<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
				       	</span>
					</c:otherwise>
				</c:choose>
           	</p>
       	<hr/>
		</c:if>
		<c:choose>
			<c:when test="${submodule.tasks.size() == 0}">
				<p class="card-text"><c:out value="No tasks registered" /></p>
			</c:when>
			<c:when test="${submodule.tasks.size() == 1}">
				<p class="card-text"><c:out value="${submodule.tasks.size()} Task" /></p>
				<p class="card-text"><c:out value="${submodule.completion}% Completed" /></p>
			</c:when>
			<c:otherwise>
				<p class="card-text"><c:out value="${submodule.tasks.size()} Tasks" /></p>
				<p class="card-text"><c:out value="${submodule.completion}% Completed" /></p>
			</c:otherwise>
		</c:choose>
		<a href="Submodule?id=<c:out value="${submodule.id}" />" class="btn btn-primary">Inspect Submodule</a>
	</div>
	<!--/.Card content-->
</div>
<br/>
<!--/.Card-->