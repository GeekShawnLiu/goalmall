package www.tonghao.mall.api.jd.attwrap;

import java.math.BigDecimal;
import java.util.List;

public class FreightAttr {
	/**
	 * 总运费 
	 */
   private BigDecimal freight;
   
   /**
	 * 基础运费 
	 */
   private BigDecimal baseFreight;
   
   /**
	 * 偏远运费 
	 */
   private BigDecimal remoteRegionFreight;
   
   /**
    * 需收取偏远运费的 sku 
    */
   private List<String> remoteSku;

public BigDecimal getFreight() {
	return freight;
}

public void setFreight(BigDecimal freight) {
	this.freight = freight;
}

public BigDecimal getBaseFreight() {
	return baseFreight;
}

public void setBaseFreight(BigDecimal baseFreight) {
	this.baseFreight = baseFreight;
}

public BigDecimal getRemoteRegionFreight() {
	return remoteRegionFreight;
}

public void setRemoteRegionFreight(BigDecimal remoteRegionFreight) {
	this.remoteRegionFreight = remoteRegionFreight;
}

public List<String> getRemoteSku() {
	return remoteSku;
}

public void setRemoteSku(List<String> remoteSku) {
	this.remoteSku = remoteSku;
}
   
   
   
}
