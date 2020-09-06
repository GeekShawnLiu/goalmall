package www.tonghao.service.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import www.tonghao.common.warpper.PriceRangeModel;
import www.tonghao.service.common.entity.Brand;
import www.tonghao.service.common.entity.Parameter;
import www.tonghao.service.common.entity.ProductParameter;
import www.tonghao.service.common.entity.Products;
import www.tonghao.service.common.entity.Suppliers;

import com.beust.jcommander.internal.Lists;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
/**
 * 商品搜索响应封装
 * @author developer001
 *
 */
@ApiModel(value="商品搜索响应封装")
public class ProductSearchResponse implements IProductResponse,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="品牌")
	private List<Brand> brands; //品牌
	
	@ApiModelProperty(value="供应商")
	private List<Suppliers> suppliers; //供应商
	
	/**
	 * 价格区间
	 */
	@ApiModelProperty(value="价格区间")
	private List<PriceRangeModel> priceRanges;
	
	@ApiModelProperty(value="商品参数")
	private List<ProductParameter> productParameters;
	
	@ApiModelProperty(value="商品分页")
	private PageInfo<? extends Products> page; //商品分页
	
    public List<Brand> getBrands() {
		return brands;
	}
    
	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}
	
	public List<Suppliers> getSuppliers() {
		return suppliers;
	}
	
	public void setSuppliers(List<Suppliers> suppliers) {
		this.suppliers = suppliers;
	}
	
	public PageInfo<? extends Products> getPage() {
		return page;
	}
	
	public void setPage(PageInfo<? extends Products> page) {
		this.page = page;
	}
	
	public List<ProductParameter> getProductParameters() {
		return productParameters;
	}
	
	public void setProductParameters(List<ProductParameter> productParameters) {
		this.productParameters = productParameters;
	}
	
	public List<PriceRangeModel> getPriceRanges() {
		return priceRanges;
	}
	
	public void setPriceRanges(List<PriceRangeModel> priceRanges) {
		this.priceRanges = priceRanges;
	}
	
	/**
	 * 得到分组品牌
	 * @return 搜字母大写 -> 品牌列表
	 */
	public Map<String,List<Brand>> getGroupByBrands(){
		Map<String,List<Brand>> result = Maps.newLinkedHashMap();
		List<Brand> brandList = getBrands();
		if(brandList!=null){
			Collections.sort(brandList, (item1,item2) -> {
				String s1 =  item1.getFirstUpperCasePinyin();
				String s2 = item2.getFirstUpperCasePinyin();
				if(s1.matches("^[a-zA-Z]+$")&&s2.matches("^[a-zA-Z]+$")){
					return s1.compareTo(s2);
				}
				
				if(s1.matches("^[a-zA-Z]+$")){
					return -1;
				}
				return 1;
			});
			
			for(Brand b:brandList){
				String firstUpperCasePinyin = b.getFirstUpperCasePinyin();
				if(result.containsKey(firstUpperCasePinyin)){
					List<Brand> list = result.get(firstUpperCasePinyin);
					list.add(b);
				}else{
					List<Brand> list = Lists.newArrayList();
					list.add(b);
					result.put(firstUpperCasePinyin,list);
				}
			}
			
		}
		return result;
	} 
	
	/**
	 * 获取参数筛选更多label文本
	 * @return
	 */
	public String getParamMoreLable(){
		List<ProductParameter> pps = getProductParameters();
		if(pps!=null&&pps.size()>0){
			StringBuffer item = new StringBuffer("(");
			int count = 0;
			for (ProductParameter pp:pps) {
				Parameter p = pp.getParameter();
				if(p!=null){
					item.append(p.getName());
					if(count==2){
						break;
					}else{
						item.append(p.getName()).append("、");
					}
					count++;
				}
			}
			item.append("等)");
			return item.toString();
		}
		return null;
	}
}