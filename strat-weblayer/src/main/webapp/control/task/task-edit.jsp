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
	
	<c:set var="supervisor" value="false"/>
	<c:if test="${operator eq task.creator.name or operator eq task.objective.creator.name 
		or operator eq task.project.creator.name or operator eq task.submodule.creator.name 
		or operator eq task.submodule.project.creator.name or operator eq task.submodule.project.objective.creator.name}">
		<c:set var="supervisor" value="true"/>
	</c:if>
	<div class="wrapper">
		<br/><br/>
		<div class="container">
			<div class="card card-cascade narrower mb-r">
		        <div class="admin-panel info-admin-panel">
		            <!--Card heading-->
		            <div class="card-header mdb-color darken-1 white-text">
		                <h5><i class="fa fa-edit"></i><span class="icon-companion"> Edit Task</span></h5>
		            </div>
		            <!--Card content-->
		            <div class="card-block">
		                <!-- Edit Form -->
		                <form action="Task" method="post">
		                	<c:if test="${requestScope.task.getId() != -1}"><input type="hidden" name="id" value="${task.id}" /></c:if>
		                    <!--First row-->
		                    <div class="row">
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>The name of the Task</label><br/>
	                  				</div>
		                            <div class="md-form">
		                                <input type="text" id="form1" class="form-control validate" name="name" 
		                                	placeholder="The name of the Task" value="${task.name}" ${not supervisor ? "disabled" : ""} required>
		                            </div>
		                        </div>
		                    </div>
		                    <!--/.First row-->
		                    <c:choose>
						    	<c:when test="${supervisor}">
				                    <div class="row">
				                        <div class="col-md-6">
				                        	<div class="md-form form-sm">
						               			<label>The priority of the Task</label><br/>
			                  				</div>
				                            <div class="md-form">
				                                <input type="text" name="priority" id="form81" class="form-control validate" 
				                                	value="${task.priority}" ${not supervisor ? "disabled" : ""} required>
				                            </div>
				                        </div>
				                        <div class="col-md-6">
				                        	<div class="md-form form-sm">
						               			<label>The admittance policy of the Task</label><br/>
			                  				</div>
											<div class="input-group">
							    				<div id="radioBtn-adm" class="btn-group">
				  			            			<c:choose>
								      			      	<c:when test="${task.admittance == false}">
									    					<a class="btn-sm notActive" data-toggle="admittance" data-title="1">Open</a>
									    					<a class="btn-sm active" data-toggle="admittance" data-title="0">Closed</a>
										                </c:when>
										                <c:otherwise>
									    					<a class="btn-sm active" data-toggle="admittance" data-title="1">Open</a>
									    					<a class="btn-sm notActive" data-toggle="admittance" data-title="0">Closed</a>
										                </c:otherwise>
									                </c:choose>
							    				</div>
							    				<input type="hidden" name="admittance" id="admittance">
							    			</div>
				                        </div>
				                    </div>
		                    	</c:when>
		                    	<c:otherwise>
		                    		<div class="row">
		                    			<div class="col-md-12">
				                        	<div class="md-form form-sm">
						               			<label>The priority of the Task</label><br/>
			                  				</div>
				                            <div class="md-form">
				                                <input type="text" name="priority" id="form81" class="form-control validate" 
				                                	value="${task.priority}" disabled required>
				                            </div>
				                        </div>
			                        </div>
		                    	</c:otherwise>
						    </c:choose>
						    <!--/.Second row-->
		                    <div class="row">
		                        <div class="col-md-6">
		                        	<div class="md-form form-sm">
				               			<label>The deadline of the Task</label><br/>
	                  				</div>
		                            <div class="md-form" id="sandbox-container">
										<div class="flex-display">
											<input placeholder="None" type="text" class="form-control" name="deadline" ${not supervisor ? "disabled" : ""}
												value="<fmt:formatDate type="date" value="${task.deadline}" pattern="MM/dd/yyyy" />"/>
										</div>
		                            </div>
								</div>
								<div class="col-md-6">
									<div class="md-form form-sm">
			               				<label>The expected duration of the Task</label>
			               				<div class="input-group margin-left">
						    				<div id="radioBtn-dur" class="btn-group">
						    					<a class="btn-sm active" data-toggle="durationType" data-title="0">Set Duration</a>
						    					<a class="btn-sm notActive" data-toggle="durationType" data-title="1">Estimations</a>
						    				</div>
						    				<input type="hidden" name="durationType" id="durationType">
						    			</div>
			               			</div>
				               		<div class="md-form">
				               			<div class="flex-display" id="setDurationRow">
					         				<input type="number" id="durationField" class="form-control" name="duration" 
						                    	min="0" placeholder="Set Duration in days" value="">
						                </div>
						                <div class="row no-display" id="estimationRow">
				            				<div class="col-md-4">
				            					<input type="number" id="pessimisticDurationField" class="form-control" style="width: 105%"
				            						name="pessimisticDuration" min="0" placeholder="Pessimistic estimate" value="">
				            				</div>
				            				<div class="col-md-4">
				            					<input type="number" id="realisticDurationField" class="form-control" style="width: 105%" 
				            						name="realisticDuration" min="0" placeholder="Realistic estimate" value="">
				            				</div>
				            				<div class="col-md-4">
				            					<input type="number" id="optimisticDurationField" class="form-control" 
				            						name="optimisticDuration" min="0" placeholder="Optimisitc estimate" value="">
				            				</div>
				            			</div>
				            		</div>
								</div>
							</div>
							<!--/.Third row-->
		                    <div class="row">
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>The completion of the Task</label><br/>
	                  				</div>
                      				<div class="md-form form-sm">
										<input class="full-width" id="compslider" type="text" name="completion" value="${task.completion}"
										data-slider-ticks="[0, 25 50, 75, 100]" data-slider-ticks-snap-bounds="7" 
										data-slider-ticks-labels='["0%", "25%", "50%", "75%", "100%"]'/>
					                </div>
								</div>
							</div>
   		                    <!--Fourth row-->
		                    <div class="row">
		                        <div class="col-md-12">
		                        	<div class="md-form form-sm">
				               			<label>The description of the Task</label><br/>
	                  				</div>
		                            <div class="md-form">
		                                <textarea type="text" id="form78" class="md-textarea" name="description" value="${task.description}">
		                                	<c:out value="${task.description}"/> ${not supervisor ? "disabled" : ""} </textarea>
		                            </div>
		                        </div>
		                    </div>
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
		                </form>
		                <!-- Edit Form -->
		            </div>
		            <!--/.Card content-->
		        </div>
		    </div>
		    
   			<!-- Modals -->
			<jsp:include page="task-alert.jsp"></jsp:include>
			<jsp:include page="../../modal/logout.jsp"></jsp:include>
			<!-- /Modals -->
		    
		</div>
		<div class="push"></div>
	</div>
	
	<jsp:include page="../../partial/copyright.jsp"></jsp:include>
	<jsp:include page="../../partial/wow.jsp"></jsp:include>
	
</body>
</html>