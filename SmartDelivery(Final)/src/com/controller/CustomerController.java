package com.controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.frame.Biz;
import com.frame.DependenciesBiz;
import com.vo.Customer;
import com.vo.Product;

@Controller
public class CustomerController {
	
	@Resource(name="pbiz")
	DependenciesBiz<String, Product> pbiz;
	
	@Resource(name="cbiz")
	Biz<String,Customer> cbiz;
	
	@RequestMapping("/checkId.del")
	@ResponseBody
	public ModelAndView checkId(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String CUSTOMER_ID1 = request.getParameter("CUSTOMER_ID");
		String result="";
		try {
			System.out.println(CUSTOMER_ID1);
			Customer dbuser = cbiz.get(CUSTOMER_ID1);
			if(dbuser == null) {
				result = "0";
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(result);
				out.close();
				
			}else {
				result = "1";
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(result);
				out.close();
			}
			System.out.println("result:"+result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			mv.addObject("center","user/join");
			mv.setViewName("index");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	
	
	@RequestMapping("/customerjoinimpl.del")
	public ModelAndView register(ModelAndView mv, HttpServletRequest request) {
		String id=request.getParameter("CUSTOMER_ID");
		String pwd = request.getParameter("CUSTOMER_PWD");
		String name = request.getParameter("CUSTOMER_NM");
		String phone=request.getParameter("CUSTOMER_PHONE");
		String addr=request.getParameter("CUSTOMER_ADDR");
		String gender=request.getParameter("CUSTOMER_GD");
		String age=request.getParameter("CUSTOMER_AGE");
		Integer int_age=Integer.parseInt(age);
		
		Customer customer = new Customer(id, pwd, name, phone, addr, gender, int_age, 0);
		
		try {
			cbiz.register(customer);
		} catch (Exception e) {
			mv.addObject("join");
			e.printStackTrace();
		}
		mv.addObject("center","user/login");
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping("/loginimpl.del")
	public ModelAndView login(ModelAndView mv, HttpServletRequest request,
			HttpSession session) {
		String id=request.getParameter("CUSTOMER_ID");
		String pwd = request.getParameter("CUSTOMER_PWD");
		
		Customer customer=new Customer();
		ArrayList<Product> list;
		try {
			customer=cbiz.get(id);
			System.out.println(customer.toString());
			System.out.println("customerpwd:"+pwd);
			System.out.println("pwd:"+customer.getCustomer_pwd());
			if(pwd.equals(customer.getCustomer_pwd())) {
				session.setAttribute("loginuser", customer);
				
				try {
					list = pbiz.get();
					mv.addObject("plist", list);
					
				} catch (Exception e) {	
					e.printStackTrace();
				}
				mv.addObject("center","user/shop");
				mv.setViewName("index");
			}else {
			//	System.out.println("11111111111111");
				mv.addObject("center", "user/login");
				mv.setViewName("index");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;		
		
	}
	
	@RequestMapping("/logout.del")
	public ModelAndView logout(ModelAndView mv,
			HttpSession session) {
		if(session != null) {
			session.invalidate();
		}
		mv.addObject("center", "user/login");
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping("/dashboard.del")
	public ModelAndView dashboard(ModelAndView mv,
			HttpSession session) {
		
		mv.addObject("center", "admin/dashboard");
		mv.setViewName("index");
		return mv;
	}
	
}
