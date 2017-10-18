<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.kota.stratagem.weblayer.common.task.TaskAttribute" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Tasks</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:include page="../../partial/authority.jsp"></jsp:include>
	<jsp:useBean id="task" class="com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor" scope="request" />
	<br/><br/><br/><br/>
	<div class="wrapper">
		<div class="container">
			<div class="row">
                <!--Sidebar-->
                <div class="col-lg-4 wow fadeIn" data-wow-delay="0.1s">
					<br/><br/>
                    <div class="widget-wrapper wow fadeIn" data-wow-delay="0.2s">
                        <div class="card">
                            <div class="card-block">
								<div class="padding-top"><h3 class="h3-responsive text-center">${task.name}</h3></div>
                                <div class="md-form">
									<jsp:include page="task-detail-table.jsp"></jsp:include>
                                </div>
	                        </div>
	                    </div>
						<c:set var="supervisor" value="false" scope="request"/>
						<c:if test="${operator eq task.creator.name or operator eq task.objective.creator.name 
							or operator eq task.project.creator.name or operator eq task.submodule.creator.name 
							or operator eq task.submodule.project.creator.name or operator eq task.submodule.project.objective.creator.name}">
							<c:set var="supervisor" value="true" scope="request"/>
						</c:if>
						<c:set var="assigned" value="false" />
						<c:forEach items="${requestScope.task.assignedUsers}" var="assignment">
	                    	<c:if test="${operator eq assignment.recipient.name}"><c:set var="assigned" value="true" /></c:if>
						</c:forEach>
						<c:forEach items="${requestScope.submodule.assignedTeams}" var="assignment">
	                    	<c:if test="${operator eq assignment.recipient.leader.name}"><c:set var="assigned" value="true" /></c:if>
						</c:forEach>
						<c:if test="${supervisor or assigned or task.admittance}">
		       			    <br/><br/><br/>
		       			    <div class="card">
	                            <div class="card-block">
	                            	<div class="form-header mdb-color darken-1">
	                                	<h5><i class="fa fa-exclamation-circle"></i><span class="icon-companion"> Actions</span></h5>
	                                </div>
	                                <div class="md-form">
	                                	<table class="strat-detail-table">
		                                	<tbody>
		                                		<c:if test="${supervisor or assigned}">
			                                		<tr class="match-row"><td class="text-center">
								  		   			    <a href="Task?id=<c:out value="${task.id}"/>&edit=1" class="vertical-align-middle text-center full-width">
									       			    	<i class="fa fa-edit" aria-hidden="true"></i> Edit Task
									       			    </a>
													</td></tr>
													<tr class="match-row"><td>
														<button type="button" class="btn mdb-color ml-auto darken-1 full-width" data-target="#addDependencies" data-toggle="modal">
													    	<i class="fa fa-share-alt tile-icon"></i><span class="icon-companion">Configure Dependencies</span>
														</button>
													</td></tr>
												</c:if>
												<c:if test="${task.admittance and not assigned}">
													<c:set var="task" value="${requestScope.task}" scope="request" />
													<tr class="match-row"><td>
														<button type="button" class="btn mdb-color ml-auto darken-1 full-width" data-target="#participateTask" data-toggle="modal">
													    	<i class="fa fa-ticket tile-icon"></i><span class="icon-companion">Join Collaboration</span>
														</button>
													</td></tr>
												</c:if>
												<c:if test="${supervisor}">
													<tr class="match-row"><td>
														<button type="button" class="btn mdb-color ml-auto darken-1 full-width" data-target="#addAssignments" data-toggle="modal">
													    	<i class="fa fa-group tile-icon"></i><span class="icon-companion">Distribute Assignments</span>
														</button>
													</td></tr>
													<tr class="match-row"><td>
														<hr/>
														<button type="button" class="btn btn-danger ml-auto full-width" data-target="#deleteTask" data-toggle="modal">
													    	<i class="fa fa-trash tile-icon"></i><span class="icon-companion">Delete Task</span>
														</button>
													</td></tr>
												</c:if>
											</tbody>
	                                	</table>
	                                </div>
		                        </div>
		                    </div>
	                    </c:if>
                    </div>
                </div>
                <!--/.Sidebar-->

                <!--Main column-->
                <div class="col-lg-8">
					<!--First row-->
                    <div class="row wow fadeIn" data-wow-delay="0.2s">
                        <div class="col-lg-12">
                        <br/><br/>
	                         <!-- Tabs -->
	                         <!-- Nav tabs -->
	                         <div class="tabs-wrapper">
	                             <ul class="nav nav-justified classic-tabs tabs-primary" role="tablist">
	                                 <li class="nav-item tab-listener">
	                                    <a class="nav-link waves-light waves-effect waves-light active" data-toggle="tab" href="#dependencyPanel" role="tab" aria-expanded="false">
		                                    <span>Task Dependency chain (<c:out value="${task.dependantCount}" />|<c:out value="${task.dependencyCount}" />)</span>
	                                    </a>
	                                 </li>
	                                 <li class="nav-item tab-listener">
	                                    <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" href="#userPanel" role="tab" aria-expanded="false">
		                                    <span>Assigned Users (<c:out value="${task.assignedUsers.size()}" />)</span>
	                                    </a>
	                                 </li>
	                                 <li class="nav-item tab-listener">
	                                    <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" href="#teamPanel" role="tab" aria-expanded="false">
		                                    <span>Assigned Teams (<c:out value="${task.assignedTeams.size()}" />)</span>
	                                    </a>
	                                 </li>
	                             </ul>
	                         </div>
	                         <br/>
	                         <!-- Tab panels -->
	                         <div class="tab-content">
								 <!--Panel 1-->
								 <jsp:include page="task-dependency-panel.jsp"></jsp:include>
								 <!--Panel 2-->
	                             <div class="tab-pane fade" id="userPanel" role="tabpanel" aria-expanded="false">
								     <c:choose>
									     <c:when test="${task.assignedUsers.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
				                               			<h2 class="h2-responsive">There are currently no Users</h2>
				                               			<h2 class="h2-responsive">assigned to this Task</h2>
				                               		</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.task.assignedUsers}" var="assignment">
													<div class="col-lg-4">
														<br/><br/><br/>
														<div class="card wow fadeIn" data-wow-delay="0.2s">
															<div class="card-block">
									                            <c:set var="assignmentItem" value="${assignment}" scope="request" />
									                            <jsp:include page="../assignment/assignment-card-content.jsp"></jsp:include>
																<c:if test="${supervisor or operator eq assignmentItem.entrustor.name}">
																	<hr/>
																	<div class="full-width text-center">
																		<a href="AppUserAssignmentDelete?id=<c:out value="${assignment.id}" />
																			&taskId=<c:out value="${task.id}" />">Unassign User</a>
															    	</div>
														    	</c:if>
															</div>
														</div>
														<br/><br/>
									            	</div>
												</c:forEach>
											</div>		    
										</c:otherwise>
									</c:choose>
	                             </div>
	                             <!--Panel 3-->
	                             <div class="tab-pane fade" id="teamPanel" role="tabpanel" aria-expanded="false">
								     <c:choose>
									     <c:when test="${task.assignedTeams.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
				                               			<h2 class="h2-responsive">There are currently no Teams</h2>
				                               			<h2 class="h2-responsive">assigned to this Task</h2>
				                               		</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.task.assignedTeams}" var="assignment">
													<div class="col-lg-4">
														<br/>
														<div class="card wow fadeIn" data-wow-delay="0.2s">
															<div class="card-block">
									                            <c:set var="assignmentItem" value="${assignment}" scope="request" />
									                            <jsp:include page="../assignment/assignment-team-card-content.jsp"></jsp:include>
									                            <c:if test="${supervisor or operator eq assignmentItem.entrustor.name}">
									                            	<hr/>
										                            <div class="full-width text-center">
																		<a href="TeamAssignmentDelete?id=<c:out value="${assignment.id}" />
																			&taskId=<c:out value="${task.id}" />">Unassign Team</a>
															    	</div>
																</c:if>
															</div>
														</div>
									            	</div>
												</c:forEach>
											</div>		    
										</c:otherwise>
									</c:choose>
	                             </div>
	                         </div>
	                         <!-- /.Tabs -->
	                    </div>
                    </div>
                    <!--/.First row-->
                </div>
                <!--/.Main column-->
			</div>
			
   			<!-- Modals -->
			<jsp:include page="../assignment/assignment-create.jsp"></jsp:include>
			<jsp:include page="task-delete.jsp"></jsp:include>
			<jsp:include page="task-alert.jsp"></jsp:include>
			<jsp:include page="task-dependency-create.jsp"></jsp:include>
			<jsp:include page="task-participation.jsp"></jsp:include>
			<jsp:include page="../assignment/assignment-alert.jsp"></jsp:include>
			<jsp:include page="../../modal/logout.jsp"></jsp:include>
			<!-- /Modals -->
            
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>