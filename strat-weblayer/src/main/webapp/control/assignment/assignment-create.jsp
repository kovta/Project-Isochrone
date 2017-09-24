<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor" %>

<!--Modal: Assignment Creation Form-->
<div class="modal fade" id="addAssignments" tabindex="-1" role="dialog" aria-labelledby="assignmentModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
        	<!--Modal cascading tabs-->
            <div class="modal-c-tabs">
	            
				<!-- Nav tabs -->
                <ul class="nav nav-tabs tabs-2 mdb-color darken-1" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#panel7" role="tab"><i class="fa fa-user mr-1"></i> User Assignments</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#panel8" role="tab"><i class="fa fa-group mr-1"></i> Team Assignments</a>
                    </li>
                </ul>
	            
				<!-- Tab panels -->
                <div class="tab-content">
	            	<!--Panel 1-->
	            	<div class="tab-pane fade in show active" id="panel7" role="tabpanel">
			            <form action="AppUserAssignment" method="post">
				            <div class="modal-body">
			       				<c:choose>
									<c:when test="${requestScope.userClusters.size() == 0}">
					          			<div class="md-form form-sm">
					              			<label class="full-width text-center">There are currently no assignable Users</label>
					              			<br/>
					               		</div>
					      			</c:when>
									<c:otherwise>
										<input type="hidden" name="objectiveId" value="${objective.id}" />
						            	<input type="hidden" name="projectId" value="${project.id}" />
						            	<input type="hidden" name="submoduleId" value="${submodule.id}" />
						            	<input type="hidden" name="taskId" value="${task.id}" />
					          			<div class="md-form form-sm">
					              			<label class="full-width text-center">Select which Users to delegate</label>
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
														          <input type="checkbox" class="checkbox" name="assignments" value="${user.name}">
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
			    	<!--/.Panel 1-->
			    	
			    	<!--Panel 2-->
	            	<div class="tab-pane fade" id="panel8" role="tabpanel">
			            <form action="TeamAssignment" method="post">
				            <div class="modal-body">
			       				<c:choose>
									<c:when test="${requestScope.assignableTeams.size() == 0}">
					          			<div class="md-form form-sm">
					              			<label class="full-width text-center">There are currently no assignable Teams</label>
					              			<br/><br/>
					               		</div>
					      			</c:when>
									<c:otherwise>
										<input type="hidden" name="objectiveId" value="${objective.id}" />
						            	<input type="hidden" name="projectId" value="${project.id}" />
						            	<input type="hidden" name="submoduleId" value="${submodule.id}" />
						            	<input type="hidden" name="taskId" value="${task.id}" />
					          			<div class="md-form form-sm">
					              			<label class="full-width text-center">Select which Teams to delegate</label>
					              			<br/><br/>
					               		</div>
						           		<div class="form-sm" id="objform35">
						           			<table>
				           						<colgroup>
													<col span="1" style="width: 15%;">
													<col span="1" style="width: 55%;">
													<col span="1" style="width: 30%;">
											    </colgroup>
											    <thead>
											        <tr>
												        <th></th>
												        <th class="text-center">Team Name</th>
												        <th class="text-center">Team Leader</th>
											        </tr>
											    </thead>
											    <tbody>
												<c:forEach items="${requestScope.assignableTeams}" var="team">
													<tr>
														<td class="text-center">
										                    <div class="checkbox-animated">
									                          <label class="label-checkbox larger-font">
														          <input type="checkbox" class="checkbox" name="assignments" value="${team.id}">
														      </label>
										                    </div>
									                    </td>
									                    <td class="text-center">${team.name}</td>
									                    <td class="text-center">${team.leader.name}</td>
									                </tr>
												</c:forEach>
												</tbody>
											</table>
						                </div>
									</c:otherwise>
								</c:choose>
				            </div>
				            <!--Footer-->
				            <div class="modal-footer">
								<c:choose>
									<c:when test="${requestScope.assignableTeams.size() == 0}">
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
			    	<!--/.Panel 2-->
            	</div>
            
            </div>
        </div>
        <!--/.Content-->
    </div>
</div>
<!--Modal: Assignment Creation Form-->
