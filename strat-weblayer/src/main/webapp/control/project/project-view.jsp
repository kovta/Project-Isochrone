<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="style/page.css" />
<title>:: Project ::</title>
</head>
<body>
    <jsp:useBean id="project" class="com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor" scope="request" />
    <h1><jsp:getProperty name="project" property="name" /></h1>
    <c:if test="${not empty requestScope.project.description}"><h4><jsp:getProperty name="project" property="description" /></h4></c:if>
    <div><label>Status: </label><span>${requestScope.project.status.label}</span></div>
    <div><label>Number of tasks: </label><span>${requestScope.project.tasks.size()}</span></div>
    <c:choose>
	    <c:when test="${requestScope.project.visible}">
            <span><strong>Public</strong> project</span>
        </c:when>
        <c:otherwise>
 			<span><strong>Private</strong> project</span>
        </c:otherwise>
	</c:choose>
    <br/><br/>
    <div>
        <a href="ProjectList">back</a>
        <% if (request.isUserInRole("magadmin")) { %>
            <!-- <a href="Magazine?reference=<%  %>&edit=1">edit</a>-->
        <% } %>
    </div>
</body>
</html>