<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Modal: Team Form-->
<div class="modal fade" id="addTeam" tabindex="-1" role="dialog" aria-labelledby="teamModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
                <h4 class="title"><i class="fa fa-group"></i>New Team</h4>
            </div>
            <!--Body-->
            <form action="Team" method="post">
	            <div class="modal-body">
	                <div class="md-form form-sm">
	                    <i class="fa fa-font prefix"></i>
	                    <input type="text" id="objform31" class="form-control" name="name" placeholder="Name" value="${team.name}" required>
	                </div>
            		
					<div class="md-form form-sm">
						<i class="fa fa-lightbulb-o prefix"></i>
               			<label>Appoint Team Leader</label>
               			<br/>
                  	</div>
	                <div class="md-form form-sm">
						<select class="form-control" id="objform33" name="leader">
							<c:forEach items="${requestScope.users}" var="user">
								<option value="<c:out value="${user.name}" />">
									${user.name} - ${user.role.label}<c:out value="${not empty user.email ? ' - ' : ''}" />${user.email}
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
<!--Modal: Team Form-->