package com.ssh.shop.dao;

import java.util.List;

import com.ssh.shop.entities.Category;
import com.ssh.shop.entities.OrderItem;
import com.ssh.shop.entities.Orders;
import com.ssh.shop.entities.Product;


public class ProductDao extends BaseDao{

	@SuppressWarnings("unchecked")
	public List<Product> findHotProductList(){
		String hql = "from Product p where p.isHot = 0";
		List<Product> list = getSession().createQuery(hql).setFirstResult(0).setMaxResults(9).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Product> findNewProductList(){
		String hql = "from Product p order by p.pdate";
		List<Product> list = getSession().createQuery(hql).setFirstResult(0).setMaxResults(9).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Category> findAllCategory(){
		String hql = "from Category";
		List<Category> list = getSession().createQuery(hql).list();
		return list;
	}

	public int getCount(String cid){
		String hql = "select count(*) from Product p where p.cid = ?";
		int query = (int) getSession().createQuery(hql).setString(0, cid).uniqueResult();
		return query;
	}

	@SuppressWarnings("unchecked")
	public List<Product> findProductListByCid(String cid, int index, int currentCount){
		String hql = "from Product p where p.cid = ?";
		List<Product> list = getSession().createQuery(hql).setString(0, cid).setFirstResult(index).setMaxResults(currentCount).list();
		return list;
	}

	public Product findProductByPid(String pid){
		String hql = "from Product p where p.pid = ?";
		Product product = (Product) getSession().createQuery(hql).setString(0, pid).uniqueResult();
		return product;
	}

	public void addOrders(Orders orders){
		getSession().saveOrUpdate(orders);
	}

	/*public void addOrderItem(Orders orders){
		QueryRunner runner2 = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem item : orderItems) {
			runner2.update(DataSourceUtils.getConnection(), sql, item.getItemid(), item.getCount(), item.getSubTotal(),
					item.getProduct().getPid(), item.getOrder().getOid());
		}
	}*/

	public void updateOrderAddr(Orders orders){
		getSession().saveOrUpdate(orders);
	}

	/*public void updateOrderState(String r6_Order){
		String sql = "update orders set state = ?  where oid = ?";
		runner.update(sql, 1, r6_Order);
	}*/

	@SuppressWarnings("unchecked")
	public List<Orders> finAllOrders(String uid){
		String hql = "from Orders o where o.uid = ?";
		List<Orders> query = getSession().createQuery(hql).setString(0, uid).list();
		return query;
	}

	@SuppressWarnings("unchecked")
	public List<OrderItem> findAllOrderItems(String oid){
		String hql = "from OrderItem o where o.oid = ?";
		List<OrderItem> list = getSession().createQuery(hql).setString(0, oid).list();
		return list;
	}

	public Category findCategoryByCid(String cid){
		String hql = "from Category c where c.cid = ?";
		Category category = (Category) getSession().createQuery(hql).setString(0, hql).uniqueResult();
		return category;
	}

}
