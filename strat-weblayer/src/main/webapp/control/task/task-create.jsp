<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Modal: Task Creation Form-->
<div class="modal fade" id="addTask" tabindex="-1" role="dialog" aria-labelledby="taskModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
				<h4 class="title"><i class="fa fa-tasks"></i>New Task</h4>
            </div>
            <!--Body-->
            <form action="Task" method="post">
	            <div class="modal-body">
	            	<input type="hidden" name="objectiveId" value="${objective.id}" />
	            	<input type="hidden" name="projectId" value="${project.id}" />
	            	<input type="hidden" name="submoduleId" value="${submodule.id}" />
	                <div class="md-form form-sm">
	                    <i class="fa fa-font prefix"></i>
	                    <input type="text" id="objform31" class="form-control" name="name" placeholder="Name" value="${task.name}" required>
	                </div>

            		<div class="row">
            			<div class="col-md-6">
		            		<div class="md-form form-sm">
		            			<i class="fa fa-exclamation prefix"></i>
		               			<label>Set Priority</label>
		               			<br/>
		                  	</div>
		               		<div class="md-form form-sm">
		               			<c:choose>
			      			      	<c:when test = "${task != null}">
				         				<input type="number" id="objform32" class="form-control" name="priority" 
					                    	min="0" max="100" value="${task.priority}" required>
					                </c:when>
					                <c:otherwise>
					                	<input type="number" id="objform32" class="form-control" name="priority" 
					                    	min="0" max="100" value="10" required>
					                </c:otherwise>
				                </c:choose>
			                </div>
		                </div>
	            		<div class="col-md-6">
							<div class="md-form form-sm">
								<div class="md-form form-sm">
									<i class="fa fa-ticket prefix"></i>
			               			<label>Set Admittance policy</label>
			               			<br/>
			                  	</div>
								<div class="input-group">
				    				<div id="radioBtn-adm" class="btn-group">
				    					<a class="btn-sm notActive" data-toggle="admittance" data-title="1">Open</a>
				    					<a class="btn-sm active" data-toggle="admittance" data-title="0">Closed</a>
				    				</div>
				    				<input type="hidden" name="admittance" id="admittance">
				    			</div>
			                </div>
		                </div>
            		</div>
            		
            		<div class="md-form form-sm">
						<i class="fa fa-calendar-check-o prefix"></i>
               			<label>Set Deadline</label>
               			<br/>
                 	</div>
            		<div class="md-form form-sm" id="sandbox-container">
						<input placeholder="MM/dd/yyyy" type='text' class="form-control" name="deadline" value="${submodule.deadline}"/>
	                </div>
	                
	                <div class="md-form form-sm">
						<i class="fa fa-hourglass-end prefix"></i>
               			<label>Set Expected Duration</label>
               			<div class="input-group margin-left">
		    				<div id="radioBtn-dur" class="btn-group">
		    					<a class="btn-sm notActive" data-toggle="durationType" data-title="0">Set Duration</a>
		    					<a class="btn-sm active" data-toggle="durationType" data-title="1">Estimations</a>
		    				</div>
		    				<input type="hidden" name="durationType" id="durationType">
		    			</div>
               			<br/>
               			<div class="md-form form-sm">
	         				<input type="number" id="durationField" class="form-control" name="duration" 
		                    	min="0" placeholder="Set Duration in days" value="">
		                </div>
		                <div class="row">
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
            		
					<div class="md-form form-sm">
						<i class="fa fa-percent prefix"></i>
               			<label>Set Completion</label>
               			<br/>
                  	</div>
					<div class="md-form form-sm">
						<input class="full-width slider-horizontal" id="compslider" type="text" name="completion" value="${task.completion}"
						data-slider-ticks="[0, 25, 50, 75, 100]" data-slider-ticks-snap-bounds="7" 
						data-slider-ticks-labels='["0%", "25%", "50%", "75%", "100%"]' data-value="${task.completion}"/>
	                </div>
            		
	                <div class="md-form form-sm">
	                    <i class="fa fa-file-text prefix"></i>
	                    <textarea type="text" id="objform34" class="md-textarea" class="form-control" 
	                    name="description" placeholder="Description" value="${task.description}"></textarea>
	                </div>
	            </div>
	            <!--Footer-->
	            <div class="modal-footer">
	                <button type="button" class="btn btn-outline-info mr-auto" data-dismiss="modal">
	                	Cancel <i class="fa fa-times-circle ml-1"></i>
	                </button>
	            	<button type="submit" name="submit" class="btn mdb-color darken-1 ml-auto">
	            		Save <i class="fa fa-save ml-1"></i>
	            	</button>
	            </div>
            </form>
        </div>
        <!--/.Content-->
    </div>
</div>
<!--Modal: Task Creation Form-->
