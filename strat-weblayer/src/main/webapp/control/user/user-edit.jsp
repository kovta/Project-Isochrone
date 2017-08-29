<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Objectives</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:useBean id="user" class="com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor" scope="request" />
	<br/><br/><br/><br/><br/>
	
	<div class="wrapper">
		<br/><br/>
		<div class="container">
			<div class="card card-cascade narrower mb-r">
		        <div class="admin-panel info-admin-panel">
		            <!--Card heading-->
		            <div class="card-header mdb-color darken-1 white-text">
		                <h5><i class="fa fa-edit"></i><span class="icon-companion"> Edit Profile</span></h5>
		            </div>
		            <!--/Card heading-->
		            <!--Card content-->
		            <div class="card-block">
		                <!-- Edit Form -->
		                <form action="User" method="post">
		                	<c:if test="${requestScope.user.getId() != -1}"><input type="hidden" name="id" value="${user.id}" /></c:if>
		                    <!--First row-->
		                    <div class="row">
		                        <!--First column-->
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>Email address</label><br/>
	                  				</div>
		                            <div class="md-form">
		                                <input type="text" id="form1" class="form-control validate" name="email" 
		                                	placeholder="example@mail.com" value="${user.email}">
		                            </div>
		                        </div>
		                    </div>
   		                    <div class="row">
		                        <div class="col-md-6 text-center">
		                        	<a href="User?id=<c:out value="${user.id}" />" class="btn btn-outline-info mr-auto full-form-footer-button">
		                        		Cancel<i class="fa fa-times-circle ml-1"></i>
		                        	</a>
		                        </div>
		                        <div class="col-md-6 text-center">
									<button type="submit" name="submit" class="btn mdb-color darken-1 ml-auto full-form-footer-button">
		            					Update Account <i class="fa fa-save ml-1"></i>
		            				</button>
		                        </div>
		                    </div>
	                    </form>
	                	<!-- Edit Form -->
	            	</div>
	            	<!--/.Card content-->
		        </div>
		    </div>
		    <br/><br/>
		    
		    <!-- Avatar selection -->
		    <div class="card card-cascade narrower mb-r">
		        <div class="admin-panel info-admin-panel">
		            <div class="card-header mdb-color darken-1 white-text">
		                <h5><i class="fa fa-edit"></i><span class="icon-companion"> Choose Avatar</span></h5>
		            </div>
		            <div class="card-block">
		            	<div class="row">
		            		<c:set var="count" value="0" scope="request"/>
							<c:forEach begin="0" end="27" varStatus="loop">
								<jsp:include page="user-avatar-edit-card.jsp"></jsp:include>
								<c:set var="count" value="${count + 1}" scope="request"/>
							</c:forEach>
						</div>
	            	</div>
		        </div>
		    </div>
		
		   	<!-- Modals -->
   			<jsp:include page="user-alert.jsp"></jsp:include>
			<jsp:include page="../../modal/logout.jsp"></jsp:include>
			<!-- /Modals -->
		
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>