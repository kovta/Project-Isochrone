<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Modal: Task Move Form-->
<div class="modal fade" id="moveTask" tabindex="-1" role="dialog" aria-labelledby="TaskModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
                <h4 class="title"><i class="fa fa-arrow-right"></i>Move Task</h4>
            </div>
            <!--Body-->
            <form action="MoveTask" method="post">
	            <div class="modal-body">
	            	<input type="hidden" name="taskId" value="${task.id}" />
	            	
	            	<!--First row-->
                    <div class="row">
                        <div class="col-md-12">
							<div class="md-form form-sm full-width text-center">
			           			<h5>By moving this Task it will be effectively detached from any dependency network were it was previously configured.</h5>
			           		</div>
                        	<div class="md-form form-sm">
		               			<label>The future destination of the Task</label><br/>
                 			</div>
			                <div class="md-form form-sm">
								<select class="form-control" id="objform33" name="destination">
									<c:forEach items="${requestScope.destinations}" var="submodule">
										<option value="${submodule.id}" ${submodule.id == task.submodule.id ? 'selected="selected"' : ''}>
											${submodule.name}
										</option>
									</c:forEach>
								</select>
			                </div>
                        </div>
                    </div>
	            	
	            </div>
	            <!--Footer-->
	            <div class="modal-footer">
	                <button type="button" class="btn btn-outline-info mr-auto" data-dismiss="modal">
	                	<span>Cancel</span><i class="fa fa-times-circle ml-1"></i>
	                </button>
	            	<button type="submit" name="submit" class="btn mdb-color darken-1 ml-auto">
	            		<span>Save</span><i class="fa fa-save ml-1"></i>
	            	</button>
	            </div>
            </form>
        </div>
        <!--/.Content-->
    </div>
</div>
<!--Modal: Task Move Form-->