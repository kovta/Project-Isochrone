<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${status == 'Proposed'}"><i class="fa fa-share-square-o"></i></c:when>
	<c:when test="${status == 'Pending'}"><i class="fa fa-ellipsis-h"></i></c:when>
	<c:when test="${status == 'Initiated'}"><i class="fa fa-arrow-circle-right"></i></c:when>
	<c:when test="${status == 'Under analysis'}"><i class="fa fa-pie-chart"></i></c:when>
	<c:when test="${status == 'In design'}"><i class="fa fa-crop"></i></c:when>
	<c:when test="${status == 'In development'}"><i class="fa fa-cogs"></i></c:when>
	<c:when test="${status == 'Canceled'}"><i class="fa fa-stop-circle-o"></i></c:when>
	<c:when test="${status == 'Testing'}"><i class="fa fa-flask"></i></c:when>
	<c:when test="${status == 'Validating'}"><i class="fa fa-bar-chart"></i></c:when>
	<c:when test="${status == 'Deploying'}"><i class="fa fa-arrow-circle-o-up"></i></c:when>
	<c:when test="${status == 'Implementing'}"><i class="fa fa-factory"></i></c:when>
	<c:when test="${status == 'Integrating'}"><i class="fa fa-cubes"></i></c:when>
	<c:when test="${status == 'Live'}"><i class="fa fa-feed"></i></c:when>
	<c:when test="${status == 'Maintained by operations'}"><i class="fa fa-dashboard"></i></c:when>
	<c:when test="${status == 'Upgrading'}"><i class="fa fa-angle-double-up"></i></c:when>
	<c:when test="${status == 'Disposed'}"><i class="fa fa-level-down"></i></c:when>
	<c:otherwise></c:otherwise>
</c:choose>