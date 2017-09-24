<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor" %>

<!--Modal: Team Membership Form-->
<div class="modal fade" id="addTeamMembers" tabindex="-1" role="dialog" aria-labelledby="teamMemberModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
                <h4 class="title"><i class="fa fa-group"></i>New Team Members</h4>
            </div>
            <!--Body-->
            <form action="TeamMembershipAssignment" method="post">
	            <div class="modal-body">
       				<c:choose>
						<c:when test="${requestScope.userClusters.size() == 0}">
		          			<div class="md-form form-sm">
		              			<label class="full-width text-center">There are currently no membership opportunities</label>
		              			<br/>
		               		</div>
		      			</c:when>
						<c:otherwise>
							<input type="hidden" name="id" value="${team.id}" />
		          			<div class="md-form form-sm">
		              			<label class="full-width text-center">Select which users to add as Team Members</label>
		              			<br/>
		               		</div>
			           		<div class="form-sm" id="objform35">
								<c:forEach items="${requestScope.userClusters}" var="cluster">
									<c:if test="${not empty cluster[0]}">
										<hr/><div class="full-width text-center"><span><c:out value="${cluster[0].role.label}" />s</span></div><hr/>
									</c:if>
									<table>
										<colgroup>
											<col span="1" style="width: 15%;">
											<col span="1" style="width: 25%;">
											<col span="1" style="width: 60%;">
									    </colgroup>
										<tbody>
										<c:forEach items="${cluster}" var="user">
										<tr>
											<td class="text-center">
							                    <div class="checkbox-animated">
						                          <label class="label-checkbox larger-font">
											          <input type="checkbox" class="checkbox" name="members" value="${user.name}">
											      </label>
							                    </div>
						                    </td>
						                    <td class="text-center">${user.name}</td>
						                    <td class="text-center">${user.email}</td>
						                </tr>
										</c:forEach>
									</tbody></table>
									<br/>
								</c:forEach>
			                </div>
						</c:otherwise>
					</c:choose>
	            </div>
	            <!--Footer-->
	            <div class="modal-footer">
					<c:choose>
						<c:when test="${requestScope.userClusters.size() == 0}">
			                <button type="button" class="btn btn-outline-info mr-auto button-centered" data-dismiss="modal">
			                	Cancel <i class="fa fa-times-circle ml-1"></i>
			                </button>
		      			</c:when>
		      			<c:otherwise>
			                <button type="button" class="btn btn-outline-info mr-auto" data-dismiss="modal">
			                	Cancel <i class="fa fa-times-circle ml-1"></i>
			                </button>
			            	<button type="submit" name="submit" class="btn mdb-color darken-1 ml-auto">
			            		Save <i class="fa fa-save ml-1"></i>
			            	</button>
			            </c:otherwise>
	            	</c:choose>
	            </div>
            </form>
        </div>
        <!--/.Content-->
    </div>
</div>
<!--Modal: Team Membership  Form-->
