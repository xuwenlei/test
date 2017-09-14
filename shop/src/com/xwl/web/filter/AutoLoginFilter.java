package com.xwl.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.xwl.domain.User;
import com.xwl.service.UserService;

public class AutoLoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		if (user == null) {
			String cookie_username = null;
			String cookie_password = null;
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("cookie_username".equals(cookie.getName())) {
						cookie_username = cookie.getValue();
					}
					if ("cookie_password".equals(cookie.getName())) {
						cookie_password = cookie.getValue();
					}
				}
			}
			if (cookie_username != null && cookie_password != null) {
				UserService service = new UserService();
				user = service.login(cookie_username, cookie_password);
				req.getSession().setAttribute("user", user);
			}
		}
		chain.doFilter(req, response);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
