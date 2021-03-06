<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	                      
<div class="tab-pane fade" id="dependencyPanel" role="tabpanel" aria-expanded="false">
	<c:choose>
		<c:when test="${submodule.submoduleDependencies.size() == 0 and submodule.dependantSubmodules.size() == 0}">
			<div class="row wow fadeIn" data-wow-delay="0.2s">
	        	<div class="col-lg-12">
	            	<div class="text-center content-padder">
	                	<h2 class="h2-responsive">There are currently no Dependency Configurations</h2>
	                    <h2 class="h2-responsive">for this Submodule</h2>
	                </div>
	            </div>
	        </div>
		</c:when>
		<c:otherwise>
			<c:set var="level" value="${requestScope.submodule.dependantChain.size()}" scope="page" />
			<c:forEach items="${requestScope.submodule.dependantChain}" var="dependantLevel">
	            <div class="row wow fadeIn" data-wow-delay="0.2s">
	    			<div class="col-lg-12">
	                	<div class="divider-new">
	                    	<h2 class="h4-responsive wow fadeIn">Dependant level: <c:out value="${level}" /></h2>
	                    </div>
	                </div>
	            </div>
				<div class="row">
					<c:forEach items="${dependantLevel}" var="dependant">
						<div class="col-lg-4">
							<c:if test="${submodule.completion == 100}"><br/></c:if>
							<div class="card wow fadeIn" data-wow-delay="0.2s">
								<div class="card-block">
		            				<c:set var="target" value="${requestScope.submodule}" scope="request" />
		                            <c:set var="submodule" value="${dependant}" scope="request" />
		                            <c:set var="dependencyDriven" value="true" scope="request" />
		                            <jsp:include page="submodule-card-content.jsp"></jsp:include>
		                            <c:set var="dependencyDriven" value="false" scope="request" />
		                            <c:set var="submodule" value="${target}" scope="request" />
		                            <c:if test="${requestScope.supervisor and level eq 1}">
		                            	<hr/>
										<div class="full-width text-center">
											<a href="SubmoduleDependencyDelete?dependency=<c:out value="${submodule.id}" />
												&dependant=<c:out value="${dependant.id}" />
												&submoduleId=<c:out value="${submodule.id}" />">Remove Dependency</a>
								    	</div>
						    		</c:if>
	                           </div>
	                       </div>
	                       <br/>
	                   </div>
                   </c:forEach>
               	</div>
				<c:set var="level" value="${level - 1}" scope="page" />
			</c:forEach>
			<br/><br/><hr/>
			<div class="col-lg-12">
				<div class="text-center"><h4><c:out value="${requestScope.submodule.name}" /></h4></div>
			</div>
			<hr/>
			<c:set var="levelIndicator" value="0" scope="page" />
			<c:forEach items="${requestScope.submodule.dependencyChain}" var="dependencyLevel">
				<c:set var="levelIndicator" value="${levelIndicator + 1}" scope="page" />
	            <div class="row wow fadeIn" data-wow-delay="0.2s">
					<div class="col-lg-12">
	                	<div class="divider-new">
	                    	<h2 class="h4-responsive wow fadeIn">Dependency level: <c:out value="${levelIndicator}" /></h2>
	                    </div>
	                </div>
	            </div>
				<div class="row">
					<c:forEach items="${dependencyLevel}" var="dependency">
						<div class="col-lg-4">
							<c:if test="${submodule.completion == 100}"><br/></c:if>
							<div class="card wow fadeIn" data-wow-delay="0.2s">
								<div class="card-block">
									<c:set var="target" value="${requestScope.submodule}" scope="request" />
		                            <c:set var="submodule" value="${dependency}" scope="request" />
		                            <c:set var="dependencyDriven" value="true" scope="request" />
		                            <jsp:include page="submodule-card-content.jsp"></jsp:include>
		                            <c:set var="dependencyDriven" value="false" scope="request" />
		                            <c:set var="submodule" value="${target}" scope="request" />
		                            <c:if test="${requestScope.supervisor and levelIndicator eq 1}">
		                            	<hr/>
										<div class="full-width text-center">
											<a href="SubmoduleDependencyDelete?dependency=<c:out value="${dependency.id}" />
												&dependant=<c:out value="${submodule.id}" />
												&submoduleId=<c:out value="${submodule.id}" />">Remove Dependency</a>
								    	</div>
						    		</c:if>
	                           	</div>
	                        </div>
	                        <br/>
	                   </div>
	               </c:forEach>
	           </div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</div>
	                     