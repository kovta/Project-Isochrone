<%@ page session="true" %>
<%@ page import="com.kota.stratagem.weblayer.common.team.TeamAttribute" %>

<!-- Team -->
<% if (request.getSession().getAttribute(TeamAttribute.ATTR_ERROR) != null) { %>
	<% String errorMessage = (String) request.getSession().getAttribute(TeamAttribute.ATTR_ERROR); %>
	<div id="alertErrorMessage">
	    <div id="inner-message" class="alert alert-error">
	       <button type="button" name="dismissError" class="close" data-dismiss="alert">&times;</button>
	       <div class="error"><%= errorMessage %></div>
	       <% request.getSession().removeAttribute(TeamAttribute.ATTR_ERROR); %>
	    </div>
	</div>
<% } %>
<% if (request.getSession().getAttribute(TeamAttribute.ATTR_SUCCESS) != null) { %>
	<% String successMessage = (String) request.getSession().getAttribute(TeamAttribute.ATTR_SUCCESS); %>
	<div id="alertSuccessMessage">
	    <div id="inner-message" class="alert alert">
	       <button type="button" name="dismissSuccess" class="close" data-dismiss="alert">&times;</button>
	       <div class="error"><%= successMessage %></div>
	       <% request.getSession().removeAttribute(TeamAttribute.ATTR_SUCCESS); %>
	    </div>
	</div>
<% } %>