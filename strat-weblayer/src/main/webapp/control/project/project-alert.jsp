<%@ page session="true" %>
<%@ page import="com.kota.stratagem.weblayer.common.project.ProjectAttribute" %>

<!-- Project -->
<% if (request.getSession().getAttribute(ProjectAttribute.ATTR_ERROR) != null) { %>
	<% String errorMessage = (String) request.getSession().getAttribute(ProjectAttribute.ATTR_ERROR); %>
	<div id="alertErrorMessage">
	    <div id="inner-message" class="alert alert-error">
	       <button type="button" name="dismissError" class="close" data-dismiss="alert">&times;</button>
	       <div class="error"><%= errorMessage %></div>
	       <% request.getSession().removeAttribute(ProjectAttribute.ATTR_ERROR); %>
	    </div>
	</div>
<% } %>
<% if (request.getSession().getAttribute(ProjectAttribute.ATTR_SUCCESS) != null) { %>
	<% String successMessage = (String) request.getSession().getAttribute(ProjectAttribute.ATTR_SUCCESS); %>
	<div id="alertSuccessMessage">
	    <div id="inner-message" class="alert alert">
	       <button type="button" name="dismissSuccess" class="close" data-dismiss="alert">&times;</button>
	       <div class="error"><%= successMessage %></div>
	       <% request.getSession().removeAttribute(ProjectAttribute.ATTR_SUCCESS); %>
	    </div>
	</div>
<% } %>