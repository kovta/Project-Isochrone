<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor" %>
<%@ page import="com.kota.stratagem.weblayer.common.submodule.SubmoduleAttribute" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Submodules</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:useBean id="submodule" class="com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor" scope="request" />
	<br/><br/><br/><br/><br/>
	
	<div class="wrapper">
		<br/><br/>
		<div class="container">
			<div class="card card-cascade narrower mb-r">
		        <div class="admin-panel info-admin-panel">
		            <!--Card heading-->
		            <div class="card-header mdb-color darken-1 white-text">
		                <h5><i class="fa fa-edit"></i><span class="icon-companion"> Edit Submodule</span></h5>
		            </div>
		            <!--/Card heading-->
		            <!--Card content-->
		            <div class="card-block">
		                <!-- Edit Form -->
		                <form action="Submodule" method="post">
		                	
		                	<c:if test="${requestScope.submodule.getId() != -1}"><input type="hidden" name="id" value="${submodule.id}" /></c:if>
		                    <!--First row-->
		                    <div class="row">
		                        <!--First column-->
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>The name of the Submodule</label><br/>
	                  				</div>
		                            <div class="md-form">
		                                <input type="text" id="form1" class="form-control validate" name="name" 
		                                	placeholder="The name of the Submodule" value="${submodule.name}" required>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/.First row-->
		                    <!--Second row-->
		                    <div class="row">
		                        <!--First column-->
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>The deadline of the Submodule</label><br/>
	                  				</div>
		                            <div class="md-form" id="sandbox-container">
										<input placeholder="None" type="text" class="form-control" name="deadline" 
											value="<fmt:formatDate type="date" value="${submodule.deadline}" pattern="MM/dd/yyyy" />"/>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/.Second row-->
   		                    <!--Third row-->
		                    <div class="row">
		                        <!--First column-->
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>The description of the Submodule</label><br/>
	                  				</div>
		                            <div class="md-form">
		                                <textarea type="text" id="form78" class="md-textarea" name="description" value="${submodule.description}">
		                                <c:out value="${submodule.description}"/>
		                                </textarea>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/.Third row-->
		                    <!-- Fourth row -->
		                    <div class="row">
		                        <div class="col-md-6 text-center">
		                        	<a href="Submodule?id=<c:out value="${submodule.id}" />" class="btn btn-outline-info mr-auto full-form-footer-button">
		                        		Cancel<i class="fa fa-times-circle ml-1"></i>
		                        	</a>
		                        </div>
		                        <div class="col-md-6 text-center">
									<button type="submit" name="submit" class="btn mdb-color darken-1 ml-auto full-form-footer-button">
		            					Update Submodule <i class="fa fa-save ml-1"></i>
		            				</button>
		                        </div>
		                    </div>
		                    <!-- /.Fourth row -->
		                </form>
		                <!-- Edit Form -->
		            </div>
		            <!--/.Card content-->
		        </div>
		    </div>
		    
		    <!-- Modals -->
			<jsp:include page="submodule-alert.jsp"></jsp:include>
			<jsp:include page="../../modal/logout.jsp"></jsp:include>
			<!-- /Modals -->
		    
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>