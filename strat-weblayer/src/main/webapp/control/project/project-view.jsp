<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.kota.stratagem.weblayer.common.project.ProjectAttribute" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor" %>
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
						<h2>${project.name}</h2>
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
											    		<p class="center-text"><span class="font-no-content">No Description</span></p>
										    		</td></tr>
											    </c:when>
										        <c:otherwise>
										        	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="center-text">Description</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="center-text">...</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="center-text">${project.description}</p></td></tr>
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
						  		   			    <a href="Project?id=<c:out value="${project.id}"/>&edit=1" class="vertical-align-middle center-text full-width">
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

                    <div class="row wow fadeIn" data-wow-delay="0.2s">
                        <div class="col-lg-12">
                            <div class="divider-new">
                                <h2 class="h2-responsive">List of Submodules</h2>
                            </div>
                        </div>
                    </div>

					<div class="row">
						<c:forEach items="${requestScope.project.submodules}" var="submodule">
							<div class="col-lg-4">
	                            <!--Card-->
	                            <div class="card wow fadeIn" data-wow-delay="0.2s">
	                                <!--Card content-->
	                                <div class="card-block">
	                                    <!--Title-->
	                                    <h4 class="card-title"><c:out value="${submodule.name}" /></h4>
	                                    <hr/>
	                                    <!--Text-->
	                                    <c:if test="${not empty submodule.deadline}">
	                                    	<p class="card-text">
	                                    		Deadline:
	                                    		<c:choose>
												    <c:when test="${submodule.urgencyLevel == 3}">
														<span class="danger-text">
															<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
														</span>
												    </c:when>
												    <c:when test="${submodule.urgencyLevel == 2}">
														<span class="heavy-warning-text">
															<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
														</span>
												    </c:when>
												    <c:when test="${submodule.urgencyLevel == 1}">
														<span class="warning-text">
															<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
														</span>
												    </c:when>
											        <c:otherwise>
											        	<span class="success-text">
											        		<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
											        	</span>
											        </c:otherwise>
												</c:choose>
	                                    	</p>
	                                    	<hr/>
	                                    </c:if>
                              			<c:choose>
										    <c:when test="${submodule.tasks.size() == 0}">
												<p class="card-text"><c:out value="No tasks registered" /></p>
										    </c:when>
										    <c:when test="${submodule.tasks.size() == 1}">
												<p class="card-text"><c:out value="${submodule.tasks.size()} Task" /></p>
									        	<p class="card-text"><c:out value="${submodule.completion}% Completed" /></p>
										    </c:when>
									        <c:otherwise>
									        	<p class="card-text"><c:out value="${submodule.tasks.size()} Tasks" /></p>
									        	<p class="card-text"><c:out value="${submodule.completion}% Completed" /></p>
									        </c:otherwise>
										</c:choose>
	                                    <a href="Submodule?id=<c:out value="${submodule.id}" />" class="btn btn-primary">Inspect Submodule</a>
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
                            <div class="divider-new">
                                <h2 class="h2-responsive">List of Tasks</h2>
                            </div>
                        </div>
                    </div>

					<div class="row">
						<c:forEach items="${requestScope.project.tasks}" var="task">
							<div class="col-lg-4">
	                            <!--Card-->
	                            <div class="card wow fadeIn" data-wow-delay="0.2s">
	                                <!--Card content-->
	                                <div class="card-block">
	                                    <!--Title-->
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
            
   			<!-- Modals -->
			<jsp:include page="../submodule/submodule-create.jsp"></jsp:include>
			<jsp:include page="../task/task-create.jsp"></jsp:include>
			<jsp:include page="../../partial/alert.jsp"></jsp:include>
			<!-- /Modals -->
            
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>