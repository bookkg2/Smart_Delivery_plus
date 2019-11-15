<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
		
    <div class="hero-wrap hero-bread" style="background-image: url('/SmartDelivery/view/user/images/bg_1.jpg');">
      <div class="container">
        <div class="row no-gutters slider-text align-items-center justify-content-center">
          <div class="col-md-9 ftco-animate text-center">
          	<p class="breadcrumbs"><span class="mr-2"><a href="/SmartDelivery/index.jsp">Home</a></span> <span>Join us</span></p>
            <h1 class="mb-0 bread">Join</h1>
          </div>
        </div>
      </div>
    </div>

        <div class="join_form_div">
          <div>
            <form action="customerjoinimpl.del" method="POST" onsubmit="return joinCheck(this)" >
             <div class="form-group"> 
                <input type="text"  size="7" id="id" name="CUSTOMER_ID" class="form-control" placeholder="ID">
              
                <input type="button" class="btn btn-primary py-3 px-20" name="idceck" value="중복확인" />
               <br> <span class="idspan"></span>
               </div>
              <div class="form-group">
                <input type="password"  size="10"  name="CUSTOMER_PWD" class="form-control" placeholder="Password">
              </div>
             <div class="form-group"> 
                <input type="password"  size="10" name="CUSTOMER_PWD2" onchange="isSame()" class="form-control" placeholder="Password Check">
                <span class="same"></span>
             </div> 
             <div class="form-group"> 
                <input type="text" size="10"  id="name" name="CUSTOMER_NM" class="form-control" placeholder="Name">
            	<span class="namespan"></span>
            </div> 

             <div class="form-group">	
                <input type="text"  size="10"  id="gender"  name="CUSTOMER_GD" class="form-control" placeholder="Gender">
            	<span class="genderspan"></span>
             </div> 

              <div class="form-group"> 
                <input type="text"  size="10" id="age" name="CUSTOMER_AGE" class="form-control" placeholder="Age">
            	<span class="agespan"></span>
              </div> 
              
            <div class="form-group">   
                <input type="text"  size="10" id="phonenumber" name="CUSTOMER_PHONE" class="form-control" placeholder="Phone Number">
            	<span class="phonespan"></span>            
            </div> 
             
             <div class="form-group">
                <input type="text" size="10"  id="useraddr" name="CUSTOMER_ADDR" class="form-control" placeholder="Address">
             	<span class="addrspan"></span>
             </div>
             <div class="form-group">
                <input type="submit" value="JOIN" name="registerok" class="btn btn-primary py-3 px-5" >
              </div> 
            </form> 
             
             
          </div>
          </div>
     
        </div>
      </div>
    </section>

    
    
  

