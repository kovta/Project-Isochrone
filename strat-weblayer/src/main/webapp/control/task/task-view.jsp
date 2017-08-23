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
                                	<table class="strat-detail-table">
	                                	<tbody>
	                                		<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											<tr>
												<c:choose>
												    <c:when test="${not empty task.objective}">
				                               			<td class="strat-detail-attribute-name">Parent Objective</td>
			                                			<td class="strat-detail-attribute-value">
			                                				<a href="Objective?id=<c:out value="${task.objective.id}" />">${task.objective.name}</a>
			                                			</td>
												    </c:when>
												    <c:when test="${not empty task.project}">	
			                                			<td class="strat-detail-attribute-name">Parent Project</td>
			                                			<td class="strat-detail-attribute-value">
			                                				<a href="Project?id=<c:out value="${task.project.id}" />">${task.project.name}</a>
			                                			</td>
												    </c:when>
											        <c:otherwise>
						                      			<td class="strat-detail-attribute-name">Parent Submodule</td>
			                                			<td class="strat-detail-attribute-value">
			                                				<a href="Submodule?id=<c:out value="${task.submodule.id}" />">${task.submodule.name}</a>
			                                			</td>
											        </c:otherwise>
												</c:choose>
											</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Priority</td>
	                                			<td class="strat-detail-attribute-value">${task.priority}</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Completion</td>
	                                			<td class="strat-detail-attribute-value">${task.completion} %</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Deadline</td>
	                                			<td class="strat-detail-attribute-value">
												<c:choose>
												    <c:when test="${empty task.deadline}"><span class="font-no-content">None</span></c:when>
											        <c:otherwise>${task.deadline}</c:otherwise>
												</c:choose>
	                                			</td>
	                                		</tr>
                           			 		<tr>
	                                			<td class="strat-detail-attribute-name">Created by</td>
	                                			<td class="strat-detail-attribute-value">${task.creator.name}</td>
	                                		</tr>
                                		    <tr>
	                                			<td class="strat-detail-attribute-name">Creation date</td>
	                                			<td class="strat-detail-attribute-value">${task.creationDate}</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Modified by</td>
	                                			<td class="strat-detail-attribute-value">${task.modifier.name}</td>
	                                		</tr>
                                		    <tr>
	                                			<td class="strat-detail-attribute-name">Modification date</td>
	                                			<td class="strat-detail-attribute-value">${task.modificationDate}</td>
	                                		</tr>
											<c:choose>
											    <c:when test="${empty task.description}">
											    	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											    	<tr><td colspan="2" class="strat-detail-description">
											    		<p class="text-center"><span class="font-no-content">No Description</span></p>
										    		</td></tr>
											    </c:when>
										        <c:otherwise>
										        	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="text-center">Description</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="text-center">...</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="text-center">${task.description}</p></td></tr>
										        </c:otherwise>
											</c:choose>
										</tbody>
                                	</table>
                                </div>
	                        </div>
	                    </div>

	       			    <br/><br/><br/>
	       			    <div class="card">
                            <div class="card-block">
                            	<div class="form-header mdb-color darken-1">
                                	<h5><i class="fa fa-exclamation-circle"></i><span class="icon-companion"> Actions</span></h5>
                                </div>
                                <div class="md-form">
                                	<table class="strat-detail-table">
	                                	<tbody>
	                                		<tr class="match-row"><td class="text-center">
						  		   			    <a href="Task?id=<c:out value="${task.id}"/>&edit=1" class="vertical-align-middle text-center full-width">
							       			    	<i class="fa fa-edit" aria-hidden="true"></i> Edit Task
							       			    </a>
											</td></tr>
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
										</tbody>
                                	</table>
                                </div>
	                        </div>
	                    </div>
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
	                                    <a class="nav-link waves-light waves-effect waves-light active" data-toggle="tab" href="#userPanel" role="tab" aria-expanded="false">
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
	                         <br/><br/>
	                         <!-- Tab panels -->
	                         <div class="tab-content">
								 <!--Panel 3-->
	                             <div class="tab-pane fade active show" id="userPanel" role="tabpanel" aria-expanded="false">
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
																<div class="full-width text-center">
																	<a href="AppUserAssignmentDelete?id=<c:out value="${assignment.id}" />&taskId=<c:out value="${task.id}" />">Unassign user</a>
														    	</div>
															</div>
														</div>
														<br/><br/>
									            	</div>
												</c:forEach>
											</div>		    
										</c:otherwise>
									</c:choose>
	                             </div>
	                             <!--/.Panel 3-->
	                             <!--Panel 4-->
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
									                    <br/><br/><br/>
									                    <div class="card wow fadeIn" data-wow-delay="0.2s">
									                        <div class="card-block">
									                            <c:set var="assignmentItem" value="${assignment}" scope="request" />
									                            <jsp:include page="../assignment/assignment-card-content.jsp"></jsp:include>
									                            <div class="full-width text-center">
									                            	<a href="AppUserAssignmentDelete?id=<c:out value="${assignment.id}" />&objectiveId=<c:out value="${objective.id}" />">Unassign user</a>
									                            </div>
									                        </div>
									                    </div>
									                    <br/><br/>
									            	</div>
												</c:forEach>
											</div>		    
										</c:otherwise>
									</c:choose>
	                             </div>
	                             <!--/.Panel 4-->
	                         </div>
	                         <!-- /.Tabs -->
	                    </div>
                    </div>
                    <!--/.First row-->
                </div>
                <!--/.Main column-->
			</div>
			
   			<!-- Modals -->
			<jsp:include page="../task/task-create.jsp"></jsp:include>
			<jsp:include page="../assignment/assignment-create.jsp"></jsp:include>
			<jsp:include page="task-delete.jsp"></jsp:include>
			<jsp:include page="task-alert.jsp"></jsp:include>
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