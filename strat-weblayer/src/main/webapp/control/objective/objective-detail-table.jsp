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
			<td class="strat-detail-attribute-name">Status</td>
			<td class="strat-detail-attribute-value">${objective.status.label}</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Priority</td>
			<td class="strat-detail-attribute-value">${objective.priority}</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Deadline</td>
			<td class="strat-detail-attribute-value">
			    <c:choose>
					<c:when test="${empty objective.deadline}">Not specified</c:when>
					<c:when test="${objective.urgencyLevel eq 3 and objective.completion ne 100}">
						<span class="danger-text">
							<fmt:formatDate type="date" value="${objective.deadline}" pattern="yyyy-MM-dd" />
							<i class="fa fa-exclamation-triangle"></i>
						</span>
					</c:when>
					<c:when test="${objective.urgencyLevel eq 2 and objective.completion ne 100}">
						<span class="heavy-warning-text">
							<fmt:formatDate type="date" value="${objective.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${objective.urgencyLevel eq 1 and objective.completion ne 100}">
						<span class="warning-text">
							<fmt:formatDate type="date" value="${objective.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${objective.completion eq 100}">
						<span class="success-text">
							<fmt:formatDate type="date" value="${objective.deadline}" pattern="yyyy-MM-dd" />
							<i class="fa fa-check"></i>
						</span>
					</c:when>
					<c:otherwise>
						<span class="success-text">
							<fmt:formatDate type="date" value="${objective.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Confidentiality</td>
			<td class="strat-detail-attribute-value">
				<c:choose>
					<c:when test="${requestScope.objective.confidential}"><span class="font-no-content">Private</span></c:when>
					<c:otherwise>Public</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Completion</td>
			<td class="strat-detail-attribute-value">${objective.completion} %</td>
		</tr>
				<c:if test="${objective.durationSum ne 0}">
			<tr class="text-center">
				<td colspan="2">
					<hr class="detail-table-top-header"/><strong><span>Progression Evaluation</span></strong><hr class="detail-table-bottom-header"/>
				</td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name">Progression</td>
				<td class="strat-detail-attribute-value">
					<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${(objective.completedDurationSum / objective.durationSum) * 100}" />
					<c:out value=" %" />
				</td>
			</tr>
			<c:if test="${objective.completedDurationSum eq objective.durationSum}">
				<tr>
					<td class="strat-detail-attribute-name"></td>
					<td class="strat-detail-attribute-value">
						<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${objective.durationSum}" />
						<c:out value="${objective.completedDurationSum eq 1 ? ' day' : ' days'}" />
						<c:out value=" in total" />
					</td>
				</tr>			
			</c:if>
			<c:if test="${objective.completedDurationSum ne objective.durationSum}">
				<tr>
					<td class="strat-detail-attribute-name"></td>
					<td class="strat-detail-attribute-value">
						<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${objective.completedDurationSum}" />
						<c:out value="${objective.completedDurationSum eq 1 ? ' day' : ' days'}" />
						<c:out value=" finished" />
					</td>
				</tr>
				<tr>
					<td class="strat-detail-attribute-name"></td>
					<td class="strat-detail-attribute-value">
						<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${objective.durationSum}" />
						<c:out value="${objective.completedDurationSum eq 1 ? ' day' : ' days'}" />
						<c:out value=" in total" />
					</td>
				</tr>
				<tr>
					<td class="strat-detail-attribute-name">Expected Completion Date</td>
					<td class="strat-detail-attribute-value"><fmt:formatDate type="date" value="${objective.expectedCompletionDate}" pattern="yyyy-MM-dd" /></td>
				</tr>
				<c:if test="${objective.expectedCompletionDate ne objective.estimatedCompletionDate}">
					<tr>
						<td class="strat-detail-attribute-name">Estimated Completion Date</td>
						<td class="strat-detail-attribute-value"><fmt:formatDate type="date" value="${objective.estimatedCompletionDate}" pattern="yyyy-MM-dd" /></td>
					</tr>
				</c:if>
				<c:if test="${objective.isDeadlineProvided()}">
					<tr>
						<td class="strat-detail-attribute-name">Deviation from Deadline</td>
						<td class="strat-detail-attribute-value">
							<c:choose>
								<c:when test="${objective.targetDeviation eq 0}"><span class="success-text">Right on schedule</span></c:when>
								<c:when test="${objective.targetDeviation gt 0}">
									<span class="success-text">
										${objective.targetDeviation lt 0 ? -objective.targetDeviation : objective.targetDeviation}
										<c:out value="${objective.targetDeviation eq 1 ? ' day' : ' days'} ahead of schedule" />
									</span>
								</c:when>
								<c:otherwise>
									<span class="danger-text">
										${objective.targetDeviation lt 0 ? -objective.targetDeviation : objective.targetDeviation}
										<c:out value="${objective.targetDeviation eq 1 ? ' day' : ' days'} behind schedule" />
									</span>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="strat-detail-attribute-name">Early finish probability</td>
						<td class="strat-detail-attribute-value">
							<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "2" value = "${objective.earlyFinishEstimation * 100}" /> %
						</td>
					</tr>
				</c:if>
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
				<a href="User?id=<c:out value="${objective.creator.id}" />">${objective.creator.name}</a>
			</td>
		</tr>
		    <tr>
			<td class="strat-detail-attribute-name">Creation date</td>
			<td class="strat-detail-attribute-value">${objective.creationDate}</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Modified by</td>
			<td class="strat-detail-attribute-value">
				<a href="User?id=<c:out value="${objective.modifier.id}" />">${objective.modifier.name}</a>
			</td>
		</tr>
		    <tr>
			<td class="strat-detail-attribute-name">Modification date</td>
			<td class="strat-detail-attribute-value">${objective.modificationDate}</td>
		</tr>
		<c:choose>
			<c:when test="${empty objective.description}">
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
				<tr><td colspan="2" class="strat-detail-description"><p class="text-center">${objective.description}</p></td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>