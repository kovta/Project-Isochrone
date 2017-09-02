<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="tab-pane fade" id="taskPanel" role="tabpanel" aria-expanded="false">
	<c:choose>
		<c:when test="${project.tasks.size() == 0}">
			<div class="row wow fadeIn" data-wow-delay="0.2s">
            	<div class="col-lg-12">
            		<div class="text-center content-padder">
            			<h2 class="h2-responsive">There are currently no Tasks</h2>
            			<h2 class="h2-responsive">defined under this project</h2>
            		</div>
            	</div>
			</div>
		</c:when>
		<c:otherwise>
			<c:if test="${requestScope.project.overdueTasks.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
                    <div class="col-lg-12">
						<div class="divider-new">
                        	<h2 class="h4-responsive wow fadeIn"><c:out value="Overdue (${requestScope.project.overdueTasks.size()})" /></h2>
                    	</div>
                 	</div>
                </div>
				<div class="row">
					<c:forEach items="${requestScope.project.overdueTasks}" var="taskItem">
						<div class="col-lg-4">
	                		<c:set var="task" value="${taskItem}" scope="request" />
	                        <jsp:include page="../task/task-card.jsp"></jsp:include>
	                    </div>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${requestScope.project.ongoingTasks.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
					<div class="col-lg-12">
                    	<div class="divider-new">
                        	<h2 class="h4-responsive wow fadeIn"><c:out value="In Progress (${requestScope.project.ongoingTasks.size()})" /></h2>
						</div>
                    </div>
               	</div>
				<div class="row">
					<c:forEach items="${requestScope.project.ongoingTasks}" var="taskItem">
						<div class="col-lg-4">
	                    	<c:set var="task" value="${taskItem}" scope="request" />
	                        <jsp:include page="../task/task-card.jsp"></jsp:include>
	                    </div>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${requestScope.project.completedTasks.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
                	<div class="col-lg-12">
                    	<div class="divider-new">
                        	<h2 class="h4-responsive wow fadeIn"><c:out value="Completed (${requestScope.project.completedTasks.size()})" /></h2>
                        </div>
                    </div>
                </div>
				<div class="row">
					<c:forEach items="${requestScope.project.completedTasks}" var="taskItem">
						<div class="col-lg-4">
                        	<c:set var="task" value="${taskItem}" scope="request" />
                            <jsp:include page="../task/task-card.jsp"></jsp:include>
                       </div>
					</c:forEach>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>
