<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Modal: Objective Form-->
<div class="modal fade" id="addSubmodule" tabindex="-1" role="dialog" aria-labelledby="submoduleModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
				<h4 class="title"><i class="fa fa-list-alt"></i>New Submodule</h4>
            </div>
            <!--Body-->
            <form action="Submodule" method="post">
	            <div class="modal-body">
	            	<input type="hidden" name="projectId" value="${project.id}" />
	                <div class="md-form form-sm">
	                    <i class="fa fa-font prefix"></i>
	                    <input type="text" id="objform31" class="form-control" name="name" placeholder="Name" value="${submodule.name}">
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
	                    <i class="fa fa-file-text prefix"></i>
	                    <textarea type="text" id="objform34" class="md-textarea" class="form-control" 
	                    name="description" placeholder="Description" value="${submodule.description}"></textarea>
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