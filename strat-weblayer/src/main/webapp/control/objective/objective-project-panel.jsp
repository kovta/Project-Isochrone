<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="tab-pane fade active show" id="projectPanel" role="tabpanel" aria-expanded="false">
	<c:choose>
		<c:when test="${objective.projects.size() == 0}">
			<div class="row wow fadeIn" data-wow-delay="0.2s">
            	<div class="col-lg-12">
            		<div class="text-center content-padder">
            			<h2 class="h2-responsive">There are currently no Projects</h2>
            			<h2 class="h2-responsive">defined under this objective</h2>
            		</div>
            	</div>
			</div>
		</c:when>
		<c:otherwise>
			<c:if test="${requestScope.objective.overdueProjects.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
                    <div class="col-lg-12">
						<div class="divider-new">
                        	<h2 class="h4-responsive wow fadeIn"><c:out value="Overdue (${requestScope.objective.overdueProjects.size()})" /></h2>
                    	</div>
                 	</div>
                </div>
				<div class="row">
					<c:forEach items="${requestScope.objective.overdueProjects}" var="projectItem">
						<div class="col-lg-4">
	                		<c:set var="project" value="${projectItem}" scope="request" />
	                        <jsp:include page="../project/project-card.jsp"></jsp:include>
	                    </div>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${requestScope.objective.ongoingProjects.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
					<div class="col-lg-12">
                    	<div class="divider-new">
                        	<h2 class="h4-responsive wow fadeIn"><c:out value="In Progress (${requestScope.objective.ongoingProjects.size()})" /></h2>
						</div>
                    </div>
               	</div>
				<div class="row">
					<c:forEach items="${requestScope.objective.ongoingProjects}" var="projectItem">
						<div class="col-lg-4">
	                    	<c:set var="project" value="${projectItem}" scope="request" />
	                        <jsp:include page="../project/project-card.jsp"></jsp:include>
	                    </div>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${requestScope.objective.completedProjects.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
                	<div class="col-lg-12">
                    	<div class="divider-new">
                        	<h2 class="h4-responsive wow fadeIn"><c:out value="Completed (${requestScope.objective.completedProjects.size()})" /></h2>
                        </div>
                    </div>
                </div>
				<div class="row">
					<c:forEach items="${requestScope.objective.completedProjects}" var="projectItem">
						<div class="col-lg-4">
                        	<c:set var="project" value="${projectItem}" scope="request" />
                            <jsp:include page="../project/project-card.jsp"></jsp:include>
                       </div>
					</c:forEach>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>
