<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor" %>
<%@ page import="com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor" %>
<%@ page import="com.kota.stratagem.weblayer.common.project.ProjectAttribute" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% ProjectRepresentor project = (ProjectRepresentor) request.getAttribute(ProjectAttribute.ATTR_PROJECT); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="style/page.css" />
<title>:: Project ::</title>
</head>
<body>
	<div class="frame">
		<form method="post" action="ProjectAction">
			<c:if test="${requestScope.project.getId() != -1}"><input type="hidden" name="id" value="${project.id}" /></c:if>
			<div>
				<label>Name: </label>
				<input class="inputheader" type="text" name="name" value="${project.name}" />
				<br/><br/>
				<label>Description: </label>
				<input class="inputheader" type="text" name="description" value="${project.description}" />
				<br/><br/>
			</div>
			<div>
				<label for="status">Status: </label>
				<select name="status" id="status">
					<% for ( ProjectStatusRepresentor status : ProjectStatusRepresentor.values()) { %>
						<option value="<% out.print(status.name()); %>" <% out.print( status == project.getStatus() ? "selected=\"selected\"" : "" ); %> ><% out.print(status.getLabel()); %></option>
					<% } %>
				</select>
			</div>
			<div>
				<label for="Visibility">Visible: </label>
				<input type="checkbox" name="visibility" id="visibility" value="<% out.print(project.getConfidential()); %>" />
			</div>
			<br/><br/>
			<div>
				<input type="submit" value="Save" />&nbsp;
				<a href="ProjectList">cancel</a>
			</div>
		</form>
	</div>
</body>
</html>