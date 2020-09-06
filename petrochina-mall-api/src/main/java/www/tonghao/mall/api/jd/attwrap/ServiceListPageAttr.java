package www.tonghao.mall.api.jd.attwrap;

import java.util.List;

/**
 * 
 * Description: 售后服务单信息
 * 
 * @version 2019年7月8日
 * @since JDK1.8
 */
public class ServiceListPageAttr {

	/**
	 * 售后服务单列表
	 */
	private List<AfsServicebyCustomerPinAttr> serviceInfoList;

	/**
	 * 总记录数
	 */
	private Integer totalNum;

	/**
	 * 每页记录数
	 */
	private Integer pageSize;

	/**
	 * 总页数
	 */
	private Integer pageNum;

	/**
	 * 当前页数
	 */
	private Integer pageIndex;

	public List<AfsServicebyCustomerPinAttr> getServiceInfoList() {
		return serviceInfoList;
	}

	public void setServiceInfoList(List<AfsServicebyCustomerPinAttr> serviceInfoList) {
		this.serviceInfoList = serviceInfoList;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
}
