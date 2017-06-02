<%@page import="com.kota.stratagem.weblayer.common.RegistrationAttribute"%>
<%@page import="com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% AppUserRepresentor user = (AppUserRepresentor) request.getAttribute(RegistrationAttribute.ATTR_REG_USER); %>

<!--Modal: Register Form-->
<div class="modal fade" id="modalRegister" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">

            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
                <h4 class="title"><i class="fa fa-user-plus"></i> Register</h4>
            </div>
            <!--Body-->
            <div class="modal-body">
            	<form action="Register" method="post">
	                <div class="md-form form-sm">
	                    <i class="fa fa-user prefix"></i>
	                    <input type="text" id="form32" class="form-control" name="username" placeholder="Username" value="${user.name}">
	                </div>
            	
	                <div class="md-form form-sm">
	                    <i class="fa fa-envelope prefix"></i>
	                    <input type="text" id="form32" class="form-control" name="email" placeholder="Email address" value="${user.email}">
	                </div>
	
	                <div class="md-form form-sm">
	                    <i class="fa fa-lock prefix"></i>
	                    <input type="password" id="form33" class="form-control" name="password" placeholder="Password" value="">
	                </div>
	
	                <div class="md-form form-sm">
	                    <i class="fa fa-lock prefix"></i>
	                    <input type="password" id="form34" class="form-control" name="password_confirmation" placeholder="Repeat password" value="">
	                </div>
	
	                <div class="text-center mt-2">
	                    <button type="submit" name="submit" class="btn mdb-color darken-1">Register <i class="fa fa-sign-in ml-1"></i></button>
	                </div>
				</form>
            </div>
            <!--Footer-->
            <div class="modal-footer">
                <div class="options text-center text-md-right mt-1">
                    <p>Already have an account? <a data-dismiss="modal" data-toggle="modal" data-target="#modalLogin">Log In</a></p>
                </div>
                <button type="button" class="btn btn-outline-info waves-effect ml-auto" data-dismiss="modal">Close <i class="fa fa-times-circle ml-1"></i></button>
            </div>
        </div>
        <!--/.Content-->
    </div>
</div>
<!--Modal: Register Form-->