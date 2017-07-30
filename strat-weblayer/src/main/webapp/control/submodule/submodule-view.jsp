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
						<h2>${submodule.name}</h2>
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
											    		<p class="center-text"><span class="font-no-content">No Description</span></p>
										    		</td></tr>
											    </c:when>
										        <c:otherwise>
										        	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="center-text">Description</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="center-text">...</p></td></tr>
											        <tr><td colspan="2" class="strat-detail-description"><p class="center-text">${submodule.description}</p></td></tr>
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
						  		   			    <a href="Submodule?id=<c:out value="${submodule.id}"/>&edit=1" class="vertical-align-middle center-text full-width">
							       			    	<i class="fa fa-edit" aria-hidden="true"></i> Edit Submodule
							       			    </a>
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
                                <h2 class="h2-responsive">List of Tasks</h2>
                            </div>
                        </div>
                    </div>

					<div class="row">
						<c:forEach items="${requestScope.submodule.tasks}" var="task">
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