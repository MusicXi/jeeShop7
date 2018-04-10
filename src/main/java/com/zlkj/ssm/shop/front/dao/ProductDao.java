package com.zlkj.ssm.shop.front.dao;import java.util.List;import com.zlkj.ssm.shop.core.DaoManager;import com.zlkj.ssm.shop.front.entity.Product;import com.zlkj.ssm.shop.front.entity.ProductStockInfo;public interface ProductDao extends DaoManager<Product> {	/**	 * @param id	 * @return	 */	List<Product> selectParameterList(String id);	List<ProductStockInfo> selectStockList(Product product);	List<Product> selectListProductHTML(Product product);	List<Product> selectProductListByIds(Product p);		/**	 * 支付成功后更新商品库存数据	 * @param product	 */	void updateStockAfterPaySuccess(Product product);	List<Product> selectHotSearch(Product p);	List<Product> loadHotProductShowInSuperMenu(Product product);	void updateHit(Product p);	List<Product> selectPageLeftHotProducts(Product p);	List<Product> selectActivityProductList(Product p);}