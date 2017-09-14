package com.xwl.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.xwl.domain.Category;
import com.xwl.domain.Product;
import com.xwl.service.AdminService;

public class AdminUpdateProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Product product = new Product();
		Map<String, Object> map = new HashMap<>();
		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			if(list!=null){
				for (FileItem item : list) {
					boolean formField = item.isFormField();
					if(formField){
						String fieldName = item.getFieldName();
						String fieldValue = item.getString("UTF-8");
						map.put(fieldName, fieldValue);
					}else {
						String name = item.getName();
						InputStream in = item.getInputStream();
						String path = this.getServletContext().getRealPath("upload");
						OutputStream out = new FileOutputStream(path+"/"+name);
						IOUtils.copy(in, out);
						out.close();
						in.close();
						map.put("pimage", "upload/"+name);
					}
				}
			}
			BeanUtils.populate(product, map);
			product.setPdate(new Date());
			product.setPflag(1);
			Category category = new Category();
			category.setCid(map.get("cid").toString());
			product.setCategory(category);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		AdminService service = new AdminService();
		service.updateProduct(product);
		response.sendRedirect(request.getContextPath()+"/admin?method=adminProductList");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}