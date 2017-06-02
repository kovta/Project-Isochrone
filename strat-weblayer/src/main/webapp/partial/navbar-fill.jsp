<%@ page import="com.kota.stratagem.weblayer.common.Page" %>


<nav class="navbar navbar-full navbar-toggleable-md navbar-dark fixed-top scrolling-navbar">
    <div class="container">
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNav1" aria-controls="navbarNav1" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <!--
        <a class="navbar-brand" href="#">
            <strong>Navbar</strong>
        </a>
        -->
        
        <a class="navbar-brand" href="Home">
        	<strong>Home</strong>
        </a>
        <div class="collapse navbar-collapse" id="navbarNav1">
        
        <% if (request == null || request.getUserPrincipal() == null || request.getUserPrincipal().getName() == null) { %>
        
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#about">About</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#best-features">Features</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#contact">Contact</a>
                </li>
            </ul>
            <!-- Search bar -->
            <!-- 
            <form class="form-inline waves-effect waves-light">
                <input class="form-control" type="text" placeholder="Search">
            </form>
             -->
            <!-- /Search bar -->

            <ul class="navbar-nav float-right">
            	<li class="nav-item">
            		<a class="nav-link" data-toggle="modal" data-target="#modalRegister">
            			<i class="fa fa-user-plus" aria-hidden="true"></i><span class="icon-companion"> Register</span>
           			</a>
            	</li>
				<li class="nav-item">
					<a class="nav-link" data-toggle="modal" data-target="#modalLogin">
						<i class="fa fa-sign-in" aria-hidden="true"></i><span class="icon-companion"> Log in</span>
					</a>
				</li>
            </ul>
        
        <% } else { %>
        
             <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#about">About</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#best-features">Features</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#contact">Contact</a>
                </li>
				<li class="nav-item">
                    <a class="nav-link" href="ObjectiveList">Objectives</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ProjectList">Projects</a>
                </li>
            </ul>
            <!-- Search bar -->
            <!-- 
            <form class="form-inline waves-effect waves-light">
                <input class="form-control" type="text" placeholder="Search">
            </form>
             -->
            <!-- /Search bar -->
        
            <ul class="navbar-nav float-right">
            	<li class="nav-item">
           			<span class="nav-link"> Welcome <strong><%= request.getUserPrincipal().getName() %></strong>!</span>
            	</li>
				<li class="nav-item">
					<a class="nav-link" href="Logout">
						<i class="fa fa-sign-out" aria-hidden="true"></i><span class="icon-companion"> Logout</span>
					</a>
				</li>
            </ul>
        
        <% } %>
        
        </div>
    </div>
</nav>
 


