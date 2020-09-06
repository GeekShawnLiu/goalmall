package www.tonghao.platform.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.platform.entity.DealContract;
import www.tonghao.platform.entity.DealContractDetails;
import www.tonghao.platform.entity.DealContractPerson;
import www.tonghao.platform.entity.DealContractTpl;
import www.tonghao.platform.mapper.DealContractDetailsMapper;
import www.tonghao.platform.mapper.DealContractMapper;
import www.tonghao.platform.mapper.DealContractPersonMapper;
import www.tonghao.platform.mapper.PlanMapper;
import www.tonghao.platform.service.AttachmentService;
import www.tonghao.platform.service.DealContractService;
import www.tonghao.platform.service.DealContractTplService;
import www.tonghao.service.common.base.impl.BaseServiceImpl;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Service("dealContractService")
public class DealContractServiceImpl extends BaseServiceImpl<DealContract> implements DealContractService {

	private static Log log = LogFactory.getLog(DealContractServiceImpl.class);

	@Autowired
	private DealContractTplService dealContractTplService;

	@Autowired
	private DealContractMapper dealContractMapper;

	@Autowired
	private DealContractDetailsMapper dealContractDetailsMapper;
	
	@Autowired
	private DealContractPersonMapper dealContractPersonMapper;
	
	@Autowired
	private PlanMapper planMapper;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Override
	public String createPdf(Long dealContractId, String fileName, String sealImgPath, Integer sealPageNum, Float sealAbsoluteX, Float sealAbsoluteY) {
		String pdfRresultPath = null;//生成的pdf文件路径
		String resultPath = null;//生成的水印pdf文件路径
		String ftlContent = null;//模板内容
		DealContract data = null;//合同数据
		/*if (dealContractId != null) {
			data = dealContractMapper.selectByPrimaryKey(dealContractId);
			if (data != null) {
				if (StringUtils.isNotBlank(data.getAttaUrl())) {
					//如果已生成了pdf文件，则不再生成
					return data.getAttaUrl();
				}else {
					//封装合同明细
					DealContractDetails record = new DealContractDetails();
					record.setContractId(dealContractId);
					List<DealContractDetails> contractDetails = dealContractDetailsMapper.select(record);
					for (DealContractDetails dealContractDetails : contractDetails) {
						//封装合同人员
						DealContractPerson persons = new DealContractPerson();
						persons.setContractDetailId(dealContractDetails.getId());
						List<DealContractPerson> contractPersons = dealContractPersonMapper.select(persons);
						dealContractDetails.setPersons(contractPersons);
					}
					data.setDetails(contractDetails);
					
				}
			}else {
				return null;
			}
		} else {
			return null;
		}
		if (data.getTplId() != null) {
			//获取模板内容
			DealContractTpl contractTpl = dealContractTplService.selectByKey(data.getTplId());
			if (contractTpl != null) {
				ftlContent = contractTpl.getContent();
			}
		}
		if (StringUtils.isEmpty(ftlContent)) {
			return null;
		}
		if (StringUtils.isEmpty(fileName)) {
			fileName = UUID.randomUUID() + ".pdf";
		}
		try {
			pdfRresultPath = pdfCreateUtil.createPDFByFtlContent(data, fileName, ftlContent);
			if (StringUtils.isNotBlank(pdfRresultPath)) {
				//设置输出路径
	            String uploadPath = Constant.UPLOAD_BASE_PATH + "/contract" + Constant.FILE_UPLOAD_PATH;
	            File destFile = new File(uploadPath);
				if (!destFile.getParentFile().exists()) {
					destFile.getParentFile().mkdirs();
				}
				String destPath = uploadPath + UUID.randomUUID()+ "_" + dealContractId + "_水印.pdf";
				resultPath = FreemarkerUtils.process(destPath, null);
	            log.info("设置水印pdf输出路径：" + resultPath);
	            
				WaterMark.waterMark(pdfRresultPath, resultPath, "山东省省级政府采购网上商城", sealImgPath, sealPageNum, sealAbsoluteX, sealAbsoluteY);
				//删除pdf文件
				File file = new File(pdfRresultPath);
				if(file.exists()&&file.isFile()){
					file.delete();
				}
			}
			log.info(resultPath);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return resultPath;
	}

	@Override
	public String createPdf(DealContract contract, int type) {
		String resultPath = null;
		/*DealContractTpl tpl = dealContractTplService.getUsableByType(type);
		if (tpl == null) {
			throw new SpeException("操作失败,信息:合同模板不存在");
		}
		contract.setTplId(tpl.getId());
		try {
			resultPath = pdfCreateUtil.createPDFByFtlContent(contract, UUID.randomUUID() + ".pdf", tpl.getContent());
			log.info(resultPath);
		} catch (Exception e) {
			throw new SpeException("合同生成异常", e.fillInStackTrace());
		}*/
		return resultPath;
	}

