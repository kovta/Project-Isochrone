<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor" %>
<%@ page import="com.kota.stratagem.weblayer.common.task.TaskAttribute" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem - Tasks</title>
	<jsp:include page="../../header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../partial/navbar-fill.jsp"></jsp:include>
	<jsp:useBean id="task" class="com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor" scope="request" />
	<br/><br/><br/><br/><br/>
	
	<div class="wrapper">
		<br/><br/>
		<div class="container">
			<div class="card card-cascade narrower mb-r">
		        <div class="admin-panel info-admin-panel">
		            <!--Card heading-->
		            <div class="card-header mdb-color darken-1 white-text">
		                <h5><i class="fa fa-edit"></i><span class="icon-companion"> Edit Task</span></h5>
		            </div>
		            <!--/Card heading-->
		            <!--Card content-->
		            <div class="card-block">
		                <!-- Edit Form -->
		                <form action="Task" method="post">
		                	
		                	<c:if test="${requestScope.task.getId() != -1}"><input type="hidden" name="id" value="${task.id}" /></c:if>
		                    <!--First row-->
		                    <div class="row">
		                        <!--First column-->
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>The name of the Task</label><br/>
	                  				</div>
		                            <div class="md-form">
		                                <input type="text" id="form1" class="form-control validate" name="name" 
		                                	placeholder="The name of the Task" value="${task.name}" required>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/.First row-->
		                    <!--Second row-->
		                    <div class="row">
		                        <!--First column-->
		                        <div class="col-md-6">
		                        	<div class="md-form form-sm">
				               			<label>The priority of the Task</label><br/>
	                  				</div>
		                            <div class="md-form">
		                                <input type="text" name="priority" id="form81" class="form-control validate" value="${task.priority}" required>
		                            </div>
		                        </div>
		                        <!--Second column-->
		                        <div class="col-md-6">
		                        	<div class="md-form form-sm">
				               			<label>The deadline of the Task</label><br/>
	                  				</div>
		                            <div class="md-form" id="sandbox-container">
										<input placeholder="None" type="text" class="form-control" name="deadline" 
											value="<fmt:formatDate type="date" value="${task.deadline}" pattern="MM/dd/yyyy" />"/>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/.Second row-->
		                    <!--Third row-->
		                    <div class="row">
		                        <!--First column-->
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>The name of the Task</label><br/>
	                  				</div>
                       					<div class="md-form form-sm">
											<input class="full-width" id="compslider" type="text" name="completion" value="${task.completion}"
											data-slider-ticks="[0, 25 50, 75, 100]" data-slider-ticks-snap-bounds="7" 
											data-slider-ticks-labels='["0%", "25%", "50%", "75%", "100%"]'/>
											<!-- <input type="text" id="slider-input" name="completion" class="slider" value="${task.completion}" /> -->
						                </div>
		                            </div>
		                        </div>
		                    <!--/.Third row-->
   		                    <!--Fourth row-->
		                    <div class="row">
		                        <!--First column-->
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>The description of the Task</label><br/>
	                  				</div>
		                            <div class="md-form">
		                                <textarea type="text" id="form78" class="md-textarea" name="description" value="${task.description}">
		                                <c:out value="${task.description}"/>
		                                </textarea>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/.Fourth row-->
		                    <!-- Fifth row -->
		                    <div class="row">
		                        <div class="col-md-6 text-center">
		                        	<a href="Task?id=<c:out value="${task.id}" />" class="btn btn-outline-info mr-auto full-form-footer-button">
		                        		Cancel<i class="fa fa-times-circle ml-1"></i>
		                        	</a>
		                        </div>
		                        <div class="col-md-6 text-center">
									<button type="submit" name="submit" class="btn mdb-color darken-1 ml-auto full-form-footer-button">
		            					Update Task <i class="fa fa-save ml-1"></i>
		            				</button>
		                        </div>
		                    </div>
		                    <!-- /.Fifth row -->
		                </form>
		                <!-- Edit Form -->
		            </div>
		            <!--/.Card content-->
		        </div>
		    </div>
		    
   			<!-- Modals -->
			<jsp:include page="task-alert.jsp"></jsp:include>
			<!-- /Modals -->
		    
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>