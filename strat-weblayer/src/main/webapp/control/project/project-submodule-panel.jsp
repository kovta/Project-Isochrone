<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="tab-pane fade active show" id="submodulePanel" role="tabpanel" aria-expanded="true">
	<c:choose>
	     <c:when test="${project.submodules.size() == 0}">
			<div class="row wow fadeIn" data-wow-delay="0.2s">
	            <div class="col-lg-12">
					<div class="text-center content-padder">
						<h2 class="h2-responsive">There are currently no Submodules</h2>
						<h2 class="h2-responsive">defined under this Project</h2>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<c:if test="${requestScope.project.overdueSubmodules.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
                    <div class="col-lg-12">
                        <div class="divider-new">
                            <h2 class="h4-responsive wow fadeIn"><c:out value="Overdue (${requestScope.project.overdueSubmodules.size()})" /></h2>
                        </div>
                    </div>
                </div>
				<div class="row">
					<c:forEach items="${requestScope.project.overdueSubmodules}" var="submoduleItem">
						<div class="col-lg-4">
	                        <c:set var="submodule" value="${submoduleItem}" scope="request" />
	                        <jsp:include page="../submodule/submodule-card.jsp"></jsp:include>
	                    </div>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${requestScope.project.ongoingSubmodules.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
                     <div class="col-lg-12">
                         <div class="divider-new">
                             <h2 class="h4-responsive wow fadeIn"><c:out value="In Progress (${requestScope.project.ongoingSubmodules.size()})" /></h2>
                         </div>
                     </div>
                 </div>
				<div class="row">
					<c:forEach items="${requestScope.project.ongoingSubmodules}" var="submoduleItem">
						<div class="col-lg-4">
	                    	<c:set var="submodule" value="${submoduleItem}" scope="request" />
	                        <jsp:include page="../submodule/submodule-card.jsp"></jsp:include>
	                    </div>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${requestScope.project.completedSubmodules.size() ne 0}">
				<div class="row wow fadeIn" data-wow-delay="0.2s">
                     <div class="col-lg-12">
                         <div class="divider-new">
                             <h2 class="h4-responsive wow fadeIn"><c:out value="Completed (${requestScope.project.completedSubmodules.size()})" /></h2>
                         </div>
                     </div>
                 </div>
				<div class="row">
					<c:forEach items="${requestScope.project.completedSubmodules}" var="submoduleItem">
						<div class="col-lg-4">
						    <c:set var="submodule" value="${submoduleItem}" scope="request" />
	                    	<jsp:include page="../submodule/submodule-card.jsp"></jsp:include>
	                    </div>
					</c:forEach>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>