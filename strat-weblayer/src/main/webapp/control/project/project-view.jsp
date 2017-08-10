<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.kota.stratagem.weblayer.common.project.ProjectAttribute" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.catalog.ProjectStatusRepresentor" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Projects</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:useBean id="project" class="com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor" scope="request" />
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
                                <div class="padding-top"><h3 class="h3-responsive text-center">${project.name}</h3></div>
                                <div class="md-form">
                                	<table class="strat-detail-table">
	                                	<tbody>
											<tr><td colspan="2"><hr class="extra-margins"></td></tr>
    	                               		<tr>
	                                			<td class="strat-detail-attribute-name">Parent Objective</td>
	                                			<td class="strat-detail-attribute-value">
	                                				<a href="Objective?id=<c:out value="${project.objective.id}" />">${project.objective.name}</a>
	                                			</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Status</td>
	                                			<td class="strat-detail-attribute-value">${project.status.label}</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Deadline</td>
	                                			<td class="strat-detail-attribute-value">
												<c:choose>
												    <c:when test="${empty project.deadline}"><span class="font-no-content">None</span></c:when>
											        <c:otherwise>${project.deadline}</c:otherwise>
												</c:choose>
	                                			</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Confidentiality</td>
	                                			<td class="strat-detail-attribute-value">
	                               			    <c:choose>
												    <c:when test="${requestScope.project.confidential}">Private</c:when>
											        <c:otherwise>Public</c:otherwise>
												</c:choose>
												</td>
	                                		</tr>
                           			 		<tr>
	                                			<td class="strat-detail-attribute-name">Created by</td>
	                                			<td class="strat-detail-attribute-value">${project.creator.name}</td>
	                                		</tr>
                                		    <tr>
	                                			<td class="strat-detail-attribute-name">Creation date</td>
	                                			<td class="strat-detail-attribute-value">${project.creationDate}</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Modified by</td>
	                                			<td class="strat-detail-attribute-value">${project.modifier.name}</td>
	                                		</tr>
                                		    <tr>
	                                			<td class="strat-detail-attribute-name">Modification date</td>
	                                			<td class="strat-detail-attribute-value">${project.modificationDate}</td>
	                                		</tr>
											<c:choose>
											    <c:when test="${empty project.description}">
											    	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											    	<tr><td colspan="2" class="strat-detail-description">
											    		<p class="text-center"><span class="font-no-content">No Description</span></p>
										    		</td></tr>
											    </c:when>
										        <c:otherwise>
										        	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="text-center">Description</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="text-center">...</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="text-center">${project.description}</p></td></tr>
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
						  		   			    <a href="Project?id=<c:out value="${project.id}"/>&edit=1" class="vertical-align-middle text-center full-width">
							       			    	<i class="fa fa-edit" aria-hidden="true"></i> Edit Project
							       			    </a>
											</td></tr>
											<tr class="match-row"><td>
												<button type="button" class="btn mdb-color ml-auto darken-1 full-width" data-target="#addSubmodule" data-toggle="modal">
											    	<i class="fa fa-list-alt tile-icon"></i><span class="icon-companion">Define Submodule</span>
												</button>
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
												<button type="button" class="btn btn-danger ml-auto full-width" data-target="#deleteProject" data-toggle="modal">
											    	<i class="fa fa-trash tile-icon"></i><span class="icon-companion">Delete Project</span>
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
		                             	 <a class="nav-link waves-light waves-effect waves-light active" data-toggle="tab" href="#submodulePanel" role="tab" aria-expanded="true">
		                                 	 <span>Submodules (<c:out value="${project.submodules.size()}" />)</span>
		                                 </a>
	                                 </li>
	                                 <li class="nav-item tab-listener">
	                                    <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" href="#taskPanel" role="tab" aria-expanded="false">
		                                    <span>Tasks (<c:out value="${project.tasks.size()}" />)</span>
	                                    </a>
	                                 </li>
	                                 <li class="nav-item tab-listener">
	                                    <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" href="#userPanel" role="tab" aria-expanded="false">
		                                    <span>Assigned Users (<c:out value="${project.assignedUsers.size()}" />)</span>
	                                    </a>
	                                 </li>
	                                 <li class="nav-item tab-listener">
	                                    <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" href="#teamPanel" role="tab" aria-expanded="false">
		                                    <span>Assigned Teams (<c:out value="${project.assignedTeams.size()}" />)</span>
	                                    </a>
	                                 </li>
	                             </ul>
	                         </div>
	                         <br/><br/>
	                         <!-- Tab panels -->
	                         <div class="tab-content">
	                         	 <!--Panel 1-->
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
										    <div class="row">
												<c:forEach items="${requestScope.project.submodules}" var="submoduleItem">
													<div class="col-lg-4">
							                     		<c:set var="submodule" value="${submoduleItem}" scope="request" />
							                            <jsp:include page="../submodule/submodule-card.jsp"></jsp:include>
							                        </div>
												</c:forEach>
											</div>
										</c:otherwise>
									</c:choose>
	                             </div>
	                             <!--/.Panel 1-->
	                             <!--Panel 2-->
	                             <div class="tab-pane fade" id="taskPanel" role="tabpanel" aria-expanded="false">
								     <c:choose>
									     <c:when test="${project.tasks.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
				                               			<h2 class="h2-responsive">There are currently no Tasks</h2>
				                               			<h2 class="h2-responsive">defined under this Project</h2>
				                               		</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.project.tasks}" var="taskItem">
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
									     <c:when test="${project.assignedUsers.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
				                               			<h2 class="h2-responsive">There are currently no Users</h2>
				                               			<h2 class="h2-responsive">assigned to this Project</h2>
				                               		</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.project.assignedUsers}" var="assignment">
													<div class="col-lg-4">
														<!--Card-->
														<br/><br/><br/>
														<div class="card wow fadeIn" data-wow-delay="0.2s">
														    <!--Card content-->
															<div class="card-block">
																<div class="card-avatar">
																	<img class="rounded-circle img-responsive" src="https://www.filepicker.io/api/file/9dXFgbwRRlKXzHDItGEK" 
																	style="border-bottom-left-radius: 50%; border-bottom-right-radius: 50%;">
																</div>
														   		<!--Title-->
																<h4 class="card-title text-center"><c:out value="${assignment.recipient.name}" /></h4>
																<hr/>
																<!--Text-->
																<p class="card-text">Assigned by: <c:out value="${assignment.entrustor.name}" /></p>
																<p class="card-text">Assignment date: 
																	<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd" />
																</p>
																<div class="full-width text-center">
																	<a href="AppUserAssignmentDelete?id=<c:out value="${assignment.id}" />&projectId=<c:out value="${project.id}" />">Unassign user</a>
														    	</div>
															</div>
															<!--/.Card content-->
														</div>
														<br/><br/><br/><br/><br/>
														<!--/.Card-->
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
									     <c:when test="${project.assignedTeams.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
				                               			<h2 class="h2-responsive">There are currently no Teams</h2>
				                               			<h2 class="h2-responsive">assigned to this Project</h2>
				                               		</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.objective.assignedTeams}" var="assignment">
													<div class="col-lg-4">
									                    <!--Card-->
									                    <br/><br/><br/>
									                    <div class="card wow fadeIn" data-wow-delay="0.2s">
									                        <!--Card content-->
									                        <div class="card-block">
									                        	<div class="card-avatar">
									                        		<img class="rounded-circle img-responsive" src="https://www.filepicker.io/api/file/9dXFgbwRRlKXzHDItGEK" 
									                        		style="border-bottom-left-radius: 50%; border-bottom-right-radius: 50%;">
									                        	</div>
									                            <!--Title-->
									                            <h4 class="card-title text-center"><c:out value="${assignment.recipient.name}" /></h4>
									                            <hr/>
									                            <!--Text-->
									                            <p class="card-text">Assigned by: <c:out value="${assignment.entrustor.name}" /></p>
									                            <p class="card-text">Assignment date: 
									                            	<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd" />
									                            </p>
									                            <div class="full-width text-center">
									                            	<a href="AppUserAssignmentDelete?id=<c:out value="${assignment.id}" />&objectiveId=<c:out value="${objective.id}" />">Unassign user</a>
									                            </div>
									                        </div>
									                        <!--/.Card content-->
									                    </div>
									                    <br/><br/><br/><br/><br/>
									                    <!--/.Card-->
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
			<jsp:include page="../submodule/submodule-create.jsp"></jsp:include>
			<jsp:include page="../task/task-create.jsp"></jsp:include>
			<jsp:include page="../assignment/assignment-create.jsp"></jsp:include>
			<jsp:include page="project-delete.jsp"></jsp:include>
			<jsp:include page="project-alert.jsp"></jsp:include>
			<jsp:include page="../submodule/submodule-alert.jsp"></jsp:include>
			<jsp:include page="../task/task-alert.jsp"></jsp:include>
			<jsp:include page="../assignment/assignment-alert.jsp"></jsp:include>
			<!-- /Modals -->
            
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>