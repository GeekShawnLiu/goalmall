package www.tonghao.common.utils;


import java.math.BigDecimal;
import java.util.List;

import www.tonghao.common.warpper.PriceRangeModel;

import com.google.common.collect.Lists;
/**
 * 价格工具
 * @author developer001
 *
 */
public class PriceUtil {
	
	/**
	 * 默认价格范围大小
	 */
	public static final int DEFALUT_RANGE_SIZE = 5;
	
	private PriceUtil(){}
	
	/**
	 * 汇总计算价格范围
	 * @param priceRanges
	 * @return
	 */
	public static PriceRangeModel calRange(String[] priceRanges){
		int min = 0;
		int max = 0; //0 表示无穷大
		boolean isMinZreo = false;//最小值是否为0
		boolean isHasMax = true; //是否有最大值 false表示无穷大
		for(String range:priceRanges){
			String[] ranges = range.split("_");
			int begin = Integer.parseInt(ranges[0]);
			int end = Integer.parseInt(ranges[1]);
			
			if(begin==0){
				isMinZreo = true;
			}else{
				if(min==0||begin<min){
					min = begin;
				}
			}
			
			if(end!=0){
				if(end>max){
					max = end;
				}
			}else{
				isHasMax = false;
			}
		}
		if(isMinZreo){
			min = 0;
		}
		if(!isHasMax){
			max = 0;
		}
		return new PriceRangeModel(min, max);
	}
	
	/**
	 * 动态获取价格区间
	 * @param maxPrice 最大价格
	 * @return
	 */
	public static List<PriceRangeModel> getRanges(BigDecimal maxPrice){
		return getRanges(maxPrice,DEFALUT_RANGE_SIZE);
	}
	
	/**
	 * 动态获取价格区间
	 * @param maxPrice 最大价格
	 * @param s 区间大小 返回s+1 
	 * @return
	 */
	public static List<PriceRangeModel> getRanges(BigDecimal maxPrice,int s){
		if(maxPrice==null){
			return null;
		}
		int max = maxPrice.intValue();
		int avg = max/s;
		int r = 0;
		if(avg<100){
			r = 10;
		}else if(avg>=100&&avg<1000){
			r = 100;
		}else if(avg>=1000&&avg<10000){
			r = 1000;
		}else{
			r = 10000;
		}
		return getRanges(r, s);
	}
	
	/**
	 * 动态获取价格区间
	 * @param r 价格系数
	 * @param s 区间大小 返回s+1 
	 * @return
	 */
	public static List<PriceRangeModel> getRanges(int r,int s){
		List<PriceRangeModel> ranges = Lists.newArrayList();
		int end = 0;
		for(int i=0;i<s;i++){
			end = ((i+1)*r);
			ranges.add(new PriceRangeModel(i*r, end));
		}
		if(end!=0){
			ranges.add(new PriceRangeModel(end, 0));
		}
		return ranges;
	}
	
	public static void main(String[] args) {
		System.out.println(JsonUtil.toJson(calRange(new String[]{"100_200"})));
	}
}
