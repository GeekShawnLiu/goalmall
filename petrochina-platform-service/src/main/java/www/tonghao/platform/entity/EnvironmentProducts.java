package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "environment_products")
public class EnvironmentProducts extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 品目编号
     */
    @Column(name = "pur_item_code")
    private String purItemCode;

    @Column(name = "pur_item_num")
    private String purItemNum;

    /**
     * 品目编号加名称
     */
    @Column(name = "pur_item_name")
    private String purItemName;

    @Column(name = "product_dir_type")
    private String productDirType;

    /**
     * 中国环境标志认证证书编号
     */
    @Column(name = "cert_code")
    private String certCode;

    /**
     * 有效期
     */
    @Column(name = "cert_expir")
    private String certExpir;

    /**
     * 产品型号
     */
    @Column(name = "goods_spec")
    private String goodsSpec;

    /**
     * 品牌
     */
    @Column(name = "brand_zh_name")
    private String brandZhName;

    /**
     * 企业名称
     */
    @Column(name = "manuf_name")
    private String manufName;

    /**
     * 清单期数
     */
    @Column(name = "period_no")
    private String periodNo;

    /**
     * 获取品目编号
     *
     * @return pur_item_code - 品目编号
     */
    public String getPurItemCode() {
        return purItemCode;
    }

    /**
     * 设置品目编号
     *
     * @param purItemCode 品目编号
     */
    public void setPurItemCode(String purItemCode) {
        this.purItemCode = purItemCode;
    }

    /**
     * @return pur_item_num
     */
    public String getPurItemNum() {
        return purItemNum;
    }

    /**
     * @param purItemNum
     */
    public void setPurItemNum(String purItemNum) {
        this.purItemNum = purItemNum;
    }

    /**
     * 获取品目编号加名称
     *
     * @return pur_item_name - 品目编号加名称
     */
    public String getPurItemName() {
        return purItemName;
    }

    /**
     * 设置品目编号加名称
     *
     * @param purItemName 品目编号加名称
     */
    public void setPurItemName(String purItemName) {
        this.purItemName = purItemName;
    }

    /**
     * @return product_dir_type
     */
    public String getProductDirType() {
        return productDirType;
    }

    /**
     * @param productDirType
     */
    public void setProductDirType(String productDirType) {
        this.productDirType = productDirType;
    }

    /**
     * 获取中国环境标志认证证书编号
     *
     * @return cert_code - 中国环境标志认证证书编号
     */
    public String getCertCode() {
        return certCode;
    }

    /**
     * 设置中国环境标志认证证书编号
     *
     * @param certCode 中国环境标志认证证书编号
     */
    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    /**
     * 获取有效期
     *
     * @return cert_expir - 有效期
     */
    public String getCertExpir() {
        return certExpir;
    }

    /**
     * 设置有效期
     *
     * @param certExpir 有效期
     */
    public void setCertExpir(String certExpir) {
        this.certExpir = certExpir;
    }

    /**
     * 获取产品型号
     *
     * @return goods_spec - 产品型号
     */
    public String getGoodsSpec() {
        return goodsSpec;
    }

    /**
     * 设置产品型号
     *
     * @param goodsSpec 产品型号
     */
    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    /**
     * 获取品牌
     *
     * @return brand_zh_name - 品牌
     */
    public String getBrandZhName() {
        return brandZhName;
    }

    /**
     * 设置品牌
     *
     * @param brandZhName 品牌
     */
    public void setBrandZhName(String brandZhName) {
        this.brandZhName = brandZhName;
    }

    /**
     * 获取企业名称
     *
     * @return manuf_name - 企业名称
     */
    public String getManufName() {
        return manufName;
    }

    /**
     * 设置企业名称
     *
     * @param manufName 企业名称
     */
    public void setManufName(String manufName) {
        this.manufName = manufName;
    }

    /**
     * 获取清单期数
     *
     * @return period_no - 清单期数
     */
    public String getPeriodNo() {
        return periodNo;
    }

    /**
     * 设置清单期数
     *
     * @param periodNo 清单期数
     */
    public void setPeriodNo(String periodNo) {
        this.periodNo = periodNo;
    }
}