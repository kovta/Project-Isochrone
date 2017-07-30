<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!--Modal: Task Delete Form-->
<div class="modal fade" id="deleteTask" tabindex="-1" role="dialog" aria-labelledby="TaskModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header danger-color-dark white-text">
                <h4 class="title"><i class="fa fa-trash"></i>Delete Task</h4>
            </div>
            <!--Body-->
            <form action="TaskDelete" method="post">
	            <div class="modal-body">
	            	<input type="hidden" name="id" value="${task.id}" />
	      			<div class="md-form form-sm full-width center-text">
	           			<h3>Are you sure you want to delete this Task?</h3>
	           		</div>
	            </div>
	            <!--Footer-->
	            <div class="modal-footer">
	                <button type="button" class="btn btn-outline-info mr-auto" data-dismiss="modal">
	                	<span>Cancel</span><i class="fa fa-times-circle ml-1"></i>
	                </button>
	            	<button type="submit" name="submit" class="btn danger-color-dark ml-auto">
	            		<span>Delete</span><i class="fa fa-trash-o ml-1"></i>
	            	</button>
	            </div>
            </form>
        </div>
        <!--/.Content-->
    </div>
</div>
<!--Modal: Task Delete Form-->