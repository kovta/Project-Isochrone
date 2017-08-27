<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--Modal: Dependency Form-->
<div class="modal fade" id="addDependencies" tabindex="-1" role="dialog" aria-labelledby="dependencyModal" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
                <h4 class="title"><i class="fa fa-share-alt"></i>Dependency Configurations</h4>
            </div>
            <!--Body-->
            <form action="TaskDependency" method="post">
	            <div class="modal-body">
       				<c:choose>
						<c:when test="${requestScope.configurableDependencies.size() == 0}">
		          			<div class="md-form form-sm">
		              			<label class="full-width text-center">There are currently no attachable dependencies</label>
		              			<br/>
		               		</div>
		      			</c:when>
						<c:otherwise>
			            	<input type="hidden" name="taskId" value="${task.id}" />
		          			<div class="md-form form-sm">
		              			<label class="full-width text-center">Select which Tasks to add as direct dependencies</label>
		              			<br/>
		               		</div>
			           		<div class="form-sm" id="objform35">
								<table>
									<colgroup>
										<col span="1" style="width: 15%;">
										<col span="1" style="width: 40%;">
										<col span="1" style="width: 10%;">
										<col span="1" style="width: 25%;">
										<col span="1" style="width: 10%;">
								    </colgroup>
								    <thead>
								        <tr class="dependency-table-header">
									        <th></th>
									        <th>Name</th>
									        <th class="text-center">Priority</th>
									        <th class="text-center">Deadline</th>
									        <th class="text-center">Creator</th>
								        </tr>
								    </thead>
									<tbody>
									<c:forEach items="${requestScope.configurableDependencies}" var="dependency">
										<tr class="small-padder"></tr>
										<tr>
											<td class="text-center">
							                    <div class="checkbox-animated">
						                          <label class="label-checkbox larger-font">
											          <input type="checkbox" class="checkbox" name="dependencies" value="${dependency.id}">
											      </label>
							                    </div>
						                    </td>
						                    <td>${dependency.name}</td>
						                    <td class="text-center">${dependency.priority}</td>
						                    <td class="text-center"><fmt:formatDate type="date" value="${dependency.deadline}" pattern="yyyy-MM-dd" /></td>
						                    <td class="text-center">${dependency.creator.name}</td>
						                </tr>
					                </c:forEach>
								</tbody></table>
								<br/>
			                </div>
						</c:otherwise>
					</c:choose>
	            </div>
	            <!--Footer-->
	            <div class="modal-footer">
					<c:choose>
						<c:when test="${requestScope.configurableDependencies.size() == 0}">
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
<!--Modal: Objective Form-->