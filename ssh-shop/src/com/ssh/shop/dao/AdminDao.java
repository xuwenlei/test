package com.ssh.shop.dao;

import java.util.List;

import com.ssh.shop.entities.Category;
import com.ssh.shop.entities.Orders;
import com.ssh.shop.entities.Product;

public class AdminDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<Category> finAllCategory(){
		String hql = "from Category";
		List<Category> list = getSession().createQuery(hql).list();
		return list;
	}

	public void addProduct(Product product){
		getSession().saveOrUpdate(product);
	}

	@SuppressWarnings("unchecked")
	public List<Product> findAllProduct(){
		String hql = "from Product";
		List<Product> list = getSession().createQuery(hql).list();
		return list;
	}

	public Product findProductByPid(String pid){
		String hql = "from Product p where p.pid = ?";
		Product product = (Product) getSession().createQuery(hql).setString(0, pid).uniqueResult();
		return product;
	}

	public void updateProduct(Product product){
		getSession().saveOrUpdate(product);
	}

	public void deleteProductByPid(String pid){
		String hql = "delete from Product p where p.pid = ?";
		getSession().createQuery(hql).setString(0, pid).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Orders> findAllOrderList(){
		String hql = "from Orders";
		List<Orders> list = getSession().createQuery(hql).list();
		return list;
	}

	public Category getCategoryByCid(String cid){
		String hql = "from Category c where c.cid = ?";
		Category category = (Category) getSession().createQuery(hql).setString(0, cid).uniqueResult();
		return category;
	}

	public void updateCategory(Category category){
		getSession().saveOrUpdate(category);
	}

	public void deleteCategoryByCid(String cid){
		String hql = "delete from Category c where c.cid = ?";
		getSession().createQuery(hql).setString(0, cid).executeUpdate();
	}

	public void addCategory(Category category){
		getSession().saveOrUpdate(category);
	}

	@SuppressWarnings("unchecked")
	public List<Orders> findOrderInfoByOid(String oid){
		String hql = "from Orders o where o.oid = ?";
		List<Orders> list = getSession().createQuery(hql).setString(0, oid).list();
		return list;
	}

}
