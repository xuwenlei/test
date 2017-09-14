package com.xwl.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xwl.domain.Category;
import com.xwl.domain.Order;
import com.xwl.domain.OrderItem;
import com.xwl.domain.Product;
import com.xwl.utils.DataSourceUtils;

public class ProductDao {

	private QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

	public List<Product> findHotProductList() throws SQLException {
		String sql = "select * from product where is_hot = ? limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class), 1, 0, 9);
		return list;
	}

	public List<Product> findNewProductList() throws SQLException {
		String sql = "select * from product order by pdate desc limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class), 0, 9);
		return list;
	}

	public List<Category> findAllCategory() throws SQLException {
		String sql = "select * from category";
		List<Category> list = runner.query(sql, new BeanListHandler<>(Category.class));
		return list;
	}

	public int getCount(String cid) throws SQLException {
		String sql = "select count(*) from product where cid = ?";
		Long query = (long) runner.query(sql, new ScalarHandler<>(), cid);
		return query.intValue();
	}

	public List<Product> findProductListByCid(String cid, int index, int currentCount) throws SQLException {
		String sql = "select * from product where cid = ? order by pdate desc limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class), cid, index, currentCount);
		return list;
	}

	public Product findProductByPid(String pid) throws SQLException {
		String sql = "select * from product where pid = ?";
		Product product = runner.query(sql, new BeanHandler<>(Product.class), pid);
		return product;
	}

	public void addOrders(Order order) throws SQLException {
		QueryRunner runner2 = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		runner2.update(DataSourceUtils.getConnection(), sql, order.getOid(), order.getOrderTime(), order.getTotal(),
				order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid());
	}

	public void addOrderItem(Order order) throws SQLException {
		QueryRunner runner2 = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem item : orderItems) {
			runner2.update(DataSourceUtils.getConnection(), sql, item.getItemid(), item.getCount(), item.getSubTotal(),
					item.getProduct().getPid(), item.getOrder().getOid());
		}
	}

	public void updateOrderAddr(Order order) throws SQLException {
		String sql = "update orders set address = ?, name= ?, telephone = ? where oid = ?";
		runner.update(sql, order.getAddress(), order.getName(), order.getTelephone(), order.getOid());
	}

	public void updateOrderState(String r6_Order) throws SQLException {
		String sql = "update orders set state = ?  where oid = ?";
		runner.update(sql, 1, r6_Order);
	}

	public List<Order> finAllOrders(String uid) throws SQLException {
		String sql = "select * from orders where uid = ?";
		List<Order> query = runner.query(sql, new BeanListHandler<>(Order.class), uid);
		return query;
	}

	public List<Map<String, Object>> findAllOrderItems(String oid) throws SQLException {
		String sql = "select p.pimage,p.pname,p.shop_price,o.count,o.subtotal subTotal from orderitem o,product p where p.pid = o.pid and o.oid = ?";
		List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), oid);
		return mapList;
	}

	public Category findCategoryByCid(String cid) throws SQLException {
		String sql = "select * from category where cid = ?";
		Category category = runner.query(sql, new BeanHandler<>(Category.class), cid);
		return category;
	}

}
