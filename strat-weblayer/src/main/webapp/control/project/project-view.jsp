<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.kota.stratagem.weblayer.common.project.ProjectAttribute" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - projects</title>
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
	                                				<a href="ObjectiveAction?id=<c:out value="${project.objective.id}" />">${project.objective.name}</a>
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
						  		   			    <a href="ProjectAction?id=<c:out value="${project.id}"/>&edit=1" class="vertical-align-middle center-text full-width">
							       			    	<i class="fa fa-edit" aria-hidden="true"></i> Edit project
							       			    </a>
											</td></tr>
											<tr class="match-row"><td>
												<button type="button" class="btn mdb-color ml-auto darken-1 full-width" data-toggle="modal" data-target="#addTask">
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
	                                    <!--Text-->
	                                    <p class="card-text"><c:out value="${task.completion}" />%</p>
	                                    <a href="#" class="btn btn-primary">Inspect task</a>
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