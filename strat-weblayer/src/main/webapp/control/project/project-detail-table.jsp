<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="strat-detail-table">
	<tbody>
		<tr class="text-center">
			<td colspan="2">
				<hr class="detail-table-top-header"/><strong><span>Primary Information</span></strong><hr class="detail-table-bottom-header"/>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Parent Objective</td>
			<td class="strat-detail-attribute-value">
				<a href="Objective?id=<c:out value="${project.objective.id}" />">${project.objective.name}</a>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Status</td>
			<td class="strat-detail-attribute-value">${project.status.label}</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Deadline</td>
			<td class="strat-detail-attribute-value">
				<c:choose>
					<c:when test="${empty project.deadline}"><span class="font-no-content">Not specified</span></c:when>
					<c:when test="${project.urgencyLevel eq 3 and project.completion ne 100}">
						<span class="danger-text">
							<fmt:formatDate type="date" value="${project.deadline}" pattern="yyyy-MM-dd" />
							<i class="fa fa-exclamation-triangle"></i>
						</span>
					</c:when>
					<c:when test="${project.urgencyLevel eq 2 and project.completion ne 100}">
						<span class="heavy-warning-text">
							<fmt:formatDate type="date" value="${project.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${project.urgencyLevel eq 1 and project.completion ne 100}">
						<span class="warning-text">
							<fmt:formatDate type="date" value="${project.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${project.completion eq 100}">
						<span class="success-text">
							<fmt:formatDate type="date" value="${project.deadline}" pattern="yyyy-MM-dd" />
							<i class="fa fa-check"></i>
						</span>
					</c:when>
					<c:otherwise>
						<span class="success-text">
							<fmt:formatDate type="date" value="${project.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Confidentiality</td>
			<td class="strat-detail-attribute-value">
			    <c:choose>
					<c:when test="${requestScope.project.confidential}">Private</c:when>
					<c:otherwise>Public</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Completion</td>
			<td class="strat-detail-attribute-value">${project.completion} %</td>
		</tr>
		<c:if test="${project.durationSum ne 0}">
			<tr class="text-center">
				<td colspan="2">
					<hr class="detail-table-top-header"/><strong><span>Progression Evaluation</span></strong><hr class="detail-table-bottom-header"/>
				</td>
			</tr>
		<tr>
				<td class="strat-detail-attribute-name">Progression</td>
				<td class="strat-detail-attribute-value">
					<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${(project.completedDurationSum / project.durationSum) * 100}" />
					<c:out value=" %" />
				</td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name"></td>
				<td class="strat-detail-attribute-value">
					<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${project.completedDurationSum}" />
					<c:out value="${project.completedDurationSum eq 1 ? ' day' : ' days'}" />
					<c:out value=" finished" />
				</td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name"></td>
				<td class="strat-detail-attribute-value">
					<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${project.durationSum}" />
					<c:out value="${project.completedDurationSum eq 1 ? ' day' : ' days'}" />
					<c:out value=" in total" />
				</td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name">Expected Completion Date</td>
				<td class="strat-detail-attribute-value"><fmt:formatDate type="date" value="${project.expectedCompletionDate}" pattern="yyyy-MM-dd" /></td>
			</tr>
			<c:if test="${project.expectedCompletionDate ne project.estimatedCompletionDate}">
				<tr>
					<td class="strat-detail-attribute-name">Estimated Completion Date</td>
					<td class="strat-detail-attribute-value"><fmt:formatDate type="date" value="${project.estimatedCompletionDate}" pattern="yyyy-MM-dd" /></td>
				</tr>
			</c:if>
			<c:if test="${project.isDeadlineProvided()}">
				<tr>
					<td class="strat-detail-attribute-name">Deviation from Deadline</td>
					<td class="strat-detail-attribute-value">
						<c:choose>
							<c:when test="${project.targetDeviation eq 0}"><span class="success-text">Right on schedule</span></c:when>
							<c:when test="${project.targetDeviation gt 0}">
								<span class="success-text">
									${project.targetDeviation lt 0 ? -project.targetDeviation : project.targetDeviation}
									<c:out value="${project.targetDeviation eq 1 ? ' day' : ' days'} ahead of schedule" />
								</span>
							</c:when>
							<c:otherwise>
								<span class="danger-text">
									${project.targetDeviation lt 0 ? -project.targetDeviation : project.targetDeviation}
									<c:out value="${project.targetDeviation eq 1 ? ' day' : ' days'} behind schedule" />
								</span>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="strat-detail-attribute-name">Early finish probability</td>
					<td class="strat-detail-attribute-value">
						<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "2" value = "${project.earlyFinishEstimation * 100}" /> %
					</td>
				</tr>
			</c:if>
		</c:if>
		<tr class="text-center">
			<td colspan="2">
				<hr class="detail-table-top-header"/><strong><span>Technical properties</span></strong><hr class="detail-table-bottom-header"/>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Created by</td>
			<td class="strat-detail-attribute-value">
				<a href="User?id=<c:out value="${project.creator.id}" />">${project.creator.name}</a>
			</td>
		</tr>
		    <tr>
			<td class="strat-detail-attribute-name">Creation date</td>
			<td class="strat-detail-attribute-value">${project.creationDate}</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Modified by</td>
			<td class="strat-detail-attribute-value">
				<a href="User?id=<c:out value="${project.modifier.id}" />">${project.modifier.name}</a>
			</td>
		</tr>
		    <tr>
			<td class="strat-detail-attribute-name">Modification date</td>
			<td class="strat-detail-attribute-value">${project.modificationDate}</td>
		</tr>
		<c:choose>
			<c:when test="${empty project.description}">
				<tr><td colspan="2"><hr class="extra-margins"></td></tr>
				<tr>
					<td colspan="2" class="strat-detail-description">
						<p class="text-center"><span class="font-no-content">No Description</span></p>
					</td>
				</tr>
				</c:when>
			<c:otherwise>
				<tr><td colspan="2"><hr class="extra-margins"></td></tr>
				<tr><td colspan="2" class="strat-detail-description"><p class="text-center">Description</p></td></tr>
				<tr><td colspan="2" class="strat-detail-description"><p class="text-center">...</p></td></tr>
				<tr><td colspan="2" class="strat-detail-description"><p class="text-center">${project.description}</p></td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>