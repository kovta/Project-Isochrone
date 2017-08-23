<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.kota.stratagem.weblayer.common.submodule.SubmoduleAttribute" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Submodules</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:useBean id="submodule" class="com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor" scope="request" />
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
                                <div class="padding-top"><h3 class="h3-responsive text-center">${submodule.name}</h3></div>
                                <div class="md-form">
                                	<table class="strat-detail-table">
	                                	<tbody>
	                                		<tr><td colspan="2"><hr class="extra-margins"></td></tr>
    	                               		<tr>
	                                			<td class="strat-detail-attribute-name">Parent Project</td>
	                                			<td class="strat-detail-attribute-value">
	                                				<a href="Project?id=<c:out value="${submodule.project.id}" />">${submodule.project.name}</a>
	                                			</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Deadline</td>
	                                			<td class="strat-detail-attribute-value">
												<c:choose>
												    <c:when test="${empty submodule.deadline}"><span class="font-no-content">None</span></c:when>
											        <c:otherwise>${submodule.deadline}</c:otherwise>
												</c:choose>
	                                			</td>
	                                		</tr>
                           			 		<tr>
	                                			<td class="strat-detail-attribute-name">Created by</td>
	                                			<td class="strat-detail-attribute-value">${submodule.creator.name}</td>
	                                		</tr>
                                		    <tr>
	                                			<td class="strat-detail-attribute-name">Creation date</td>
	                                			<td class="strat-detail-attribute-value">${submodule.creationDate}</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Modified by</td>
	                                			<td class="strat-detail-attribute-value">${submodule.modifier.name}</td>
	                                		</tr>
                                		    <tr>
	                                			<td class="strat-detail-attribute-name">Modification date</td>
	                                			<td class="strat-detail-attribute-value">${submodule.modificationDate}</td>
	                                		</tr>
											<c:choose>
											    <c:when test="${empty submodule.description}">
											    	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											    	<tr><td colspan="2" class="strat-detail-description">
											    		<p class="text-center"><span class="font-no-content">No Description</span></p>
										    		</td></tr>
											    </c:when>
										        <c:otherwise>
										        	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="text-center">Description</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="text-center">...</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="text-center">${submodule.description}</p></td></tr>
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
						  		   			    <a href="Submodule?id=<c:out value="${submodule.id}"/>&edit=1" class="vertical-align-middle text-center full-width">
							       			    	<i class="fa fa-edit" aria-hidden="true"></i> Edit Submodule
							       			    </a>
											</td></tr>
											<tr class="match-row"><td>
												<button type="button" class="btn mdb-color ml-auto darken-1 full-width" data-target="#addTask" data-toggle="modal">
											    	<i class="fa fa-tasks tile-icon"></i><span class="icon-companion">Register Task</span>
												</button>
											</td></tr>
											<tr class="match-row"><td>
												<button type="button" class="btn mdb-color ml-auto darken-1 full-width" data-target="#addAssignments" data-toggle="modal">
											    	<i class="fa fa-group tile-icon"></i><span class="icon-companion">Distribute Assignments</span>
												</button>
											</td></tr>
											<tr class="match-row"><td>
												<hr/>
												<button type="button" class="btn btn-danger ml-auto full-width" data-target="#deleteSubmodule" data-toggle="modal">
											    	<i class="fa fa-trash tile-icon"></i><span class="icon-companion">Delete Submodule</span>
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
	                                    <a class="nav-link waves-light waves-effect waves-light active" data-toggle="tab" href="#taskPanel" role="tab" aria-expanded="false">
		                                    <span>Tasks (<c:out value="${submodule.tasks.size()}" />)</span>
	                                    </a>
	                                 </li>
	                                 <li class="nav-item tab-listener">
	                                    <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" href="#userPanel" role="tab" aria-expanded="false">
		                                    <span>Assigned Users (<c:out value="${submodule.assignedUsers.size()}" />)</span>
	                                    </a>
	                                 </li>
	                                 <li class="nav-item tab-listener">
	                                    <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" href="#teamPanel" role="tab" aria-expanded="false">
		                                    <span>Assigned Teams (<c:out value="${submodule.assignedTeams.size()}" />)</span>
	                                    </a>
	                                 </li>
	                             </ul>
	                         </div>
	                         <br/><br/>
	                         <!-- Tab panels -->
	                         <div class="tab-content">
	                             <!--Panel 2-->
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
											<div class="row">
												<c:forEach items="${requestScope.submodule.tasks}" var="taskItem">
													<div class="col-lg-4">
							                            <c:set var="task" value="${taskItem}" scope="request" />
							                            <jsp:include page="../task/task-card.jsp"></jsp:include>
							                        </div>
												</c:forEach>
											</div>		    
										</c:otherwise>
									</c:choose>
	                             </div>
	                             <!--/.Panel 2-->
								 <!--Panel 3-->
	                             <div class="tab-pane fade" id="userPanel" role="tabpanel" aria-expanded="false">
								     <c:choose>
									     <c:when test="${submodule.assignedUsers.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
				                               			<h2 class="h2-responsive">There are currently no Users</h2>
				                               			<h2 class="h2-responsive">assigned to this Submodule</h2>
				                               		</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.submodule.assignedUsers}" var="assignment">
													<div class="col-lg-4">
														<br/><br/><br/>
														<div class="card wow fadeIn" data-wow-delay="0.2s">
															<div class="card-block">
									                            <c:set var="assignmentItem" value="${assignment}" scope="request" />
									                            <jsp:include page="../assignment/assignment-card-content.jsp"></jsp:include>
																<div class="full-width text-center">
																	<a href="AppUserAssignmentDelete?id=<c:out value="${assignment.id}" />&submoduleId=<c:out value="${submodule.id}" />">Unassign user</a>
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
									     <c:when test="${submodule.assignedTeams.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
				                               			<h2 class="h2-responsive">There are currently no Teams</h2>
				                               			<h2 class="h2-responsive">assigned to this Submodule</h2>
				                               		</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.submodule.assignedTeams}" var="assignment">
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
			<jsp:include page="submodule-delete.jsp"></jsp:include>
			<jsp:include page="submodule-alert.jsp"></jsp:include>
			<jsp:include page="../task/task-alert.jsp"></jsp:include>
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