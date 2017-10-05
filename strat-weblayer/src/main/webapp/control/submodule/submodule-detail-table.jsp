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
			<td class="strat-detail-attribute-name">Parent Project</td>
			<td class="strat-detail-attribute-value">
				<a href="Project?id=<c:out value="${submodule.project.id}" />">${submodule.project.name}</a>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Deadline</td>
			<td class="strat-detail-attribute-value">
				<c:choose>
					<c:when test="${empty submodule.deadline}"><span class="font-no-content">Not Specified</span></c:when>
  					<c:when test="${submodule.urgencyLevel eq 3 and not submodule.isCompleted()}">
						<span class="danger-text">
							<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
							<i class="fa fa-exclamation-triangle"></i>
						</span>
					</c:when>
					<c:when test="${submodule.urgencyLevel eq 2 and not submodule.isCompleted()}">
						<span class="heavy-warning-text">
							<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${submodule.urgencyLevel eq 1 and not submodule.isCompleted()}">
						<span class="warning-text">
							<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${submodule.isCompleted()}">
						<span class="success-text">
							<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
							<i class="fa fa-check"></i>
						</span>
					</c:when>
					<c:otherwise>
						<span class="success-text">
							<fmt:formatDate type="date" value="${submodule.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Completion</td>
			<td class="strat-detail-attribute-value">${submodule.completion} %</td>
		</tr>
		<c:if test="${submodule.durationSum ne 0}">
			<tr class="text-center">
				<td colspan="2">
					<hr class="detail-table-top-header"/><strong><span>Progression Evaluation</span></strong><hr class="detail-table-bottom-header"/>
				</td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name">Progression</td>
				<td class="strat-detail-attribute-value">
					<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${(submodule.completedDurationSum / submodule.durationSum) * 100}" />
					<c:out value=" %" />
				</td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name"></td>
				<td class="strat-detail-attribute-value">
					<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${submodule.completedDurationSum}" />
					<c:out value="${submodule.completedDurationSum eq 1 ? ' day' : ' days'}" />
					<c:out value=" finished" />
				</td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name"></td>
				<td class="strat-detail-attribute-value">
					<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "1" value = "${submodule.durationSum}" />
					<c:out value="${submodule.completedDurationSum eq 1 ? ' day' : ' days'}" />
					<c:out value=" in total" />
				</td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name">Expected Completion Date</td>
				<td class="strat-detail-attribute-value"><fmt:formatDate type="date" value="${submodule.expectedCompletionDate}" pattern="yyyy-MM-dd" /></td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name">Estimated Completion Date</td>
				<td class="strat-detail-attribute-value"><fmt:formatDate type="date" value="${submodule.estimatedCompletionDate}" pattern="yyyy-MM-dd" /></td>
			</tr>
			<c:if test="${submodule.isDeadlineProvided()}">
				<tr>
					<td class="strat-detail-attribute-name">Deviation from Deadline</td>
					<td class="strat-detail-attribute-value">
						<c:choose>
							<c:when test="${submodule.targetDeviation eq 0}"><span class="success-text">Right on schedule</span></c:when>
							<c:when test="${submodule.targetDeviation gt 0}">
								<span class="success-text">
									${submodule.targetDeviation lt 0 ? -submodule.targetDeviation : submodule.targetDeviation}
									<c:out value="${submodule.targetDeviation eq 1 ? ' day' : ' days'} ahead of schedule" />
								</span>
							</c:when>
							<c:otherwise>
								<span class="danger-text">
									${submodule.targetDeviation lt 0 ? -submodule.targetDeviation : submodule.targetDeviation}
									<c:out value="${submodule.targetDeviation eq 1 ? ' day' : ' days'} behind schedule" />
								</span>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="strat-detail-attribute-name">Early finish probability</td>
					<td class="strat-detail-attribute-value">
						<fmt:formatNumber type = "number" maxIntegerDigits = "3" maxFractionDigits = "2" value = "${submodule.earlyFinishEstimation * 100}" /> %
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
				<a href="User?id=<c:out value="${submodule.creator.id}" />">${submodule.creator.name}</a>
			</td>
		</tr>
	    <tr>
			<td class="strat-detail-attribute-name">Creation date</td>
			<td class="strat-detail-attribute-value">${submodule.creationDate}</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Modified by</td>
			<td class="strat-detail-attribute-value">
				<a href="User?id=<c:out value="${submodule.modifier.id}" />">${submodule.modifier.name}</a>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Modification date</td>
			<td class="strat-detail-attribute-value">${submodule.modificationDate}</td>
		</tr>
		<c:choose>
			<c:when test="${empty submodule.description}">
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
				  <tr><td colspan="2" class="strat-detail-description"><p class="text-center">${submodule.description}</p></td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
