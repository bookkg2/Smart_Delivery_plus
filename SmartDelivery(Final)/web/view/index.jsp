<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
  <head>
      <title>
    Smart Delivery+
  </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Amatic+SC:400,700&display=swap" rel="stylesheet">
  
    <link rel="stylesheet" href="/SmartDelivery/view/user/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="/SmartDelivery/view/user/css/animate.css">
   
    <link rel="stylesheet" href="/SmartDelivery/view/user/css/owl.carousel.min.css">
    <link rel="stylesheet" href="/SmartDelivery/view/user/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="/SmartDelivery/view/user/css/magnific-popup.css">

    <link rel="stylesheet" href="/SmartDelivery/view/user/css/aos.css">
 
    <link rel="stylesheet" href="/SmartDelivery/view/user/css/ionicons.min.css">

    <link rel="stylesheet" href="/SmartDelivery/view/user/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="/SmartDelivery/view/user/css/jquery.timepicker.css">
 
    <link rel="stylesheet" href="/SmartDelivery/view/user/css/flaticon.css">
    
    
    <link rel="stylesheet" href="/SmartDelivery/view/user/css/icomoon.css">
     
    <link rel="stylesheet" href="/SmartDelivery/view/user/css/style.css">  
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    
  <style>
  .join_form_div{
	margin: auto 0;
	padding: 100px;
   }
  </style>
  </head>
  <body class="goto-here">
	
    <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
	    <div class="container">
	      <a class="navbar-brand" href="index.jsp">SmartDelivery+</a>
	      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
	        <span class="oi oi-menu"></span> Menu
	      </button>

	      <div class="collapse navbar-collapse" id="ftco-nav">
	        <ul class="navbar-nav ml-auto">
	           <li class="nav-item active"><a href="customermain.del" class="nav-link">Home</a></li>
	            <li class="nav-item active"><a href="productlist.del" class="nav-link">Shop</a></li>
	          <c:choose>
				<%--  <c:when test="${loginuser != null }"> --%>
			 <c:when test="${loginuser.admin_check eq '1'.charAt(0) }">
			
	                 <li class="nav-item"><a href="logout.del" class="nav-link">Logout</a></li>
	                 <!-- 로그인한 후에만 들어갈 수 있게 해야 함  -->
	             <!--     <li class="nav-item"><a href="productorder.del" class="nav-link" >Order</a></li>	  -->	
	                 <li class="nav-item"><a href="customerabout.del" class="nav-link">About</a></li>
	         		 <li class="nav-item"><a href="customerblog.del" class="nav-link">Blog</a></li>
	       	         <li class="nav-item"><a href="customerdelcheck.del" class="nav-link">DeliveryCheck</a></li>
	       	         <li class="nav-item"><a href="dashboard.del" class="nav-link">Dashboard</a></li>
					  
				 </c:when>
				 
			<c:when test="${loginuser.admin_check eq null }">
					<li class="nav-item"><a href="customerjoin.del" class="nav-link">Join</a></li>
	              	<li class="nav-item"><a href="customerlogin.del" class="nav-link">Login</a></li>
	          		<li class="nav-item"><a href="customerabout.del" class="nav-link">About</a></li>
	         		<li class="nav-item"><a href="customerblog.del" class="nav-link">Blog</a></li>
			</c:when>
				 <c:otherwise>
					<li class="nav-item"><a href="logout.del" class="nav-link">Logout</a></li>
	                 <!-- 로그인한 후에만 들어갈 수 있게 해야 함  -->
	             <!--     <li class="nav-item"><a href="productorder.del" class="nav-link" >Order</a></li>	  -->	
	                 <li class="nav-item"><a href="customerabout.del" class="nav-link">About</a></li>
	         		 <li class="nav-item"><a href="customerblog.del" class="nav-link">Blog</a></li>
	       	         <li class="nav-item"><a href="customerdelcheck.del" class="nav-link">DeliveryCheck</a></li>
				 </c:otherwise>
			  </c:choose>
	        </ul> 
	      </div>
	    </div>
	  </nav>
    <!-- END nav -->

	
   

	<!-- Product -->
		<c:choose>
			<c:when test="${center == null }">
				<jsp:include page="center.jsp"></jsp:include>
			</c:when>
			<c:otherwise>
				<jsp:include page="${center }.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
	
	
  <!-- ============================================================================================================================= -->	
   
 <!--    
    <footer class="ftco-footer ftco-section">
      <div class="container">
      	<div class="row">
      		<div class="mouse">
						<a href="#" class="mouse-icon">
							<div class="mouse-wheel"><span class="ion-ios-arrow-up"></span></div>
						</a>
					</div>
      	</div>
       
        <div class="row">
          <div class="col-md-12 text-center">

            <p>Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0.
						  Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart color-danger" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
						  Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0.
						</p>
          </div>
        </div>
      </div>
    </footer> -->
    
  

  <!-- loader -->
  <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>



    
  </body>
  <script src="/SmartDelivery/view/user/js/jquery.min.js"></script>
  <script src="/SmartDelivery/view/user/js/jquery-migrate-3.0.1.min.js"></script>
  <script src="/SmartDelivery/view/user/js/popper.min.js"></script>
  <script src="/SmartDelivery/view/user/js/bootstrap.min.js"></script>
  <script src="/SmartDelivery/view/user/js/jquery.easing.1.3.js"></script>
  <script src="/SmartDelivery/view/user/js/jquery.waypoints.min.js"></script>
  <script src="/SmartDelivery/view/user/js/jquery.stellar.min.js"></script>
  <script src="/SmartDelivery/view/user/js/owl.carousel.min.js"></script>
  <script src="/SmartDelivery/view/user/js/jquery.magnific-popup.min.js"></script>
  <script src="/SmartDelivery/view/user/js/aos.js"></script>
  <script src="/SmartDelivery/view/user/js/jquery.animateNumber.min.js"></script>
  <script src="/SmartDelivery/view/user/js/bootstrap-datepicker.js"></script>
  <script src="/SmartDelivery/view/user/js/scrollax.min.js"></script>
  <script src="/SmartDelivery/view/user/js/main.js"></script>
</html>