package www.tonghao.platform.service;

import java.util.List;
import java.util.Map;

import www.tonghao.platform.entity.DealContract;
import www.tonghao.service.common.base.BaseService;


/**
 * 成交合同
 * 
 * @author yggc
 *
 */
public interface DealContractService extends BaseService<DealContract> {

	/**
	 * <p>
	 * Title: createPdf
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author YML
	 * @param dealContractId
	 *            合同id
	 * @param fileName
	 *            生成pdf文件名称
	 * @return 生成pdf路径
	 */
	/**  
	 * <p>Title: createPdf</p>
	 * <p>Description: </p>  
	 * @author YML 
	 * @param dealContractId  合同id
	 * @param fileName  生成pdf文件名称
	 * @param sealImgPath 印章绝对路径(需先在附件管理功能处上传印章图片), 不加印章可传null
	 * @param sealPageNum 印章在pdf文件的页码, 不加印章可传null
	 * @param sealAbsoluteX  印章在pdf文件页码的坐标, 不加印章可传null
	 * @param sealAbsoluteY  印章在pdf文件页码的坐标, 不加印章可传null
	 * @return 生成pdf路径
	 */
	String createPdf(Long dealContractId, String fileName, String sealImgPath, Integer sealPageNum, Float sealAbsoluteX, Float sealAbsoluteY);

	/**
	 * 创建pdf
	 * 
	 * @param contract
	 * @param type
	 * @return
	 */
	String createPdf(DealContract contract, int type);

	/**
	 * 创建pdf
	 * 
	 * @param contract
	 * @param ftlContent
	 * @return
	 */
	String createPdf(DealContract contract, String ftlContent);

	/**
	 * 通过计划编号查询数量
	 * 
	 */
	Integer findCountByPlanCode(String planCode);

	/**
	 * 查询合同列表
	 */
	List<DealContract> find(Map<String, Object> map);

	DealContract getById(Long id);
	
	int updateStatus(DealContract dealContract);
	
	public Long findIdByCode(String planCode);
	
	/**  
	 * @param dealContractId  合同id
	 * @param fileName  生成pdf文件名称
	 * @param sealPageNum 二维码在pdf文件的页码, 不加二维码可传null
	 * @param sealAbsoluteX  二维码在pdf文件页码的坐标, 不加二维码可传null
	 * @param sealAbsoluteY  二维码在pdf文件页码的坐标, 不加二维码可传null
	 * @return 生成pdf路径
	 */
	String createMallReceipt(Long dealContractId, String fileName, Integer sealPageNum, Float sealAbsoluteX, Float sealAbsoluteY);
}
