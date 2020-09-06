package www.tonghao.common.enums;

/**
 * 
 * Description: elasticSearch 索引类型
 * 
 * @version 2019年4月29日
 * @since JDK1.8
 */
public enum IndexMappingType {
	products("petrochina-product", "product"), // 商品
	supplier("petrochina-supplier", "supplier"); // 供应商

	IndexMappingType(String indexName, String mappingName) {
		this.indexName = indexName;
		this.mappingName = mappingName;
	}

	String indexName;
	
	String mappingName;

	/**
	 * 获取索引名
	 * 
	 * @return
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * 获取映射名字
	 * 
	 * @return
	 */
	public String getMappingName() {
		return mappingName;
	}
}
