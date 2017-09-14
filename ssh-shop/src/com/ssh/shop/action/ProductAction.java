package com.ssh.shop.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ssh.shop.service.ProductService;

public class ProductAction extends ActionSupport implements RequestAware{

	private static final long serialVersionUID = 1L;
	
	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	public String index(){
		request.put("hotProducts", productService.findHotProductList());
		request.put("newProducts", productService.findNewProductList());
		return "list";
	}

	private Map<String, Object> request = null;
	
	@Override
	public void setRequest(Map<String, Object> arg0) {
		this.request = arg0;
	}

}
