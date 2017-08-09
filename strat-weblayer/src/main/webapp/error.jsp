<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Page unavailable - Stratagem</title>
	<jsp:include page="header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/partial/navbar-fill.jsp"></jsp:include>
	<div class="container">
		<br/><br/><br/><br/><br/><br/><br/>
		<div class="divider-new">
            <h1 class="h1-responsive wow fadeIn" data-wow-delay="0.1s">The requested resource is not available</h1>
        </div>
        <h2 class="vertical-align-middle text-center full-width">Please use the following link to navigate back to the starting page</h2>
        <br/><br/>
        <a href="Home" class="absolutely-positioned vertical-align-middle text-center full-width">
    		<i class="fa fa-home" aria-hidden="true"></i> Home
	    </a>
		<br/><br/><br/><br/><br/><br/><br/><br/>
	    <!-- Modals -->
		<jsp:include page="/modal/login.jsp"></jsp:include>
		<jsp:include page="/modal/register.jsp"></jsp:include>
		<jsp:include page="/partial/access-alert.jsp"></jsp:include>
		<!-- /Modals -->
    </div>
	
	<jsp:include page="/partial/footer.jsp"></jsp:include>
	<jsp:include page="/partial/wow.jsp"></jsp:include>
	
</body>
</html>