package com.xwl.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xwl.domain.Category;
import com.xwl.domain.Order;
import com.xwl.domain.Product;
import com.xwl.service.AdminService;
import com.xwl.utils.CommersUtils;

public class AdminServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	//ajax异步查询订单详情
	public void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String oid = request.getParameter("oid");
		AdminService service = new AdminService();
		List<Map<String, Object>> mapList = service.findOrderInfoByOid(oid);
		Gson gson = new Gson();
		String json = gson.toJson(mapList);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		
	}
	//添加类别
	public void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cname = request.getParameter("cname");
		Category category = new Category();
		category.setCid(CommersUtils.getUUID());
		category.setCname(cname);
		AdminService service = new AdminService();
		service.addCategory(category);
		response.sendRedirect(request.getContextPath()+"/admin?method=adminCategoryList");
	}
	
	//删除类别
	public void delCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		AdminService service = new AdminService();
		service.deleteCategoryByCid(cid);
		response.sendRedirect(request.getContextPath()+"/admin?method=adminCategoryList");
	}

	//更新类别
	public void categoryUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cname = request.getParameter("cname");
		String cid = request.getParameter("cid");
		AdminService service = new AdminService();
		service.updateCategory(cid,cname);
		response.sendRedirect(request.getContextPath()+"/admin?method=adminCategoryList");
	}
	
	//获取类别回显
	public void categoryUpdateUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		AdminService service = new AdminService();
		Category category = service.getCategoryByCid(cid);
		request.setAttribute("category", category);
		request.getRequestDispatcher("/admin/category/edit.jsp").forward(request, response);
		
	}
	
	//获取类别列表
	public void adminCategoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service = new AdminService();
		List<Category> categories = service.findAllCategory();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("admin/category/list.jsp").forward(request, response);
	}
	
	//获取订单列表
	public void adminOrderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service = new AdminService();
		List<Order> orders = service.findAllOrderList();
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("admin/order/list.jsp").forward(request, response);
	}
	
	//删除商品
	public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		AdminService service = new AdminService();
		service.deleteProductByPid(pid);
		response.sendRedirect(request.getContextPath()+"/admin?method=adminProductList");
	}
	
	//修改商品信息
	public void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		AdminService service = new AdminService();
		List<Category> categories = service.findAllCategory();
		Product product = service.findProductByPid(pid);
		request.setAttribute("product", product);
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
	}
	
	//获取商品列表
	public void adminProductList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service = new AdminService();
		List<Product> products = service.findAllProduct();
		request.setAttribute("products", products);
		request.getRequestDispatcher("admin/product/list.jsp").forward(request, response);
	}
	
	//获取所有类别
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service = new AdminService();
		List<Category> categories = service.findAllCategory();
		Gson gson = new Gson();
		String json = gson.toJson(categories);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	}

}