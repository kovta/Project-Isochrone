<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>  
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.kota.stratagem.weblayer.common.objective.ObjectiveAttribute" %>
<%@page import="com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor"%>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ObjectiveStatusRepresentor" %>
<!DOCTYPE html>
<html lang="en">
<head>
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
                        <br/>
                        <div class="card">
                            <div class="card-block">
                                <h5><i class="fa fa-info-circle center-element-vertical detail-icon font-strat-blue"></i></h5>
                                <hr class="extra-margins">
                                <div class="md-form">
                                	<table class="strat-detail-table">
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
										<c:choose>
										    <c:when test="${empty objective.description}">
										    	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
										    	<tr><td colspan="2" class="strat-detail-description">
										    		<p class="center-text"><span class="font-no-content">No Description</span></p>
									    		</td></tr>
										    </c:when>
									        <c:otherwise>
									        	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
										        <tr><td colspan="2" class="strat-detail-description">
											        <p class="center-text">Description<br/>...<br/>${objective.description}</p>
				                                </td></tr>
									        </c:otherwise>
										</c:choose>
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
                            <div class="divider-new">
                                <h2 class="h2-responsive">List of Projects</h2>
                            </div>
                        </div>
                    </div>
                    <!--/.First row-->

					<div class="row">
						<c:forEach items="${requestScope.objective.projects}" var="project">
							<div class="col-lg-4">
	                            <!--Card-->
	                            <div class="card  wow fadeIn" data-wow-delay="0.2s">
	                                <!--Card content-->
	                                <div class="card-block">
	                                    <!--Title-->
	                                    <h4 class="card-title"><c:out value="${project.name}" /></h4>
	                                    <!--Text-->
	                                    <p class="card-text"><c:out value="${project.status.label}" /></p>
	                                    <a href="ProjectAction?id=<c:out value="${project.id}" />" class="btn btn-default">Inspect project</a>
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
						<c:forEach items="${requestScope.objective.tasks}" var="task">
							<div class="col-lg-4">
	                            <!--Card-->
	                            <div class="card  wow fadeIn" data-wow-delay="0.2s">
	                                <!--Card content-->
	                                <div class="card-block">
	                                    <!--Title-->
	                                    <h4 class="card-title"><c:out value="${task.name}" /></h4>
	                                    <!--Text-->
	                                    <p class="card-text"><c:out value="${task.completion}" />%</p>
	                                    <a href="#" class="btn btn-default">Inspect task</a>
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
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>