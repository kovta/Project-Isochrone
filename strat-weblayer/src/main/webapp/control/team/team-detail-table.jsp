<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="strat-detail-table">
	<tbody>
		<tr><td colspan="2"><hr class="extra-margins"></td></tr>
		<tr>
			<td class="strat-detail-attribute-name">Team Leader</td>
			<td class="strat-detail-attribute-value">
				<a href="User?id=<c:out value="${team.leader.id}" />">${team.leader.name}</a>
			</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Created by</td>
			<td class="strat-detail-attribute-value">
				<a href="User?id=<c:out value="${team.creator.id}" />">${team.creator.name}</a>
			</td>
		</tr>
		    <tr>
			<td class="strat-detail-attribute-name">Creation date</td>
			<td class="strat-detail-attribute-value">${team.creationDate}</td>
		</tr>
		<tr>
			<td class="strat-detail-attribute-name">Modified by</td>
			<td class="strat-detail-attribute-value">
				<a href="User?id=<c:out value="${team.modifier.id}" />">${team.modifier.name}</a>
			</td>
		</tr>
		    <tr>
			<td class="strat-detail-attribute-name">Modification date</td>
			<td class="strat-detail-attribute-value">${team.modificationDate}</td>
		</tr>
	</tbody>
</table>