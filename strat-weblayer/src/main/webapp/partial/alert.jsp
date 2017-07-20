<%@page session="true"%>
<%@page import="com.kota.stratagem.weblayer.common.LoginAttribute"%>
<%@page import="com.kota.stratagem.weblayer.common.RegistrationAttribute"%>
<%@page import="com.kota.stratagem.weblayer.common.objective.ObjectiveAttribute"%>
<%@page import="com.kota.stratagem.weblayer.common.project.ProjectAttribute"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Login -->
<% if (request.getAttribute(LoginAttribute.ATTR_ERROR) != null) { %>
	<% String errorMessage = (String) request.getAttribute(LoginAttribute.ATTR_ERROR); %>
	<div id="alertErrorMessage">
       <div id="inner-message" class="alert alert-error">
          <button type="button" name="dismissError" class="close" data-dismiss="alert">&times;</button>
          <div class="error"><%= errorMessage %></div>
        </div>
	</div>
<% } %>

<!-- Registration -->
<% if (request.getAttribute(RegistrationAttribute.ATTR_REG_ERROR) != null) { %>
	<% String errorMessage = (String) request.getAttribute(RegistrationAttribute.ATTR_REG_ERROR); %>
	<div id="alertErrorMessage">
	    <div id="inner-message" class="alert alert-error">
	       <button type="button" name="dismissError" class="close" data-dismiss="alert">&times;</button>
	       <div class="error"><%= errorMessage %></div>
	    </div>
	</div>
<% } %>
<% if (request.getAttribute(RegistrationAttribute.ATTR_REG_SUCCESS) != null) { %>
	<% String successMessage = (String) request.getAttribute(RegistrationAttribute.ATTR_REG_SUCCESS); %>	
	<div id="alertSuccessMessage">
	    <div id="inner-message" class="alert alert">
	       <button type="button" name="dismissSuccess" class="close" data-dismiss="alert">&times;</button>
	       <div class="error"><%= successMessage %></div>
	    </div>
	</div>
<% } %>

<!-- Objective -->
<% if (request.getSession().getAttribute(ObjectiveAttribute.ATTR_ERROR) != null) { %>
	<% String errorMessage = (String) request.getSession().getAttribute(ObjectiveAttribute.ATTR_ERROR); %>
	<div id="alertErrorMessage">
	    <div id="inner-message" class="alert alert-error">
	       <button type="button" name="dismissError" class="close" data-dismiss="alert">&times;</button>
	       <div class="error"><%= errorMessage %></div>
	       <% request.getSession().removeAttribute(ObjectiveAttribute.ATTR_ERROR); %>
	    </div>
	</div>
<% } %>
<% if (request.getSession().getAttribute(ObjectiveAttribute.ATTR_SUCCESS) != null) { %>
	<% String successMessage = (String) request.getSession().getAttribute(ObjectiveAttribute.ATTR_SUCCESS); %>
	<div id="alertSuccessMessage">
	    <div id="inner-message" class="alert alert">
	       <button type="button" name="dismissSuccess" class="close" data-dismiss="alert">&times;</button>
	       <div class="error"><%= successMessage %></div>
	       <% request.getSession().removeAttribute(ObjectiveAttribute.ATTR_SUCCESS); %>
	    </div>
	</div>
<% } %>

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