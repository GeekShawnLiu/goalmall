package www.tonghao.common.utils;

import www.tonghao.common.Constant;

/**
 * 产品工具
 * @author developer001
 *
 */
public class ProductUtil {
	
	/**
	 * 得到商品Url
	 * @return
	 */
	public static String getMallProductUrl(Long catalogId,Long productId){
		return Constant.mallSiteUrl+"/html/product.html?c_"+catalogId+"|p_"+productId;
	}
	
	/**
	 * 得到商城品目Url
	 * @param catalogId
	 * @return
	 */
	public static String getMallCatalogUrl(Long catalogId){
		return Constant.mallSiteUrl+"/html/goods-list.html?c_"+catalogId+"|t_0";
	}
	
	/**
	 * 得到商城竞价品目Url
	 * @param catalogId
	 * @return
	 */
	public static String getMallBidCatalogUrl(Long catalogId){
		return Constant.mallSiteUrl+"/html/goods-list.html?c_"+catalogId+"|t_0|f_bid";
	}
	
	/**
	 * 得到批量品目Url
	 * @param catalogId
	 * @return
	 */
	public static String getCentralCatalogUrl(Long catalogId){
		return Constant.centralSiteUrl+"/html/goods-list.html?c_"+catalogId;
	}
}
