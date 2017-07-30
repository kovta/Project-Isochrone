<%@ page session="true"%>
<%@ page import="com.kota.stratagem.weblayer.common.access.LoginAttribute"%>
<%@ page import="com.kota.stratagem.weblayer.common.access.RegistrationAttribute"%>
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







