<!DOCTYPE html>
<html lang="en">
<head>
	<title>Stratagem</title>
	<jsp:include page="header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/partial/navbar-transparent.jsp"></jsp:include>
	
	<!--Mask-->
    <div class="view hm-black-strong">
        <div class="full-bg-img flex-center">
            <ul>
                <li><h1 class="brand-name font-bold wow fadeInDown" data-wow-delay="0.2s">Stratagem</h1></li>
				<li><hr class="hr-light"></li>
                <li>
                    <p class="wow fadeInDown" data-wow-delay="0.22s">The revisioned project workflow management system</p>
                    <p class="wow fadeInDown" data-wow-delay="0.22s">for startups, and the enterprise</p>
                </li>
                <!--
                <li>
                    <a target="_blank" href="http://mdbootstrap.com/getting-started/" class="btn btn-primary btn-lg wow fadeInLeft" data-wow-delay="0.2s" rel="nofollow">Sign up!</a>
                    <a target="_blank" href="http://mdbootstrap.com/material-design-for-bootstrap/" class="btn btn-default btn-lg wow fadeInRight" data-wow-delay="0.2s" rel="nofollow">Learn more</a>
                </li>
                -->
            </ul>
        </div>
    </div>
	<!--/.Mask-->
	
	<div class="container">
	
        <div class="divider-new"><h2 class="h2-responsive wow fadeIn" data-wow-delay="0.2s">About us</h2></div>
        <!--Section: About-->
        <section id="about" class="text-center wow fadeIn" data-wow-delay="0.2s">
            <p>
	            During the lifecycle of any organization, the policy by which they choose to orchestrate their corporate
	            measures will ultimately determine the success of the company as a whole. 
	            Managing these operations becomes all the more challenging with uncertain deadlines, loosely coupled productive engagements,
	            and ever rising demands for absolute perfection. Without the correct tools to aid leaders in team focused strategic 
	            coordination, these goals would most certainly remain mere aspirations. By integrating Stratagem into key business processes
	            companies are now able to have a leading edge on reaching their designated objectives.
            </p>
            <p>
	            With complex workflows spanning across numerous project teams keeping track of progress throughout multiple tracking systems. 
	            Stratagem aims to unify the monitoring of current work status, the evaluation of progression in different areas of effect
	            and overarching alerting system to keep all affected parties up-to-date with their key elements of interest.
            </p>
        </section>
        <!--Section: About-->
        
        <div class="divider-new"><h2 class="h2-responsive wow fadeIn">Best features</h2></div>
        <!--Section: Best features-->
        <section id="best-features">
            <div class="row">
                <div class="col-lg-3">
                    <div class="card wow fadeIn">
                        <div class="view overlay hm-white-slight">
                            <img src="http://mdbootstrap.com/img/Photos/Horizontal/Work/4-col/img%20(25).jpg" class="img-fluid" alt="">
                            <a><div class="mask"></div></a>
                        </div>
                        <div class="card-block text-center">
                            <h4 class="card-title">Workflow optimization</h4><hr>
                            <p class="card-text">Create accurate models of your current state of work according to your incorporated methodologies, be it agile or traditional.</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="card wow fadeIn" data-wow-delay="0.2s">
                        <div class="view overlay hm-white-slight">
                            <img src="http://mdbootstrap.com/img/Photos/Horizontal/Work/4-col/img%20(14).jpg" class="img-fluid" alt="">
                            <a><div class="mask"></div></a>
                        </div>
                        <div class="card-block text-center">
                            <h4 class="card-title">Team management</h4><hr>
                            <p class="card-text">Delegate responsibility to qualified participants or to teams on any desired scope, breaking up the given mission into maintainable actions.</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="card wow fadeIn" data-wow-delay="0.4s">
                        <div class="view overlay hm-white-slight">
                            <img src="http://mdbootstrap.com/img/Photos/Horizontal/Work/4-col/img%20(21).jpg" class="img-fluid" alt="">
                            <a><div class="mask"></div></a>
                        </div>
                        <div class="card-block text-center">
                            <h4 class="card-title">Platform independence</h4><hr>
                            <p class="card-text">Stratagems' responsive web client allows users to easily collaborate using any work station, or smart device.</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="card wow fadeIn" data-wow-delay="0.6s">
                        <div class="view overlay hm-white-slight">
                            <img src="http://mdbootstrap.com/img/Photos/Horizontal/Work/4-col/img%20(37).jpg" class="img-fluid" alt="">
                            <a><div class="mask"></div></a>
                        </div>
                        <div class="card-block text-center">
                            <h4 class="card-title">24/7 Support</h4><hr>
                            <p class="card-text">General assistance, Feedback and change requests are handled on a daily basis. Feel free to contact us concerning project modeling, or any form of technical help.</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!--/Section: Best features-->
        
        <div class="divider-new"><h2 class="h2-responsive wow fadeIn">Contact us</h2></div>
        <!--Section: Contact-->
        <section id="contact">
            <div class="row">
                <div class="col-md-8">
                    <div id="map-container" class="z-depth-1 wow fadeIn" style="height: 300px"></div>
                </div>
                <div class="col-md-4">
                    <ul class="text-center">
                        <li class="wow fadeIn" data-wow-delay="0.2s"><i class="fa fa-map-marker teal-text"></i><p>New York, NY 10012, USA</p></li>
                        <li class="wow fadeIn" data-wow-delay="0.3s"><i class="fa fa-phone teal-text"></i><p>+ 01 234 567 89</p></li>
                        <li class="wow fadeIn" data-wow-delay="0.4s"><i class="fa fa-envelope teal-text"></i><p>contact@smail.com</p></li>
                    </ul>
                </div>
            </div>
        </section>
        <!--Section: Contact-->
        
        <!-- Modals -->
		<jsp:include page="/modal/login.jsp"></jsp:include>
		<jsp:include page="/modal/register.jsp"></jsp:include>
		<jsp:include page="/modal/logout.jsp"></jsp:include>
		<jsp:include page="/partial/access-alert.jsp"></jsp:include>
		<!-- /Modals -->
		
    </div>
	
	<jsp:include page="/partial/footer.jsp"></jsp:include>
	<jsp:include page="/partial/wow.jsp"></jsp:include>
	
</body>
</html>
