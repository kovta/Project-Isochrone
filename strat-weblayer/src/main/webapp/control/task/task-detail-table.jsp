<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="strat-detail-table">
	<tbody>
    	<tr><td colspan="2"><hr class="extra-margins"></td></tr>
		<tr>
			<c:choose>
				<c:when test="${not empty task.objective}">
					<td class="strat-detail-attribute-name">Parent Objective</td>
					<td class="strat-detail-attribute-value">
						<a href="Objective?id=<c:out value="${task.objective.id}" />">${task.objective.name}</a>
					</td>
				</c:when>
				<c:when test="${not empty task.project}">	
					<td class="strat-detail-attribute-name">Parent Project</td>
					<td class="strat-detail-attribute-value">
						<a href="Project?id=<c:out value="${task.project.id}" />">${task.project.name}</a>
					</td>
				</c:when>
				<c:otherwise>
				<td class="strat-detail-attribute-name">Parent Submodule</td>
		    	<td class="strat-detail-attribute-value">
		    		<a href="Submodule?id=<c:out value="${task.submodule.id}" />">${task.submodule.name}</a>
				</td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
        	<td class="strat-detail-attribute-name">Priority</td>
        	<td class="strat-detail-attribute-value">${task.priority}</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Completion</td>
			<td class="strat-detail-attribute-value">${task.completion} %</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Deadline</td>
			<td class="strat-detail-attribute-value">
				<c:choose>
					<c:when test="${empty task.deadline}"><span class="font-no-content">Not specified</span></c:when>
					<c:when test="${task.urgencyLevel eq 3 and task.completion ne 100}">
						<span class="danger-text">
							<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
							<i class="fa fa-exclamation-triangle"></i>
						</span>
					</c:when>
					<c:when test="${task.urgencyLevel eq 2 and task.completion ne 100}">
						<span class="heavy-warning-text">
							<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${task.urgencyLevel eq 1 and task.completion ne 100}">
						<span class="warning-text">
							<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:when>
					<c:when test="${task.completion eq 100}">
						<span class="success-text">
							<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
							<i class="fa fa-check"></i>
						</span>
					</c:when>
					<c:otherwise>
						<span class="success-text">
							<fmt:formatDate type="date" value="${task.deadline}" pattern="yyyy-MM-dd" />
						</span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Duration</td>
			<td class="strat-detail-attribute-value">
				<c:choose>
					<c:when test="${not task.estimated and empty task.duration}"><span class="font-no-content">Not specified</span></c:when>
					<c:when test="${not empty task.duration and task.duration eq 1}">${task.duration} day</c:when>
					<c:when test="${not empty task.duration and task.duration ne 1}">${task.duration} days</c:when>
					<c:otherwise>Estimated</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<c:if test="${task.estimated}">
			<tr>
				<td class="strat-detail-attribute-name">Pessimistic</td>
				<td class="strat-detail-attribute-value">${task.pessimistic}<c:out value="${task.pessimistic eq 1 ? ' day' : ' days'}" /></td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name">Realistic</td>
				<td class="strat-detail-attribute-value">${task.realistic}<c:out value="${task.realistic eq 1 ? ' day' : ' days'}" /></td>
			</tr>
			<tr>
				<td class="strat-detail-attribute-name">Optimistic</td>
				<td class="strat-detail-attribute-value">${task.optimistic}<c:out value="${task.optimistic eq 1 ? ' day' : ' days'}" /></td>
			</tr>
		</c:if>
		<tr>
			<td class="strat-detail-attribute-name">Admittance status</td>
			<td class="strat-detail-attribute-value">
			    <c:choose>
					<c:when test="${requestScope.task.admittance}">Open</c:when>
					<c:otherwise>Closed</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Created by</td>
			<td class="strat-detail-attribute-value">
		    	<a href="User?id=<c:out value="${task.creator.id}" />">${task.creator.name}</a>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Creation date</td>
			<td class="strat-detail-attribute-value">${task.creationDate}</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Modified by</td>
			<td class="strat-detail-attribute-value">
				<a href="User?id=<c:out value="${task.modifier.id}" />">${task.modifier.name}</a>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Modification date</td>
			<td class="strat-detail-attribute-value">${task.modificationDate}</td>
		</tr>
		<c:choose>
			<c:when test="${empty task.description}">
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
				<tr><td colspan="2" class="strat-detail-description"><p class="text-center">${task.description}</p></td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
