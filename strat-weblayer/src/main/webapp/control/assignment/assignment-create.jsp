<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor" %>

<!--Modal: Objective Form-->
<div class="modal fade" id="addAssignments" tabindex="-1" role="dialog" aria-labelledby="assignmentModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
                <h4 class="title"><i class="fa fa-group"></i>New Assignments</h4>
            </div>
            <!--Body-->
            <form action="Assignment" method="post">
	            <div class="modal-body">
   	            	<input type="hidden" name="objectiveId" value="${objective.id}" />
	            	<input type="hidden" name="projectId" value="${project.id}" />
	            	<input type="hidden" name="submoduleId" value="${submodule.id}" />
	            	<input type="hidden" name="taskId" value="${task.id}" />
          			<div class="md-form form-sm">
              			<label class="full-width center-text">Select which users to delegate</label>
              			<br/>
               		</div>
	           		<div class="form-sm" id="objform35">
						<c:forEach items="${requestScope.userClusters}" var="cluster">
							<c:if test="${not empty cluster[0]}">
								<hr/><div class="full-width center-text"><span><c:out value="${cluster[0].role.label}" />s</span></div><hr/>
							</c:if>
							<c:forEach items="${cluster}" var="user">
			                    <div class="checkbox-animated">
		                          <label class="label--checkbox larger-font">
							          <input type="checkbox" class="checkbox">
							            ${user.name}
							      </label>
			                    </div>
							</c:forEach>
							<br/>
						</c:forEach>
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