<script>
	function joinCheck(obj) {		
		var regex = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/;
		if (!obj.CUSTOMER_ID.value || obj.CUSTOMER_ID.value.trim().length == 0) {
			obj.CUSTOMER_ID.value = "";
			obj.CUSTOMER_ID.focus();
			$('.idspan').html('<span style="color:red">아이디가 입력되지 않았습니다.</span>');
			return false;
		}
		else{
			$('.idspan').html('<span style="color:red"></span>');
		} 
			
		//아이디 길이 체크
		if(obj.CUSTOMER_ID.value.length<4 || obj.CUSTOMER_ID.value.length>12){
           // obj.CUSTOMER_ID.focus();
            obj.CUSTOMER_ID.select();
            return false;
		
		}
		//아이디 유효성 검사 (영문소문자, 숫자만 허용)
        for (i = 0; i < obj.CUSTOMER_ID.value.length; i++) {
            ch = obj.CUSTOMER_ID.value.charAt(i);
            if (!(ch >= '0' && ch <= '9') && !(ch >= 'a' && ch <= 'z')&&!(ch >= 'A' && ch <= 'Z')) {
               // obj.CUSTOMER_ID.focus();
                obj.CUSTOMER_ID.select();
                return false;
            }
        }
		//아이디에 공백 사용하지 않기
        if (obj.CUSTOMER_ID.value.indexOf(" ") >= 0) {
            obj.CUSTOMER_ID.select();
            return false;
        }
		
		
		if (!obj.CUSTOMER_PWD.value
				|| obj.CUSTOMER_PWD.value.trim().length == 0) {
			obj.CUSTOMER_PWD.value = "";
			return false;
		}
		
		 if (obj.CUSTOMER_PWD.value == obj.CUSTOMER_ID.value) {
	           // obj.CUSTOMER_PWD.focus();
	            return false;
	      }
		 
		
		
		if (regex.test(obj.CUSTOMER_PWD.value) === false) {
	       //  obj.CUSTOMER_PWD.focus();
	         obj.CUSTOMER_PWD.select();
	         return false;
        }
		
		if (!obj.CUSTOMER_NM.value || obj.CUSTOMER_NM.value.trim().length == 0) {
			obj.CUSTOMER_NM.value = "";
			$('.namespan').html('<span style="color:red">이름이 입력되지 않았습니다.</span>');
			//obj.CUSTOMER_NM.focus();
			return false;
		}else{
			$('.namespan').html('<span style="color:red"></span>');
		}
		
		if(obj.CUSTOMER_NM.value.length<2){
	            return false;
	     }
		
		if (!obj.CUSTOMER_GD.value || obj.CUSTOMER_GD.value.trim().length == 0) {
			obj.CUSTOMER_GD.value = "";
			$('.genderspan').html('<span style="color:red">성별이 입력되지 않았습니다.</span>');
			return false;
		}else{
			$('.genderspan').html('<span style="color:red"></span>');
		}
		//age
		if (!obj.CUSTOMER_AGE.value || obj.CUSTOMER_AGE.value.trim().length == 0) {
			obj.CUSTOMER_AGE.value = "";
			$('.agespan').html('<span style="color:red">나이가 입력되지 않았습니다.</span>');
			return false;
		}else{
			$('.agespan').html('<span style="color:red"></span>');
		}
		
		if (!obj.CUSTOMER_PHONE.value
				|| obj.CUSTOMER_PHONE.value.trim().length == 0) {
			obj.CUSTOMER_PHONE.value = "";
			$('.phonespan').html('<span style="color:red">휴대폰 번호가 입력되지 않았습니다.</span>');
			return false;
		}else{
			$('.phonespan').html('<span style="color:red"></span>');
		}
		if (!obj.CUSTOMER_ADDR.value
				|| obj.CUSTOMER_ADDR.value.trim().length == 0) {
			obj.CUSTOMER_ADDR.value = "";
			$('.addrspan').html('<span style="color:red">주소가 입력되지 않았습니다.</span>');
			return false;
		}else{
			$('.addrspan').html('<span style="color:red"></span>');
		}
	}
	
	
	$(document).ready(function() {
		$('input[name="CUSTOMER_PWD2"]').keyup(function() {
			var pw = $('input[name="CUSTOMER_PWD"]').val();
			var pw2 = $('input[name="CUSTOMER_PWD2"]').val();
			if(pw != pw2 || pw2 == ''){
			 $('.same').html('<span style="color:red">비밀번호가 맞지 않아요</span>');
			 $('input[name="registerok"]').attr('disabled','disabled');
			 return false;
				
			}else if(pw == pw2){
				$('.same').html('<span style="color:blue">비밀번호가 맞습니다.</span>');
				$('input[name="registerok"]').removeAttr('disabled');
				return false;
			}else
				return true;
			
		});
		$('input[name="idceck"]').click(function() {
			
			var CUSTOMER_ID = $('input[name="CUSTOMER_ID"]').val();
			
			$.ajax({
				url:"checkId.del",
				data:{'CUSTOMER_ID':CUSTOMER_ID},
				method:"POST",
				success:function(result){
					if(result == '1'){
						alert("아이디 중복입니다.");
						$('input[name="registerok"]').attr('disabled','disabled');
						$('.idsame').html('<span style="color:red">아이디 중복입니다.</span>');
						return false;
					}else if(result == '0'){
						alert("사용 가능한 아이디 입니다.");
						$('input[name="registerok"]').removeAttr('disabled');
						$('.idsame').html('<span style="color:red"></span>');
						return false;
					}
				}
			});
			
		});
	});
</script>

