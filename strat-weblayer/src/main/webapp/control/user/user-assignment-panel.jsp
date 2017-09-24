<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
	<c:when test="${requestScope.user.objectives.size() == 0 and requestScope.user.projects.size() == 0 
						and requestScope.user.submodules.size() == 0 and requestScope.user.tasks.size() == 0}">	
		<c:choose>
			<c:when test="${requestScope.operatorAccount}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
				    <div class="col-lg-12">
						<div class="text-center content-padder">
							<h2 class="h2-responsive">You currently have</h2>
							<h2 class="h2-responsive">no Assignments</h2>
						</div>
					</div>
				</div>
			</c:when>
		   <c:otherwise>
				<div class="row wow fadeIn" data-wow-delay="0.2s">
				    <div class="col-lg-12">
						<div class="text-center content-padder">
							<h2 class="h2-responsive">This User currently has</h2>
							<h2 class="h2-responsive">no Assignments</h2>
						</div>
					</div>
				</div>
	   	   </c:otherwise>
       </c:choose>
	</c:when>
	<c:otherwise>
		<c:if test="${requestScope.user.objectives.size() != 0}">
			<div class="row wow fadeIn" data-wow-delay="0.2s">
	  			<div class="col-lg-12">
	                <div class="divider-new">
	            		<h2 class="h4-responsive wow fadeIn"><c:out value="Objective Assignments (${requestScope.user.objectives.size()})" /></h2>
					</div>
				</div>
			</div>
		</c:if>
		<div class="row">
			<c:forEach items="${requestScope.user.objectives}" var="assignment">
				<div class="col-lg-4">
                    <div class="card wow fadeIn" data-wow-delay="0.2s">
						<div class="card-block">
							<h4 class="card-title"><c:out value="${assignment.objective.name}" /></h4>
							<hr/>
							<p class="card-text">Assigned by: 
								<a href="User?id=<c:out value="${assignment.entrustor.id}" />">
									<c:out value="${assignment.entrustor.name}" />
								</a>		
							</p>
							<p class="card-text">Date: 
								<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd hh:mm" />
							</p>
							<div class="full-width text-center">
								<a href="Objective?id=<c:out value="${assignment.objective.id}" />" class="btn btn-primary">Inspect Objective</a>
							</div>
						</div>
					</div>
					<br/>
				</div>
			</c:forEach>
		</div>
		<c:if test="${requestScope.user.projects.size() != 0}">
			<div class="row wow fadeIn" data-wow-delay="0.2s">
	  			<div class="col-lg-12">
	                <div class="divider-new">
	            		<h2 class="h4-responsive wow fadeIn"><c:out value="Project Assignments (${requestScope.user.projects.size()})" /></h2>
					</div>
				</div>
			</div>
		</c:if>
		<div class="row">
			<c:forEach items="${requestScope.user.projects}" var="assignment">
				<div class="col-lg-4">
					<div class="card wow fadeIn" data-wow-delay="0.2s">
						<div class="card-block">
							<h4 class="card-title"><c:out value="${assignment.project.name}" /></h4>
							<hr/>
							<p class="card-text">Assigned by: 
								<a href="User?id=<c:out value="${assignment.entrustor.id}" />">
									<c:out value="${assignment.entrustor.name}" />
								</a>
							</p>
							<p class="card-text">Date: 
								<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd hh:mm" />
							</p>
							<div class="full-width text-center">
								<a href="Project?id=<c:out value="${assignment.project.id}" />" class="btn btn-primary">Inspect Project</a>
							</div>
						</div>
					</div>
					<br/>				                    
				</div>
			</c:forEach>
		</div>
		<c:if test="${requestScope.user.submodules.size() != 0}">
			<div class="row wow fadeIn" data-wow-delay="0.2s">
	  			<div class="col-lg-12">
	                <div class="divider-new">
	            		<h2 class="h4-responsive wow fadeIn"><c:out value="Submodule Assignments (${requestScope.user.submodules.size()})" /></h2>
					</div>
				</div>
			</div>
		</c:if>
		<div class="row">
			<c:forEach items="${requestScope.user.submodules}" var="assignment">
				<div class="col-lg-4">
					<div class="card wow fadeIn" data-wow-delay="0.2s">
						<div class="card-block">
							<h4 class="card-title"><c:out value="${assignment.submodule.name}" /></h4>
							<hr/>
							<p class="card-text">Assigned by: 
								<a href="User?id=<c:out value="${assignment.entrustor.id}" />">
									<c:out value="${assignment.entrustor.name}" />
								</a>
							</p>
							<p class="card-text">Date: 
								<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd hh:mm" />
							</p>
							<div class="full-width text-center">
								<a href="Submodule?id=<c:out value="${assignment.submodule.id}" />" class="btn btn-primary">Inspect Submodule</a>
							</div>
						</div>
					</div>
					<br/>
				</div>
			</c:forEach>
		</div>
		<c:if test="${requestScope.user.tasks.size() != 0}">
			<div class="row wow fadeIn" data-wow-delay="0.2s">
	  			<div class="col-lg-12">
	                <div class="divider-new">
	            		<h2 class="h4-responsive wow fadeIn"><c:out value="Task Assignments (${requestScope.user.tasks.size()})" /></h2>
					</div>
				</div>
			</div>
		</c:if>
		<div class="row">
			<c:forEach items="${requestScope.user.tasks}" var="assignment">
				<div class="col-lg-4">
                    <div class="card wow fadeIn" data-wow-delay="0.2s">
                        <div class="card-block">
                            <h4 class="card-title"><c:out value="${assignment.task.name}" /></h4>
                            <hr/>
                            <p class="card-text">Assigned by: 
                            	<a href="User?id=<c:out value="${assignment.entrustor.id}" />">
                            		<c:out value="${assignment.entrustor.name}" />
                            	</a>
                            </p>
                            <p class="card-text">Date: 
                            	<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd hh:mm" />
                            </p>
                            <div class="full-width text-center">
								<a href="Task?id=<c:out value="${assignment.task.id}" />" class="btn btn-primary">Inspect Task</a>
							</div>
						</div>
                    </div>
                    <br/>
            	</div>
			</c:forEach>
		</div>		    
	</c:otherwise>
</c:choose>