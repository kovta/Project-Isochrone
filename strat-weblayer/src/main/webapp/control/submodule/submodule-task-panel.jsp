<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="tab-pane fade active show" id="taskPanel" role="tabpanel" aria-expanded="false">
	<c:choose>
		<c:when test="${submodule.tasks.size() == 0}">
			<div class="row wow fadeIn" data-wow-delay="0.2s">
            	<div class="col-lg-12">
                	<div class="text-center content-padder">
                    	<h2 class="h2-responsive">There are currently no Tasks</h2>
                        <h2 class="h2-responsive">defined under this Submodule</h2>
                    </div>
	      		</div>
    		</div>
		</c:when>
		<c:otherwise>
			<c:if test="${requestScope.submodule.overdueTasks.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
    				<div class="col-lg-12">
                    	<div class="divider-new">
                        	<h2 class="h4-responsive wow fadeIn"><c:out value="Overdue (${requestScope.submodule.overdueTasks.size()})" /></h2>
						</div>
                    </div>
				</div>
				<div class="row">
					<c:forEach items="${requestScope.submodule.overdueTasks}" var="taskItem">
						<div class="col-lg-4">
                        	<c:set var="task" value="${taskItem}" scope="request" />
                           	<jsp:include page="../task/task-card.jsp"></jsp:include>
                       </div>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${requestScope.submodule.ongoingTasks.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
					<div class="col-lg-12">
                    	<div class="divider-new">
                        	<h2 class="h4-responsive wow fadeIn"><c:out value="In Progress (${requestScope.submodule.ongoingTasks.size()})" /></h2>
                        </div>
                    </div>
                </div>
				<div class="row">
					<c:forEach items="${requestScope.submodule.ongoingTasks}" var="taskItem">
						<div class="col-lg-4">
	                		<c:set var="task" value="${taskItem}" scope="request" />
	                        <jsp:include page="../task/task-card.jsp"></jsp:include>
	                    </div>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${requestScope.submodule.completedTasks.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
                	<div class="col-lg-12">
                    	<div class="divider-new">
                        	<h2 class="h4-responsive wow fadeIn"><c:out value="Completed (${requestScope.submodule.completedTasks.size()})" /></h2>
                        </div>
                    </div>
                </div>
				<div class="row">
					<c:forEach items="${requestScope.submodule.completedTasks}" var="taskItem">
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