	@Override
	public String createPdf(DealContract contract, String ftlContent) {
		String resultPath = null;
		/*try {
			resultPath = pdfCreateUtil.createPDFByFtlContent(contract, UUID.randomUUID() + ".pdf", ftlContent);
			log.info(resultPath);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return resultPath;
	}

	/**
	 * 查询最大的合同编号
	 * 
	 * @param projectCode
	 *            项目编号
	 * @return
	 */

	public Integer findCountByPlanCode(String planCode) {
		return dealContractMapper.findCountByPlanCode(planCode);
	}

	@Override
	public List<DealContract> find(Map<String, Object> map) {
		return dealContractMapper.find(map);
	}

	@Override
	public DealContract getById(Long id) {
		return dealContractMapper.getById(id);
	}

	@Override
	public int updateStatus(DealContract dealContract) {
		
		return dealContractMapper.updateStatus(dealContract);
	}

	@Override
	public Long findIdByCode(String planCode) {
		return planMapper.findIdByCode(planCode);
	}
	
	public String createMallReceipt(Long dealContractId, String fileName, Integer sealPageNum, Float sealAbsoluteX, Float sealAbsoluteY) {
		String pdfRresultPath = null;//生成的pdf文件路径
		/*String resultPath = null;//生成的水印pdf文件路径
		String ftlContent = null;//模板内容
		DealContract data = null;//合同数据
		if (dealContractId != null) {
			DealContract dealContract = dealContractMapper.selectByPrimaryKey(dealContractId);
			String contractCode = dealContract.getSn();
			Long bizRalationId = dealContract.getBizRelationId();
			if (bizRalationId != null) {
				
			}
			
		}else {
			return null;
		}
		
		//获取模板内容
		DealContractTpl contractTpl = dealContractTplService.getUsableByType(14);
		if (contractTpl != null) {
			ftlContent = contractTpl.getContent();
		}
		
		if (StringUtils.isEmpty(ftlContent)) {
			return null;
		}
		if (StringUtils.isEmpty(fileName)) {
			fileName = UUID.randomUUID() + ".pdf";
		}
		try {
			pdfRresultPath = pdfCreateUtil.createPDFByFtlContent(data, fileName, ftlContent);
			if (StringUtils.isNotBlank(pdfRresultPath)) {
				//设置输出路径
	            String uploadPath = Constant.UPLOAD_BASE_PATH + "/contract" + Constant.FILE_UPLOAD_PATH;
	            File destFile = new File(uploadPath);
				if (!destFile.getParentFile().exists()) {
					destFile.getParentFile().mkdirs();
				}
				String destPath = uploadPath + UUID.randomUUID()+ "_" + dealContractId + "_水印.pdf";
				resultPath = FreemarkerUtils.process(destPath, null);
	            log.info("设置水印pdf输出路径：" + resultPath);
	            
				WaterMark.waterMark(pdfRresultPath, resultPath, "山东省省级政府采购网上商城", null, sealPageNum, sealAbsoluteX, sealAbsoluteY);
				//删除pdf文件
				File file = new File(pdfRresultPath);
				if(file.exists()&&file.isFile()){
					file.delete();
				}
			}
			log.info(resultPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
				
		return null;
	}
	
}
