package www.tonghao.service.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import www.tonghao.common.utils.PageBean;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.response.IProductResponse;

/**
 * 
 * Description: 搜索
 * 
 * @version 2019年4月29日
 * @since JDK1.8
 */
public interface ProductSearchService {

	/**
	 * 创建引库
	 */
	public void createIndex();

	/**
	 * 判断索引是否存在
	 * 
	 * @return
	 */
	boolean isIndexExists();

	/**
	 * 创建Mapping
	 */
	void createMapping();

	/**
	 * 根据数据库ID查询索引
	 * 
	 * @param id
	 * @return
	 */
	String findIndexId(long id);

	/**
	 * 添加
	 * 
	 * @param product
	 * @return
	 */
	boolean addData(Products product);

	/**
	 * 修改
	 * 
	 * @param indexId
	 * @param product
	 * @return
	 */
	boolean updateData(String indexId, Products product);

	/**
	 * 删除
	 * 
	 * @param indexId
	 * @return
	 */
	boolean deleteData(String indexId);

	/**
	 * 产品搜索
	 * 
	 * @param keywords
	 * @param page
	 * @param usableSupplierIds 可用供应商ids
	 * @return
	 */
	IProductResponse searcherProduct(String keywords, PageBean page, Set<Long> usableSupplierIds, Map<String, Object> queryfilter);

	/**
	 * 是否启用
	 * 
	 * @return
	 */
	boolean isEnable();
	
	/**
	 * 
	 * Description: 批量构建索引
	 * 
	 * @data 2019年4月29日
	 * @param 
	 * @return
	 */
	public void addBulkDatas(List<Products> products);
	
	/**
	 * 
	 * Description: 删除索引库
	 * 
	 * @data 2019年4月29日
	 * @param 
	 * @return
	 */
	public void deleteIndex();
}
