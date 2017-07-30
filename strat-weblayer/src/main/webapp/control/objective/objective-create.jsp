<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.kota.stratagem.weblayer.common.objective.ObjectiveAttribute" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor" %>

<!--Modal: Objective Form-->
<div class="modal fade" id="addObjective" tabindex="-1" role="dialog" aria-labelledby="objectiveModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
                <h4 class="title"><i class="fa fa-flag"></i>New Objective</h4>
            </div>
            <!--Body-->
            <form action="Objective" method="post">
	            <div class="modal-body">
	                <div class="md-form form-sm">
	                    <i class="fa fa-font prefix"></i>
	                    <input type="text" id="objform31" class="form-control" name="name" placeholder="Name" value="${objective.name}">
	                </div>
            	
            		<div class="row">
            			<div class="col-md-6">
		            		<div class="md-form form-sm">
		            			<i class="fa fa-exclamation prefix"></i>
		               			<label>Set priority</label>
		               			<br/>
		                  	</div>
		               		<div class="md-form form-sm">
		               			<c:choose>
			      			      	<c:when test = "${objective != null}">
				         				<input type="number" id="objform32" class="form-control" name="priority" 
					                    min="0" max="100" value="${objective.priority}">
					                </c:when>
					                <c:otherwise>
					                	<input type="number" id="objform32" class="form-control" name="priority" 
					                    min="0" max="100" value="10">
					                </c:otherwise>
				                </c:choose>
			                </div>
		                </div>
		                <div class="col-md-6">
							<div class="md-form form-sm">
								<i class="fa fa-lightbulb-o prefix"></i>
		               			<label>Set status</label>
		               			<br/>
		                  	</div>
			                <div class="md-form form-sm">
								<select class="form-control" id="objform33" name="status">
									<% for ( ObjectiveStatusRepresentor status : ObjectiveStatusRepresentor.values()) { %>
										<option value="<% out.print(status.name()); %>"><% out.print(status.getLabel()); %></option>
									<% } %>
								</select>
			                </div>
		                </div>
	                </div>

            		<div class="row">
	            		<div class="col-md-6">
	            			<div class="md-form form-sm">
								<i class="fa fa-calendar-check-o prefix"></i>
		               			<label>Set Deadline</label>
		               			<br/>
	                  		</div>
		            		<div class="md-form form-sm" id="sandbox-container">
								<input placeholder="MM/dd/yyyy" type='text' class="form-control" name="deadline" value="${objective.deadline}"/>
			                </div>
		                </div>
            			<div class="col-md-6">
							<div class="md-form form-sm">
								<div class="md-form form-sm">
									<i class="fa fa-shield prefix"></i>
			               			<label>Set Confidentiality</label>
			               			<br/>
			                  	</div>
								<div class="input-group">
				    				<div id="radioBtn" class="btn-group">
				    					<a class="btn-sm active" data-toggle="confidentiality" data-title="0">Public</a>
				    					<a class="btn-sm notActive" data-toggle="confidentiality" data-title="1">Private</a>
				    				</div>
				    				<input type="hidden" name="confidentiality" id="confidentiality">
				    			</div>
			                </div>
		                </div>
            		</div>
            		
	                <div class="md-form form-sm">
	                    <i class="fa fa-file-text prefix"></i>
	                    <textarea type="text" id="objform34" class="md-textarea" class="form-control" 
	                    name="description" placeholder="Description" value="${objective.description}"></textarea>
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
<!--Modal: Objective Form-->