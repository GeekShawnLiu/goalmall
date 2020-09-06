package www.tonghao.common;

import java.math.BigDecimal;

/**  

* <p>Title: Constant</p>  

* <p>Description: 常量类</p>  

* @author Yml  

* @date 2018年10月29日  

*/
public class Constant {
	
	/*
	 * 编码格式
	 */
	public static final String CHARSET_ENCODING = "utf-8";
	
	/**
	 * 商城网站Url
	 */
	public static String mallSiteUrl = "";
	
	/**
	 * 商城网站ctx
	 */
	public static String mallSiteCtx = "";
	
	/*
	 * 图片站点
	 */
	public static String imgSiteUrl = ""; 
	
	/**
	 * 批量网站Url
	 */
	public static String centralSiteUrl = "";
	
	/**
	 * 批量网站ctx
	 */
	public static String centralSiteCtx = "";
	
	/*
	 * 上传基础路径
	 */
	public static String UPLOAD_BASE_PATH = "";
	
	/*
	 *图片上传路径 
	 */
	public static final String IMAGE_UPLOAD_PATH = "/upload/image/${.now?string('yyyyMM')}/${.now?string('dd')}/";
	
	/*
	 * 文件上传路径 
	 */
	public static final String FILE_UPLOAD_PATH = "/upload/file/${.now?string('yyyyMM')}/${.now?string('dd')}/";
	
	 /**
     * 权限分隔符
     */
    public static final String PERM_SEPARATOR = ",";
    
	 /**
     * session key 定义
     */
    public static final class SessionKeyStore {
        public static final String LOGIN_USER = "loginUser";
        public static final String USER_ROLE = "userRole";
        public static final String USER_MENU = "userMenu";
    }
    
    /**
     * 最大商品数
     */
    public static final Integer MAX_PRODUCT_CART_COUNT = 9999;

    /**
     * es 默认集群名称
     */
    public static String ES_DEFAULT_CLUSTERNAME = "elasticsearch";
    
    /**
     * 默认苏宁地区ID
     */
    public static final String DEFALUT_SN_AREAID = "531"; //531 济南市
    
    /**
     * 默认邮编
     */
    public static final String DEFALUT_ZIP_CODE = "250000"; //默认济南市
    
    /**
     * 默认地区ID
     */
    public static final Long defalut_area_id = 370101L;//济南市辖区
    
    /**
     * 集采机构根节点
     */
    public static final String ORG_ROOT_CODE = "01";
    
    /**
     * 超市竞价金额边界
     */
    public static final BigDecimal MALL_BID_AMOUNT_LIMIT = new BigDecimal(200000);

    /**
     * 订单确认等待时间  单位：分钟
     */
    public static final int ORDER_CONFIRM_WAIT_TIME = 5;
    
    /**
     * 订单未支付自动取消等待时间  单位：分钟
     */
    public static final int ORDER_CANCEL_WAIT_TIME = 15;
    
    /**
     * 相似商品价格系数 
     */
    public static final BigDecimal SIMILAR_PRICE_RATE = new BigDecimal(0.1);
}
