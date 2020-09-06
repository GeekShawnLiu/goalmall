package www.tonghao.service.common.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.tonghao.common.utils.DateUtilEx;
import www.tonghao.common.warpper.PriceTrend;
import www.tonghao.service.common.base.impl.BaseServiceImpl;
import www.tonghao.service.common.entity.ProductLogs;
import www.tonghao.service.common.mapper.ProductLogsMapper;
import www.tonghao.service.common.service.ProductLogsService;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;

@Service("productLogsService")
public class ProductLogsServiceImpl extends BaseServiceImpl<ProductLogs> implements ProductLogsService {

	@Autowired
	private ProductLogsMapper productLogsMapper;
	
	@Override
	public List<PriceTrend> getPriceTrend(Long pid) {
		Date date = new Date();
		List<PriceTrend> result = Lists.newArrayList();
		
		ProductLogs entity = new ProductLogs();
		entity.setProductId(pid);
		entity.setType(1);
		Map<String,Object> queryfilter = Maps.newHashMap();
		queryfilter.put("startDate", DateUtilEx.getYearStartTime());
		queryfilter.put("endDate", DateUtilEx.format(date, "yyyy-MM-dd"));
		queryfilter.put("orderByCondition", "tb.created_at desc");
		List<ProductLogs> list = productLogsMapper.findListByEntity(entity);
		if(list!=null&&list.size()>0){
			String month = DateUtilEx.format(date, "MM");
			
			int monthInt = Integer.parseInt(month);
			PriceTrend priceTrend = null;
			for(int i=1;i<=monthInt;i++){
				priceTrend = new PriceTrend();
				String monthValue = i<10?"0"+i:""+i;
				priceTrend.setMonth(monthValue+"月");
				priceTrend.setPrice(getMonthLastPrice(month, list));
				result.add(priceTrend);
			}
		}
		
		return result;
	}
	
	public BigDecimal getMonthLastPrice(String month,List<ProductLogs> list){
		for(ProductLogs item:list){
			if(item.getCreatedAt().split("-")[1].equals(month)){
				return new BigDecimal(item.getAfterValue());
			}
		}
		return BigDecimal.ZERO;
	}

}
