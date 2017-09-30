<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Modal: Role Management Form-->
<div class="modal fade" id="modifiyRole" tabindex="-1" role="dialog" aria-labelledby="roleModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
				<h4 class="title"><i class="fa fa-list-alt"></i>Role Management</h4>
            </div>
            <!--Body-->
            <form action="UserRole" method="post">
	            <div class="modal-body">
	            	<input type="hidden" name="id" value="${requestScope.user.id}" />
					<div class="md-form form-sm">
						<i class="fa fa-lightbulb-o prefix"></i>
               			<label>Set User Role</label>
               			<br/>
                  	</div>
	                <div class="md-form form-sm">
						<select class="form-control" id="objform33" name="role">
							<c:forEach items="${requestScope.subordinateRoles}" var="role">
								<option value="${role.name()}" ${role == user.role ? 'selected="selected"' : ''}>
									${role.label}
								</option>
							</c:forEach>
						</select>
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
<!--Modal: Role Management Form-->