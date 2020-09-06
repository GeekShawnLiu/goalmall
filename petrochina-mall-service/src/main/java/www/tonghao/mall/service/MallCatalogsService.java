package www.tonghao.mall.service;

import java.util.List;
import java.util.Map;

import www.tonghao.common.warpper.TreeNode;
import www.tonghao.mall.entity.MallCatalogs;
import www.tonghao.service.common.base.BaseService;

public interface MallCatalogsService extends BaseService<MallCatalogs> {
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	MallCatalogs findById(Long id);
	
	/**
	 * 获取品目节点
	 * @param platformCatalogId
	 * @return
	 */
	TreeNode findCatalogNode(Long platformCatalogId);
	
	/**
	 * 获取品目节点列表
	 * @param platformCatalogId
	 * @return
	 */
	List<TreeNode> getNodeList(Long platformCatalogId);
	
	/**
	 * 获取汽车品目节点列表
	 * @param platformCatalogId
	 * @return
	 */
	List<TreeNode> getCarNodeList(Long platformCatalogId);
	
	/**
	 * 获取品目树
	 * @param count
	 * @return
	 */
	List<TreeNode> getTreeList(Integer count, String displayPlatform);
	
	/**
	 * 获取可用商品品目节点列表
	 * @return
	 */
	List<TreeNode> getUsableNodeList();
	
	/**
	 * 供应商树品目
	 * @param supplierId
	 * @return
	 */
	List<TreeNode> getSupplierTreeList(Long supplierId);
	
	/**
	 * 获取竞价品目列表
	 * @return
	 */
	List<TreeNode> getBiddingTreeList();
	
	/**
	 * 
	 * Description: 获取活动详情页商品一级品目信息
	 * 
	 * @data 2019年5月30日
	 * @param 
	 * @return
	 */
	List<MallCatalogs> getMallCatalogsByActivity(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 查询有上架商品的一级品目信息
	 * 
	 * @data 2019年6月23日
	 * @param 
	 * @return
	 */
	List<MallCatalogs> selectMallCatalogsByProducts();
}
