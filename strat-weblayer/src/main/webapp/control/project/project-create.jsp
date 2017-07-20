<%@ page import="com.kota.stratagem.weblayer.common.objective.ObjectiveAttribute" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor"%>
<%@ page import="com.kota.stratagem.weblayer.common.project.ProjectAttribute" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor"%>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Modal: Objective Form-->
<div class="modal fade" id="addProject" tabindex="-1" role="dialog" aria-labelledby="projcetModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
                <h4 class="title"><i class="fa fa-sitemap"></i>New Project</h4>
            </div>
            <!--Body-->
            <form action="ProjectAction" method="post">
	            <div class="modal-body">
	            	<input type="hidden" name="objectiveId" value="${objective.id}" />
	                <div class="md-form form-sm">
	                    <i class="fa fa-font prefix"></i>
	                    <input type="text" id="objform31" class="form-control" name="name" placeholder="Name" value="${project.name}">
	                </div>
            	
					<div class="md-form form-sm">
						<i class="fa fa-lightbulb-o prefix"></i>
               			<label>Set status</label>
               			<br/>
                  	</div>
	                <div class="md-form form-sm">
						<select class="form-control" id="objform33" name="status">
							<% for ( ProjectStatusRepresentor status : ProjectStatusRepresentor.values()) { %>
								<option value="<% out.print(status.name()); %>"><% out.print(status.getLabel()); %></option>
							<% } %>
						</select>
	                </div>

            		<div class="row">
	            		<div class="col-md-6">
	            			<div class="md-form form-sm">
								<i class="fa fa-calendar-check-o prefix"></i>
		               			<label>Set Deadline</label>
		               			<br/>
	                  		</div>
		            		<div class="md-form form-sm" id="sandbox-container">
								<input placeholder="MM/dd/yyyy" type='text' class="form-control" name="deadline" value="${project.deadline}"/>
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
	                    name="description" placeholder="Description" value="${project.description}"></textarea>
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