package com.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.frame.DependenciesBiz;
import com.frame.OrDependenciesBiz;
import com.vo.Order;
import com.vo.Product;

@Controller
public class OrderController {
	
	@Resource(name="pbiz")
	DependenciesBiz<String, Product> pbiz;
	
	@Resource(name="obiz")
	OrDependenciesBiz<String, Order> obiz;
	
	@RequestMapping("/productorder.del")
	public ModelAndView productorder(ModelAndView mv, String product_id) {
		
		try {
			Product product=pbiz.get(product_id);
			System.out.println(product.toString());
			mv.addObject("p",product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		mv.addObject("center", "user/order");
		mv.setViewName("home");
		return mv;
	}
	
	@RequestMapping("/orderdetail.del")
	public ModelAndView orderdetail(ModelAndView mv,Order order,String product_id) {
		System.out.println(product_id);

		System.out.println(order.toString());
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("HH:mm:ss").format(date));
        String today2=(new SimpleDateFormat("yyyyMMdd").format(date));
        System.out.println(today);
        int sub_id2=0;
		try {
			Order order2 = obiz.oidmaxselect();
			
			if(order2 != null) { //테이블이 비어있지 않다면--> 그대로 값을 증가시키면 되겠음
				System.out.println(order2.toString());
				String str_sub_id2=order2.getOrder_id();
				str_sub_id2=str_sub_id2.substring(8);
				sub_id2=Integer.parseInt(str_sub_id2);
				order.setSub_id2(sub_id2++);
				order.setOrder_id(today2+sub_id2);
			}else {
				order.setSub_id2(1);
				order.setOrder_id(today2+1);
			}
			order.setCustomer_id("ID01");
			order.setCurrent_time(today);
			System.out.println(order.toString());

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			Product product1=pbiz.get(product_id);
			System.out.println("prd:"+product1.toString());
			obiz.register(order);
			
			
			mv.addObject("p",product1);
			Order order2=obiz.select_oid(today2+sub_id2);
			mv.addObject("o",order2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mv.addObject("center", "user/orderdetail");
		mv.setViewName("home");
		return mv;
	}
}
