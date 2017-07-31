<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.kota.stratagem.weblayer.common.objective.ObjectiveAttribute" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Objectives</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:useBean id="objective" class="com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor" scope="request" />
	<br/><br/><br/><br/>
	<div class="wrapper">
		<div class="container">
			<div class="row">
                <!--Sidebar-->
                <div class="col-lg-4 wow fadeIn" data-wow-delay="0.1s">
					<br/><br/>
                    <div class="widget-wrapper wow fadeIn" data-wow-delay="0.2s">
						<h2>${objective.name}</h2>
                        <br/><br/>
                        <div class="card">
                            <div class="card-block">
                            	<div class="form-header mdb-color darken-1">
                                	<h5><i class="fa fa-info-circle"></i><span class="icon-companion"> Information</span></h5>
                                </div>
                                <div class="md-form">
                                	<table class="strat-detail-table">
	                                	<tbody>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Status</td>
	                                			<td class="strat-detail-attribute-value">${objective.status.label}</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Priority</td>
	                                			<td class="strat-detail-attribute-value">${objective.priority}</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Deadline</td>
	                                			<td class="strat-detail-attribute-value">
												<c:choose>
												    <c:when test="${empty objective.deadline}"><span class="font-no-content">None</span></c:when>
											        <c:otherwise>${objective.deadline}</c:otherwise>
												</c:choose>
	                                			</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Confidentiality</td>
	                                			<td class="strat-detail-attribute-value">
	                               			    <c:choose>
												    <c:when test="${requestScope.objective.confidential}">Private</c:when>
											        <c:otherwise>Public</c:otherwise>
												</c:choose>
												</td>
	                                		</tr>
                           			 		<tr>
	                                			<td class="strat-detail-attribute-name">Created by</td>
	                                			<td class="strat-detail-attribute-value">${objective.creator.name}</td>
	                                		</tr>
                                		    <tr>
	                                			<td class="strat-detail-attribute-name">Creation date</td>
	                                			<td class="strat-detail-attribute-value">${objective.creationDate}</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Modified by</td>
	                                			<td class="strat-detail-attribute-value">${objective.modifier.name}</td>
	                                		</tr>
                                		    <tr>
	                                			<td class="strat-detail-attribute-name">Modification date</td>
	                                			<td class="strat-detail-attribute-value">${objective.modificationDate}</td>
	                                		</tr>
											<c:choose>
											    <c:when test="${empty objective.description}">
											    	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											    	<tr><td colspan="2" class="strat-detail-description">
											    		<p class="center-text"><span class="font-no-content">No Description</span></p>
										    		</td></tr>
											    </c:when>
										        <c:otherwise>
										        	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="center-text">Description</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="center-text">...</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="center-text">${objective.description}</p></td></tr>
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
	                                		<tr class="match-row"><td class="center-text">
						  		   			    <a href="Objective?id=<c:out value="${objective.id}"/>&edit=1" class="vertical-align-middle center-text full-width">
							       			    	<i class="fa fa-edit" aria-hidden="true"></i> Edit Objective
							       			    </a>
											</td></tr>
											<tr class="match-row"><td>
			         	                       <button type="button" class="btn mdb-color ml-auto darken-1 full-width" data-target="#addProject" data-toggle="modal">
											    	<i class="fa fa-sitemap tile-icon"></i><span class="icon-companion">Create Project</span>
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
												<button type="button" class="btn btn-danger ml-auto full-width" data-target="#deleteObjective" data-toggle="modal">
											    	<i class="fa fa-trash tile-icon"></i><span class="icon-companion">Delete Objective</span>
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
                           	<c:choose>
								<c:when test="${objective.projects.size() == 0 and objective.tasks.size() == 0}">
			                   		<div class="divider-new">
			                 			<h2 class="h2-responsive">/</h2>
			                 		</div>
									<div class="center-text">
                       					<h2 class="h2-responsive">There are currently no Projects</h2>
                   						<h2 class="h2-responsive">or Tasks</h2>
                   						<h2 class="h2-responsive">defined under this Objective</h2>
                      				</div>
                      			</c:when>
								<c:when test="${objective.projects.size() == 0 and objective.tasks.size() != 0}">
			                   		<div class="divider-new">
			                 			<h2 class="h2-responsive">/</h2>
			                 		</div>
									<div class="center-text">
										<h2 class="h2-responsive">There are currently no Projects</h2>
										<h2 class="h2-responsive">defined under this Objective</h2>
									</div>
								</c:when>
								<c:otherwise>
									<div class="divider-new">
										<h2 class="h2-responsive">List of Projects</h2>
									</div>
								</c:otherwise>
							</c:choose>
                        </div>
                    </div>
                    <!--/.First row-->

					<div class="row">
						<c:forEach items="${requestScope.objective.projects}" var="project">
							<div class="col-lg-4">
	                            <!--Card-->
	                            <div class="card wow fadeIn" data-wow-delay="0.2s">
	                                <!--Card content-->
	                                <div class="card-block">
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
	                                    <p class="card-text"><c:out value="${project.status.label}" /></p>
	                                    <a href="Project?id=<c:out value="${project.id}" />" class="btn btn-primary">Inspect project</a>
	                                </div>
	                                <!--/.Card content-->
	                            </div>
	                            <br/>
	                            <!--/.Card-->
	                        </div>
						</c:forEach>
					</div>

                    <div class="row wow fadeIn" data-wow-delay="0.2s">
                        <div class="col-lg-12">
							<c:choose>
								<c:when test="${objective.projects.size() == 0 and objective.tasks.size() == 0}">
                               	</c:when>   
                               	<c:when test="${objective.projects.size() != 0 and objective.tasks.size() == 0}">
			                   		<div class="divider-new">
			                 			<h2 class="h2-responsive">/</h2>
			                 		</div>
                           			<div class="center-text">
                               			<h2 class="h2-responsive">There are currently no Tasks</h2>
                               			<h2 class="h2-responsive">defined under this Objective</h2>
                               		</div>
                               	</c:when>
                               	<c:otherwise>
                               		<div class="divider-new">
                               			<h2 class="h2-responsive">List of Tasks</h2>
                               		</div>
                               	</c:otherwise>
							</c:choose>
                        </div>
                    </div>

					<div class="row">
						<c:forEach items="${requestScope.objective.tasks}" var="task">
							<div class="col-lg-4">
	                            <!--Card-->
	                            <div class="card wow fadeIn" data-wow-delay="0.2s">
	                                <!--Card content-->
	                                <div class="card-block">
	                                    <!--Title-->
	                                    <c:if test="${task.completion == 100}">
					                      	<div class="form-header success-color success-header">
			                                	<i class="fa fa-check-circle"></i>
			                                </div>
		                                </c:if>
	                                    <h4 class="card-title"><c:out value="${task.name}" /></h4>
	                                    <hr/>
	                                    <!--Text-->
	                                    <c:if test="${not empty task.deadline}">
	                                    	<p class="card-text">
	                                    		Deadline:
	                                    		<c:choose>
												    <c:when test="${task.urgencyLevel == 3}">
														<span class="danger-text">
															<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
														</span>
												    </c:when>
												    <c:when test="${task.urgencyLevel == 2}">
														<span class="heavy-warning-text">
															<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
														</span>
												    </c:when>
												    <c:when test="${task.urgencyLevel == 1}">
														<span class="warning-text">
															<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
														</span>
												    </c:when>
											        <c:otherwise>
											        	<span class="success-text">
											        		<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
											        	</span>
											        </c:otherwise>
												</c:choose>
	                                    	</p>
	                                    	<hr/>
	                                    </c:if>
	                                    <p class="card-text"><c:out value="${task.completion} Completed" />%</p>
	                                    <a href="Task?id=<c:out value="${task.id}" />" class="btn btn-primary">Inspect task</a>
	                                </div>
	                                <!--/.Card content-->
	                            </div>
	                            <br/>
	                            <!--/.Card-->
	                        </div>
						</c:forEach>
					</div>
                </div>
                <!--/.Main column-->
            </div>
            
            <!--Under column-->
            <div class="row wow fadeIn" data-wow-delay="0.2s">
            	<div class="col-lg-12">
		            <c:choose>
						<c:when test="${objective.assignedUsers.size() == 0 and objective.assignedTeams.size() == 0}">
							<div class="divider-new">
		                 		<h2 class="h2-responsive">/</h2>
		                 	</div>
							<div class="center-text">
		       					<h2 class="h2-responsive">There are currently no Users</h2>
		   						<h2 class="h2-responsive">or Teams</h2>
		   						<h2 class="h2-responsive">assigned to this Objective</h2>
		      				</div>
		      			</c:when>
						<c:otherwise>
							<div class="divider-new">
								<h2 class="h2-responsive">Assigned workforce</h2>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<br/><br/><br/><br/>
			<div class="row">
			<c:forEach items="${requestScope.objective.assignedUsers}" var="assignment">
				<div class="col-lg-3">
                    <!--Card-->
                    <div class="card wow fadeIn" data-wow-delay="0.2s">
                        <!--Card content-->
                        <div class="card-block">
                        	<div class="card-avatar">
                        		<img class="rounded-circle img-responsive" src="https://www.filepicker.io/api/file/9dXFgbwRRlKXzHDItGEK" 
                        		style="border-bottom-left-radius: 50%; border-bottom-right-radius: 50%;">
                        	</div>
                            <!--Title-->
                            <h4 class="card-title center-text"><c:out value="${assignment.recipient.name}" /></h4>
                            <hr/>
                            <!--Text-->
                            <p class="card-text">Assigned by: <c:out value="${assignment.entrustor.name}" /></p>
                            <p class="card-text">Assignment date: 
                            	<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd" />
                            </p>
                            <div class="full-width center-text">
                            	<a href="AssignmentDelete?id=<c:out value="${assignment.id}" />&objectiveId=<c:out value="${objective.id}" />">Unassign user</a>
                            </div>
                        </div>
                        <!--/.Card content-->
                    </div>
                    <br/><br/><br/><br/><br/>
                    <!--/.Card-->
            	</div>
			</c:forEach>
			<!--Under column-->
            
   			<!-- Modals -->
			<jsp:include page="../project/project-create.jsp"></jsp:include>
			<jsp:include page="../task/task-create.jsp"></jsp:include>
			<jsp:include page="../assignment/assignment-create.jsp"></jsp:include>
			<jsp:include page="objective-delete.jsp"></jsp:include>
			<jsp:include page="objective-alert.jsp"></jsp:include>
			<jsp:include page="../project/project-alert.jsp"></jsp:include>
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