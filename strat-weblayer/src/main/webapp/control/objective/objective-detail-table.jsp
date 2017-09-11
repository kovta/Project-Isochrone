<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="strat-detail-table">
	<tbody>
		<tr><td colspan="2"><hr class="extra-margins"></td></tr>
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
					<c:when test="${empty project.deadline}">Not specified</c:when>
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