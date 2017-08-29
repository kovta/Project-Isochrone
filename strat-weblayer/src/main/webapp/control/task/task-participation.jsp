<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Modal: Task Participation Form-->
<div class="modal fade" id="participateTask" tabindex="-1" role="dialog" aria-labelledby="TaskModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
                <h4 class="title"><i class="fa fa-ticket"></i>Join Task collaboration</h4>
            </div>
            <!--Body-->
            <form action="AppUserAssignment" method="post">
	            <div class="modal-body">
	            	<input type="hidden" name="taskId" value="${task.id}" />
	            	<input type="hidden" name="assignments" value="${operator}">
	      			<div class="md-form form-sm full-width text-center">
	           			<h4>Are you sure you want to join this Task?</h4><br/>
	           			<h5>By doing so you will effectively be assigned to the task: <c:out value="${requestScope.task.name}" />.</h5>
	           		</div>
	            </div>
	            <!--Footer-->
	            <div class="modal-footer">
	                <button type="button" class="btn btn-outline-info mr-auto" data-dismiss="modal">
	                	<span>Cancel</span><i class="fa fa-times-circle ml-1"></i>
	                </button>
	            	<button type="submit" name="submit" class="btn mdb-color darken-1 ml-auto">
	            		<span>Join</span><i class="fa fa-check-circle ml-1"></i>
	            	</button>
	            </div>
            </form>
        </div>
        <!--/.Content-->
    </div>
</div>
<!--Modal: Task Participation Form-->