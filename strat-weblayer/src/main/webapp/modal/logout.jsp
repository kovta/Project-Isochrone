<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!--Modal: Logout Form-->
<div class="modal fade" id="modalLogout" tabindex="-1" role="dialog" aria-labelledby="Logout" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <!--Content-->
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header mdb-color darken-1 white-text">
                <h4 class="title"><i class="fa fa-sign-out"></i>Sign out</h4>
            </div>
            <!--Body-->
            <div class="modal-body">
            	<input type="hidden" name="id" value="${objective.id}" />
      			<div class="md-form form-sm full-width text-center">
           			<h3>Are you sure you want to sign out of your account?</h3>
           		</div>
            </div>
            <!--Footer-->
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-info mr-auto" data-dismiss="modal">
                	<span>Cancel</span> <i class="fa fa-times-circle ml-1"></i>
                </button>
                <a href="Logout" class="btn mdb-color darken-1 white-text">
                	<span>Log out</span> <i class="fa fa-sign-out ml-1"></i>
                </a>
            </div>
        </div>
        <!--/.Content-->
    </div>
</div>
<!--Modal: Objective Delete Form-->