package com.xwl.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.xwl.domain.Cart;
import com.xwl.domain.CartItem;
import com.xwl.domain.Category;
import com.xwl.domain.Order;
import com.xwl.domain.OrderItem;
import com.xwl.domain.PageBean;
import com.xwl.domain.Product;
import com.xwl.domain.User;
import com.xwl.service.ProductService;
import com.xwl.utils.CommersUtils;

public class ProductServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	// 我的订单
	public void myOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 判断用户是否已经登陆
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			// 没有登陆
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		ProductService service = new ProductService();
		List<Order> orderList = service.finAllOrders(user.getUid());
		if (orderList != null) {
			for (Order order : orderList) {
				String oid = order.getOid();
				List<Map<String, Object>> mapList = service.findAllOrderItems(oid);
				for (Map<String, Object> map : mapList) {
					try {
						// 从map中取出count，subtotal封装到orderitem
						OrderItem orderItem = new OrderItem();
						// orderItem.setCount(Integer.parseInt(map.get("count").toString()));
						BeanUtils.populate(orderItem, map);

						// 从map中取出pname，pimage，shop_pirce封装到product
						Product product = new Product();
						BeanUtils.populate(product, map);
						orderItem.setProduct(product);
						order.getOrderItems().add(orderItem);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		// order封装完毕

		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);

	}

	// 确认订单
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.更新收货人信息
		Map<String, String[]> map = request.getParameterMap();
		Order order = new Order();
		try {
			BeanUtils.populate(order, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ProductService service = new ProductService();
		service.updateOrderAddr(order);

		// 2.在线支付
		// 获得 支付必须基本数据
		String orderid = request.getParameter("oid");
		service.updateOrderState(orderid);
		
		// 浏览器重定向
		response.sendRedirect(request.getContextPath()+"/paySuccess.jsp");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println("<h1>付款成功！等待商城进一步操作！等待收货...</h1>");
		
		
		// String money = order.getTotal()+"";
		/*String money = "0.01";
		// 银行
		String pd_FrpId = request.getParameter("pd_FrpId");

		// 发给支付公司需要哪些数据
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = orderid;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		// 生成url --- url?
		String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId=" + pd_FrpId + "&p0_Cmd=" + p0_Cmd
				+ "&p1_MerId=" + p1_MerId + "&p2_Order=" + p2_Order + "&p3_Amt=" + p3_Amt + "&p4_Cur=" + p4_Cur
				+ "&p5_Pid=" + p5_Pid + "&p6_Pcat=" + p6_Pcat + "&p7_Pdesc=" + p7_Pdesc + "&p8_Url=" + p8_Url
				+ "&p9_SAF=" + p9_SAF + "&pa_MP=" + pa_MP + "&pr_NeedResponse=" + pr_NeedResponse + "&hmac=" + hmac;

		// 重定向到第三方支付平台
		response.sendRedirect(url);*/
	}

	// 提交订单
	public void submitOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 判断用户是否已经登陆
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			// 没有登陆
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		Order order = new Order();
		Cart cart = (Cart) session.getAttribute("cart");
		order.setOid(CommersUtils.getUUID());
		order.setOrderTime(new Date());
		order.setTotal(cart.getTotal());
		order.setState(0);
		order.setAddress(null);
		order.setName(null);
		order.setTelephone(null);
		order.setUser(user);

		// 获得购物车中的购物项的集合map
		// 封装CartItem
		Map<String, CartItem> cartItems = cart.getCartItems();
		for (Map.Entry<String, CartItem> entry : cartItems.entrySet()) {
			// 取出每一个购物项
			CartItem cartItem = entry.getValue();
			// 创建一个新的订单项
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(CommersUtils.getUUID());
			orderItem.setCount(cartItem.getBuyNum());
			orderItem.setSubTotal(cartItem.getSubTotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			// 将该订单项添加到订单的订单项集合中
			order.getOrderItems().add(orderItem);
		}
		// order对象封装完毕

		ProductService service = new ProductService();
		service.submitOrder(order);

		// 页面跳转
		session.setAttribute("order", order);
		response.sendRedirect(request.getContextPath() + "/order_info.jsp");

	}

	// 清空购物车
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}

	// 删除单一商品
	public void delProFromCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			Map<String, CartItem> cartItems = cart.getCartItems();
			// 需要修改总价
			cart.setTotal(cart.getTotal() - cartItems.get(pid).getSubTotal());
			cartItems.remove(pid);
			// 引用传递可以不用再放进去
			cart.setCartItems(cartItems);
		}
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}

	// 添加商品到购物车
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String pid = request.getParameter("pid");
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));

		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);
		double subTotal = product.getShop_price() * buyNum;
		CartItem item = new CartItem();
		item.setBuyNum(buyNum);
		item.setProduct(product);
		item.setSubTotal(subTotal);

		// 获得购物车，判断是否已经存在购物车
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
		}

		Map<String, CartItem> cartItems = cart.getCartItems();
		// double newsubTotal = 0.0;
		if (cartItems.containsKey(pid)) {
			CartItem cartItem = cartItems.get(pid);
			int oldBuyNum = cartItem.getBuyNum();
			oldBuyNum += buyNum;
			cartItem.setBuyNum(oldBuyNum);
			cart.setCartItems(cartItems);
			// 修改小计
			double oldsubTotal = cartItem.getSubTotal();
			// 新买的商品小计
			// newsubTotal = buyNum*product.getShop_price();
			cartItem.setSubTotal(oldsubTotal + subTotal);
		} else {
			cart.getCartItems().put(product.getPid(), item);
			// newsubTotal = subTotal;
		}

		double total = cart.getTotal() + subTotal;
		cart.setTotal(total);
		session.setAttribute("cart", cart);

		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}

	// 商品类别
	public void categoryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();

		//Jedis jedis = JedisPoolUtils.getJedis();
		//String categoryListJson = jedis.get("categoryListJson");
		//if (categoryListJson == null) {
			//System.out.println("缓存没有数据,查询数据库");
			List<Category> categoryList = service.findAllCategory();
			Gson gson = new Gson();
			String categoryListJson = gson.toJson(categoryList);
			//jedis.set("categoryListJson", categoryListJson);
		//}

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(categoryListJson);
	}

	// 首页
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = new ProductService();
		List<Product> hotProductList = service.findHotProductList();
		List<Product> newProductList = service.findNewProductList();

		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);

		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");

		String currentPage = request.getParameter("currentPage");
		String cid = request.getParameter("cid");
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cid", cid);

		String pids = pid;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					pids = cookie.getValue();
					String[] split = pids.split("-");
					List<String> asList = Arrays.asList(split);
					LinkedList<String> list = new LinkedList<String>(asList);
					if (list.contains(pid)) {
						list.remove(pid);
						list.addFirst(pid);
					} else {
						list.addFirst(pid);
					}
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < list.size() && i < 7; i++) {
						sb.append(list.get(i));
						sb.append("-");
					}
					pids = sb.substring(0, sb.length() - 1);
				}
			}
		}

		Cookie cookie = new Cookie("pids", pids);
		response.addCookie(cookie);

		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);
		Category category = service.findCategoryByCid(cid);
		request.setAttribute("product", product);
		request.setAttribute("category", category);
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	public void productListByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null)
			currentPageStr = "1";
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 12;
		ProductService service = new ProductService();
		PageBean<Product> pb = service.findProductListByCid(cid, currentPage, currentCount);

		request.setAttribute("cid", cid);
		request.setAttribute("pageBean", pb);

		// 获得客户端携带名字叫pids的cookies
		Cookie[] cookies = request.getCookies();
		List<Product> historyProductList = new ArrayList<Product>();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for (String pid : split) {
						Product pro = service.findProductByPid(pid);
						historyProductList.add(pro);
					}
				}
			}
		}
		Category category = service.findCategoryByCid(cid);
		request.setAttribute("category", category);
		request.setAttribute("historyProductList", historyProductList);

		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

}
