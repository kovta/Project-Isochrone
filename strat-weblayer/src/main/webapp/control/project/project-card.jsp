<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--Card-->
<div class="card wow fadeIn" data-wow-delay="0.2s">
    <!--Card content-->
	<div class="card-block">
		<c:if test="${project.urgencyLevel eq 3}">
			<div class="form-header danger-color card-alert-header">
		       	<i class="fa fa-exclamation-triangle"></i>
		    </div>
		</c:if>
	    <!--Title-->
		<h4 class="card-title"><c:out value="${project.name}" /></h4>
		<hr/>
		<!--Text-->
		<c:if test="${not empty project.deadline}">
       		<p class="card-text">
       			Deadline:
           		<c:choose>
					<c:when test="${project.urgencyLevel == 3}">
						<span class="danger-text">
							<fmt:formatDate type="date" value="${project.deadline}" pattern="yyyy-MM-dd" />
						</span>
		  			</c:when>
					<c:when test="${project.urgencyLevel == 2}">
						<span class="heavy-warning-text">
							<fmt:formatDate type="date" value="${project.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${project.urgencyLevel == 1}">
						<span class="warning-text">
							<fmt:formatDate type="date" value="${project.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
		     		<c:otherwise>
				     	<span class="success-text">
				     		<fmt:formatDate type="date" value="${project.deadline}" pattern="yyyy-MM-dd" />
				       	</span>
		       		</c:otherwise>
				</c:choose>
       		</p>
       		<hr/>
       </c:if>
	   <p class="card-text">
		   <c:set var="status" value="${project.status.label}" scope="request"/>
	       <jsp:include page="project-status-icon-selector.jsp"></jsp:include>
	       <span class="icon-companion"><c:out value="${project.status.label}" /></span>
       </p>
       <hr/>
	   <c:choose>
			<c:when test="${project.submodules.size() eq 0}"><p class="card-text"><c:out value="No Submodules defined" /></p></c:when>
			<c:when test="${project.submodules.size() eq 1}"><p class="card-text"><c:out value="${project.submodules.size()} Submodule" /></p></c:when>
			<c:otherwise><p class="card-text"><c:out value="${project.submodules.size()} Submodules" /></p></c:otherwise>
	   </c:choose>
       <c:choose>
			<c:when test="${project.tasks.size() eq 0}"><p class="card-text"><c:out value="No Tasks registered" /></p></c:when>
			<c:when test="${project.tasks.size() eq 1}"><p class="card-text"><c:out value="${project.tasks.size()} Task" /></p></c:when>
			<c:otherwise><p class="card-text"><c:out value="${project.tasks.size()} Tasks" /></p></c:otherwise>
	   </c:choose>
	   <c:if test="${project.submodules.size() ne 0 and project.tasks.size() ne 0}">
	       <p class="card-text"><c:out value="${project.completion}% Completed" /></p>
	   </c:if>
       <div class="full-width text-center">
           <a href="Project?id=<c:out value="${project.id}" />" class="btn btn-primary">Inspect Project</a>
	   </div>
	</div>
	<!--/.Card content-->
</div>
<br/>
<!--/.Card-->