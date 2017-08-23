<%@ page import="com.kota.stratagem.weblayer.common.access.LoginAttribute" %>
           
<% 
	String userName = (String) request.getAttribute(LoginAttribute.ATTR_USERNAME); 
	String errorMessage = (String) request.getAttribute(LoginAttribute.ATTR_ERROR); 
%>

<!--Modal: Login Form-->
<div class="modal fade" id="modalLogin" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">

            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">               
                <h4 class="title"><i class="fa fa-user"></i> Sign in</h4>
            </div>
            
            <!--Body-->
            <div class="modal-body">
		        <form action="j_security_check" method="post">
		            <div class="md-form form-sm">
		                <i class="fa fa-user prefix"></i>
		                <input type="text" name="j_username" id="username" class="form-control" 
		                	placeholder="Username" value="<%= userName != null ? userName : "" %>" required>
		            </div>
		
		            <div class="md-form form-sm">
		                <i class="fa fa-lock prefix"></i>
		                <input type="password" name="j_password" id="password" class="form-control validate" placeholder="Password" required>
		            </div>
		
		            <div class="text-center mt-2">
		                <button type="submit" name="submit" class="btn mdb-color darken-1">Log in <i class="fa fa-sign-in ml-1"></i></button>
		            </div>
                </form>
            </div>
            <!--Footer-->
            <div class="modal-footer">
                <div class="options text-center text-md-right mt-1">
                    <p>Not a member? <a data-dismiss="modal" data-toggle="modal" data-target="#modalRegister">Sign Up</a></p>
                </div>
                <button type="button" class="btn btn-outline-info waves-effect ml-auto" data-dismiss="modal">
                	Close <i class="fa fa-times-circle ml-1"></i></button>
            </div>
        </div>
        <!--/.Content-->
    </div>
</div>
<!--Modal: Login Form-->