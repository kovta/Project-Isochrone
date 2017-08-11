<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.kota.stratagem.weblayer.common.appuser.AppUserAttribute" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - User</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:useBean id="user" class="com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor" scope="request" />
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
                            	<div class="card-avatar form-content">
                                	<img class="rounded-circle img-responsive" src="https://www.filepicker.io/api/file/9dXFgbwRRlKXzHDItGEK"
                                	style="border-bottom-left-radius: 50%; border-bottom-right-radius: 50%;">
                            	</div>
                        		<div class="padding-top"><h3 class="h3-responsive text-center">${user.name}</h3></div>
                        		<p class="text-center grey-text">${user.role.label}</p>
                                <div class="md-form">
                                	<table class="strat-detail-table">
	                                	<tbody>
	                                		<tr><td colspan="2"><hr class="extra-margins"></td></tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Email address</td>
	                                			<td class="strat-detail-attribute-value">
	                                			<c:choose>
												    <c:when test="${empty user.email}"><span class="font-no-content">None specified</span></c:when>
											        <c:otherwise>${user.email} </c:otherwise>
												</c:choose>
												</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Registration date</td>
	                                			<td class="strat-detail-attribute-value">${user.registrationDate}</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Account Modifier</td>
	                                			<td class="strat-detail-attribute-value">${user.accountModifier.name}</td>
	                                		</tr>
	                                		<tr>
	                                			<td class="strat-detail-attribute-name">Account modification date</td>
	                                			<td class="strat-detail-attribute-value">${user.registrationDate}</td>
	                                		</tr>
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
						  		   			    <a href="User?id=<c:out value="${user.id}"/>&edit=1" class="vertical-align-middle text-center full-width">
							       			    	<i class="fa fa-edit" aria-hidden="true"></i> Edit Profile
							       			    </a>
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
		                             	 <a class="nav-link waves-light waves-effect waves-light active" data-toggle="tab" href="#objectivePanel" role="tab" aria-expanded="true">
		                                 	 <span>Objective Assignments (<c:out value="${user.objectives.size()}" />)</span>
		                                 </a>
	                                 </li>
	                                 <li class="nav-item tab-listener">
		                             	 <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" href="#projectPanel" role="tab" aria-expanded="true">
		                                 	 <span>Project Assignments (<c:out value="${user.projects.size()}" />)</span>
		                                 </a>
	                                 </li>
	                                 <li class="nav-item tab-listener">
	                                    <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" href="#submodulePanel" role="tab" aria-expanded="false">
		                                    <span>Submodule Assignments (<c:out value="${user.submodules.size()}" />)</span>
	                                    </a>
	                                 </li>
	                                 <li class="nav-item tab-listener">
	                                    <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" href="#taskPanel" role="tab" aria-expanded="false">
		                                    <span>Task Assignments (<c:out value="${user.tasks.size()}" />)</span>
	                                    </a>
	                                 </li>
	                             </ul>
	                         </div>
	                         <br/><br/>
                			<!-- Tab panels -->
	                         <div class="tab-content">
	                         	 <!--Panel 1-->
	                             <div class="tab-pane fade active show" id="objectivePanel" role="tabpanel" aria-expanded="true">
		                             <c:choose>
									     <c:when test="${user.objectives.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
													<div class="text-center content-padder">
														<h2 class="h2-responsive">There are currently no Objectives</h2>
														<h2 class="h2-responsive">assigned to this User</h2>
													</div>
												</div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.user.objectives}" var="assignment">
													<div class="col-lg-4">
									                    <!--Card-->
									                    <div class="card wow fadeIn" data-wow-delay="0.2s">
									                        <!--Card content-->
									                        <div class="card-block">
									                            <!--Title-->
									                            <h4 class="card-title text-center"><c:out value="${assignment.objective.name}" /></h4>
									                            <hr/>
									                            <!--Text-->
									                            <p class="card-text">Assigned by: <c:out value="${assignment.entrustor.name}" /></p>
									                            <p class="card-text">Assignment date: 
									                            	<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd" />
									                            </p>
																<a href="Objective?id=<c:out value="${assignment.objective.id}" />" class="btn btn-primary">Inspect Objective</a>
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
	                             <!--/.Panel 1-->
	                             <!--Panel 2-->
	                             <div class="tab-pane fade" id="projectPanel" role="tabpanel" aria-expanded="false">
								     <c:choose>
									     <c:when test="${user.projects.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
														<h2 class="h2-responsive">There are currently no Projects</h2>
														<h2 class="h2-responsive">assigned to this User</h2>
													</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.user.projects}" var="assignment">
													<div class="col-lg-4">
									                    <!--Card-->
									                    <div class="card wow fadeIn" data-wow-delay="0.2s">
									                        <!--Card content-->
									                        <div class="card-block">
									                            <!--Title-->
									                            <h4 class="card-title text-center"><c:out value="${assignment.project.name}" /></h4>
									                            <hr/>
									                            <!--Text-->
									                            <p class="card-text">Assigned by: <c:out value="${assignment.entrustor.name}" /></p>
									                            <p class="card-text">Assignment date: 
									                            	<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd" />
									                            </p>
																<a href="Project?id=<c:out value="${assignment.project.id}" />" class="btn btn-primary">Inspect Project</a>
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
	                             <!--/.Panel 2-->
	                             <!--Panel 3-->
	                             <div class="tab-pane fade" id="submodulePanel" role="tabpanel" aria-expanded="false">
								     <c:choose>
									     <c:when test="${user.submodules.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
				                               			<h2 class="h2-responsive">There are currently no Submodules</h2>
														<h2 class="h2-responsive">assigned to this User</h2>
				                               		</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.user.submodules}" var="assignment">
													<div class="col-lg-4">
									                    <!--Card-->
									                    <div class="card wow fadeIn" data-wow-delay="0.2s">
									                        <!--Card content-->
									                        <div class="card-block">
									                            <!--Title-->
									                            <h4 class="card-title text-center"><c:out value="${assignment.submodule.name}" /></h4>
									                            <hr/>
									                            <!--Text-->
									                            <p class="card-text">Assigned by: <c:out value="${assignment.entrustor.name}" /></p>
									                            <p class="card-text">Assignment date: 
									                            	<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd" />
									                            </p>
																<a href="Submodule?id=<c:out value="${assignment.submodule.id}" />" class="btn btn-primary">Inspect Submodule</a>
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
	                             <div class="tab-pane fade" id="taskPanel" role="tabpanel" aria-expanded="false">
								     <c:choose>
									     <c:when test="${user.tasks.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
														<h2 class="h2-responsive">There are currently no Tasks</h2>
														<h2 class="h2-responsive">assigned to this User</h2>
				                               		</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.user.tasks}" var="assignment">
													<div class="col-lg-4">
									                    <!--Card-->
									                    <div class="card wow fadeIn" data-wow-delay="0.2s">
									                        <!--Card content-->
									                        <div class="card-block">
									                            <!--Title-->
									                            <h4 class="card-title text-center"><c:out value="${assignment.task.name}" /></h4>
									                            <hr/>
									                            <!--Text-->
									                            <p class="card-text">Assigned by: <c:out value="${assignment.entrustor.name}" /></p>
									                            <p class="card-text">Assignment date: 
									                            	<fmt:formatDate type="date" value="${assignment.creationDate}" pattern="yyyy-MM-dd" />
									                            </p>
																<a href="Task?id=<c:out value="${assignment.task.id}" />" class="btn btn-primary">Inspect Task</a>
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
			<!-- /Modals -->
            
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>
		