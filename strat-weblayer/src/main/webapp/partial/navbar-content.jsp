<%@page import="com.kota.stratagem.weblayer.common.appuser.AccountAttribute"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.kota.stratagem.weblayer.common.appuser.AppUserAttribute" %>

<div class="container">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNav1" aria-controls="navbarNav1" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="Home"><strong>Home</strong></a>
    <div class="collapse navbar-collapse" id="navbarNav1">
    
    <% if (request == null || request.getUserPrincipal() == null || request.getUserPrincipal().getName() == null) { %>

		<ul class="navbar-nav mr-auto">
		    <li class="nav-item"><a class="nav-link" href="#about">About</a></li>
		    <li class="nav-item"><a class="nav-link" href="#best-features">Features</a></li>
		    <li class="nav-item"><a class="nav-link" href="#contact">Contact</a></li>
		</ul>
		<!-- Search bar -->
		<!-- <form class="form-inline waves-effect waves-light"><input class="form-control" type="text" placeholder="Search"></form> -->
		<!-- /Search bar -->
	    <ul class="navbar-nav float-right">
			<li class="nav-item">
	       		<a class="nav-link" data-toggle="modal" data-target="#modalRegister">
	       			<i class="fa fa-user-plus" aria-hidden="true"></i><span class="icon-companion"> Register</span>
	      		</a>
	       	</li>
			<li class="nav-item">				
				<a class="nav-link" data-toggle="modal" data-target="#modalLogin">
					<i class="fa fa-sign-in" aria-hidden="true"></i><span class="icon-companion"> Sign in</span>
				</a>
			</li>
	    </ul>    

    <% } else { %>
    
        <ul class="navbar-nav mr-auto">
            <li class="nav-item"><a class="nav-link" href="#about">About</a></li>
            <li class="nav-item"><a class="nav-link" href="#best-features">Features</a></li>
            <li class="nav-item"><a class="nav-link" href="#contact">Contact</a></li>
            <li class="nav-separator"></li>
			<li class="nav-item"><a class="nav-link" href="ObjectiveList">Objectives</a></li>
            <li class="nav-item"><a class="nav-link" href="ProjectList">Projects</a></li>
            <li class="nav-separator"></li>
            <li class="nav-item"><a class="nav-link" href="TeamList">Teams</a></li>
            <li class="nav-item"><a class="nav-link" href="UserList">Users</a></li>
        </ul>
        <!-- Search bar -->
		<!-- <form class="form-inline waves-effect waves-light"><input class="form-control" type="text" placeholder="Search"></form> -->
		<!-- /Search bar -->
		<ul class="navbar-nav float-right">
			<li class="nav-item">
				<% int notificationCount = (Integer) request.getSession().getAttribute(AccountAttribute.ATTR_NOTIFICATION_COUNT); %>
				<% int imageSelector = (Integer) request.getSession().getAttribute(AccountAttribute.ATTR_IMAGE_SELECTOR); %>
				<% if (notificationCount > 0) { %>
					<a class="nav-link flex-display" href="User?name=<%= request.getUserPrincipal().getName() %>">
						<div class="chip">
							<c:set var="selector" value="${imageSelector}" scope="request"/>
							<jsp:include page="/control/user/user-avatar-selector.jsp"></jsp:include>
							<i class="fa fa-bell"></i>
							<span><%= notificationCount %></span>
						</div>
						<strong><%= request.getUserPrincipal().getName() %></strong>
					</a>
				<% } else { %>
					<a class="nav-link" href="User?name=<%= request.getUserPrincipal().getName() %>">
						<div class="line-avatar">
							<c:set var="selector" value="${imageSelector}" scope="request"/>
							<jsp:include page="/control/user/user-avatar-selector.jsp"></jsp:include>
							<strong><%= request.getUserPrincipal().getName() %></strong>
						</div>
					</a>					
				<% } %>
        	</li>
			<li class="nav-item">
				<a class="nav-link" data-toggle="modal" data-target="#modalLogout">
					<i class="fa fa-sign-out" aria-hidden="true"></i><span class="icon-companion"> Sign out</span>
				</a>
			</li>
        </ul>
    
    <% } %>
    
    </div>
</div>