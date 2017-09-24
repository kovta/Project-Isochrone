<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Teams</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:include page="../../partial/authority.jsp"></jsp:include>
	<jsp:useBean id="team" class="com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor" scope="request" />
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
                                <div class="padding-top"><h3 class="h3-responsive text-center">${team.name}</h3></div>
                                <div class="md-form">
                                	<jsp:include page="team-detail-table.jsp"></jsp:include>
                                </div>
	                        </div>
	                    </div>
						<c:if test="${operator eq team.creator.name or operator eq team.leader.name}">
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
							  		   			    <a href="Team?id=<c:out value="${team.id}"/>&edit=1" class="vertical-align-middle text-center full-width">
								       			    	<i class="fa fa-edit" aria-hidden="true"></i> Edit Team
								       			    </a>
												</td></tr>
												<tr class="match-row"><td>
													<button type="button" class="btn mdb-color ml-auto darken-1 full-width" data-target="#addTeamMembers" data-toggle="modal">
												    	<i class="fa fa-group tile-icon"></i><span class="icon-companion">Distribute Memberships</span>
													</button>
												</td></tr>
												<c:if test="${operator eq team.creator.name}">
													<tr class="match-row"><td>
														<hr/>
														<button type="button" class="btn btn-danger ml-auto full-width" data-target="#deleteTeam" data-toggle="modal">
													    	<i class="fa fa-trash tile-icon"></i><span class="icon-companion">Delete Team</span>
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
	                                    <a class="nav-link waves-light waves-effect waves-light active" data-toggle="tab" href="#memberPanel" role="tab" aria-expanded="false">
		                                    <span>Team Members (<c:out value="${team.members.size()}" />)</span>
	                                    </a>
	                                 </li>
									 <li class="nav-item tab-listener">
		                             	 <a class="nav-link waves-light waves-effect waves-light" data-toggle="tab" 
		                             	 	href="#assignmentPanel" role="tab" aria-expanded="true">
		                                 	<span>Assignments (<c:out value="${team.objectives.size() 
		                                 	 	+ team.projects.size() + team.submodules.size() + team.tasks.size()}" />)
		                                 	 </span>
		                                 </a>
                                 	 </li>
	                             </ul>
	                         </div>
	                         <br/>
	                         <!-- Tab panels -->
	                         <div class="tab-content">
								 <!--Panel 1-->
	                             <div class="tab-pane fade active show" id="memberPanel" role="tabpanel" aria-expanded="false">
								     <c:choose>
									     <c:when test="${team.members.size() == 0}">
											<div class="row wow fadeIn" data-wow-delay="0.2s">
	                    					    <div class="col-lg-12">
				                           			<div class="text-center content-padder">
				                               			<h2 class="h2-responsive">There are currently no Users</h2>
				                               			<h2 class="h2-responsive">assigned to this Team</h2>
				                               		</div>
			                               		</div>
		                               		</div>
										</c:when>
										<c:otherwise>
											<div class="row">
												<c:forEach items="${requestScope.team.members}" var="member">
													<div class="col-lg-4">
									                    <br/><br/><br/>
															<c:set var="user" value="${member}" scope="request" />
															<jsp:include page="../user/user-card.jsp"></jsp:include>
									                    <br/>
									            	</div>
												</c:forEach>
											</div>		    
										</c:otherwise>
									</c:choose>
	                             </div>
	                             <!--Panel 2-->
	                             <div class="tab-pane fade" id="assignmentPanel" role="tabpanel" aria-expanded="false">
				                     <jsp:include page="team-assignment-panel.jsp"></jsp:include>
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
			<jsp:include page="team-member-addition.jsp"></jsp:include>
			<jsp:include page="team-delete.jsp"></jsp:include>
			<jsp:include page="team-alert.jsp"></jsp:include>
			<jsp:include page="../../modal/logout.jsp"></jsp:include>
			<!-- /Modals -->
            
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>