package com.xwl.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.xwl.domain.User;
import com.xwl.service.UserService;
import com.xwl.utils.CommersUtils;

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public void logot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.removeAttribute("cart");
		Cookie cookie1 = new Cookie("cookie_username", "");
		cookie1.setMaxAge(0);
		Cookie cookie2 = new Cookie("cookie_password", "");
		cookie2.setMaxAge(0);
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = null;
		UserService service = new UserService();
		user = service.login(username, password);
		if (user != null) {
			String autoLogin = request.getParameter("autoLogin");
			if ("autoLogin".equals(autoLogin)) {
				Cookie cookie_username = new Cookie("cookie_username", username);
				cookie_username.setMaxAge(10 * 60);
				Cookie cookie_password = new Cookie("cookie_password", password);
				cookie_password.setMaxAge(10 * 60);
				response.addCookie(cookie_username);
				response.addCookie(cookie_password);
			}
			
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath());
			
		} else {
			request.setAttribute("loginError", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String activeCode = request.getParameter("activeCode");
		UserService service = new UserService();
		service.active(activeCode);
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	public void checkUsername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		UserService service = new UserService();
		boolean isExist = service.checkUsername(username);
		String json = "{\"isExist\":" + isExist + "}";
		response.getWriter().write(json);
	}

	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<?, ?> map = request.getParameterMap();
		User user = new User();
		try {
			// 自己指定一个类型转换器
			ConvertUtils.register(new Converter() {
				@Override
				public Object convert(@SuppressWarnings("rawtypes") Class clazz, Object value) {
					// 将String转成Date
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parse = null;
					try {
						parse = format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return parse;
				}
			}, Date.class);

			// Date不能封装
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		user.setUid(CommersUtils.getUUID());
		user.setTelephone("15855670003");
		user.setState(0);
		String activeCode = CommersUtils.getUUID();
		user.setCode(activeCode);

		UserService service = new UserService();
		boolean isRegisterSuccess = service.regist(user);
		if (isRegisterSuccess) {
			// String emailMsg =
			// "恭喜你注册成功,请点击下面的连接进行激活账户<a
			// href='http://localhost:8080/shop/activeServlet?activeCode="+activeCode+"'>"
			// +
			// "http://localhost:8080/shop/activeServlet?activeCode="+activeCode+"</a>";
			// try {
			// MailUtils.sendMail(user.getEmail(), emailMsg);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
		} else {
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}
	}

}
