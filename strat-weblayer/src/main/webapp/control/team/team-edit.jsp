<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Teams</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:useBean id="team" class="com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor" scope="request" />
	<br/><br/><br/><br/><br/>
	
	<div class="wrapper">
		<br/><br/>
		<div class="container">
			<div class="card card-cascade narrower mb-r">
		        <div class="admin-panel info-admin-panel">
		            <!--Card heading-->
		            <div class="card-header mdb-color darken-1 white-text">
		                <h5><i class="fa fa-edit"></i><span class="icon-companion"> Edit Team</span></h5>
		            </div>
		            <!--/Card heading-->
		            <!--Card content-->
		            <div class="card-block">
		                <!-- Edit Form -->
		                <form action="Team" method="post">
		                	<c:if test="${requestScope.team.getId() != -1}"><input type="hidden" name="id" value="${team.id}" /></c:if>
		                    <!--First row-->
		                    <div class="row">
		                        <!--First column-->
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>The name of the Team</label><br/>
	                  				</div>
		                            <div class="md-form">
		                                <input type="text" id="form1" class="form-control validate" name="name" 
		                                	placeholder="The name of the Project" value="${team.name}" required>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/.First row-->
		                    <!--Second row-->
		                    <div class="row">
		                        <!--First column-->
		                        <div class="col-md-12">
									<div class="md-form form-sm">
										<i class="fa fa-lightbulb-o prefix"></i>
				               			<label>The leader of the Team</label>
				               			<br/>
				                  	</div>
					                <div class="md-form">
										<select class="form-control" id="objform33" name="leader">
											<c:forEach items="${requestScope.users}" var="user">
												<option value="<c:out value="${user.name}" />">
													${user.name} - ${user.role.label}<c:out value="${not empty user.email ? ' - ' : ''}" />${user.email}
												</option>
											</c:forEach>
										</select>
					                </div>
		                        </div>
		                    </div>
		                    <!--/.Second row-->
		                    <!-- Third row -->
		                    <div class="row">
		                        <div class="col-md-6 text-center">
		                        	<a href="Team?id=<c:out value="${team.id}" />" class="btn btn-outline-info mr-auto full-form-footer-button">
		                        		Cancel<i class="fa fa-times-circle ml-1"></i>
		                        	</a>
		                        </div>
		                        <div class="col-md-6 text-center">
									<button type="submit" name="submit" class="btn mdb-color darken-1 ml-auto full-form-footer-button">
		            					Update Team <i class="fa fa-save ml-1"></i>
		            				</button>
		                        </div>
		                    </div>
		                    <!-- /.Third row -->
		                </form>
		                <!-- Edit Form -->
		            </div>
		            <!--/.Card content-->
		        </div>
		    </div>
		    
		    <!-- Modals -->
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