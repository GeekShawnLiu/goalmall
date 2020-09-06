package www.tonghao.platform.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class Pays extends BaseEntity {

    /**
     * 合同id
     */
	@ApiModelProperty("合同id")
    @Column(name = "contract_id")
    private String contractId;

    /**
     * 支付批次号
     */
	@ApiModelProperty("支付批次号")
    @Column(name = "disburse_num")
    private Integer disburseNum;

    /**
     * 支付金额
     */
	@ApiModelProperty("支付金额")
    private BigDecimal amount;

    /**
     * 支付时间
     */
	@ApiModelProperty("支付时间")
    @Column(name = "pay_date")
    private String payDate;

    /**
     * 是否验收 0：不验收 1：需要验收 2：已验收未生成支付申请
     */
	@ApiModelProperty("是否验收 0：不验收 1：需要验收 2：已验收未生成支付申请")
    @Column(name = "is_acceptance")
    private Integer isAcceptance;

    /**
     * 付款比例
     */
	@ApiModelProperty("付款比例")
    @Column(name = "pay_scale")
    private Float payScale;

    /**
     * 本批次金额
     */
	@ApiModelProperty("本批次金额")
    @Column(name = "total_sum")
    private BigDecimal totalSum;

    /**
     * 本批次金额（预算内）
     */
	@ApiModelProperty("本批次金额（预算内）")
    @Column(name = "total_sum01")
    private BigDecimal totalSum01;

    /**
     * 本批次金额（预算外）
     */
	@ApiModelProperty("本批次金额（预算外）")
    @Column(name = "total_sum02")
    private BigDecimal totalSum02;

    /**
     * 本批次金额（其它）
     */
	@ApiModelProperty("本批次金额（其它）")
    @Column(name = "total_sum03")
    private BigDecimal totalSum03;

    /**
     * 业务年度
     */
	@ApiModelProperty("业务年度")
    @Column(name = "bdg_year")
    private Integer bdgYear;

    /**
     * 区划
     */
	@ApiModelProperty("区划")
    private Integer region;


    /**
     * 获取合同id
     *
     * @return contract_id - 合同id
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * 设置合同id
     *
     * @param contractId 合同id
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    /**
     * 获取支付批次号
     *
     * @return disburse_num - 支付批次号
     */
    public Integer getDisburseNum() {
        return disburseNum;
    }

    /**
     * 设置支付批次号
     *
     * @param disburseNum 支付批次号
     */
    public void setDisburseNum(Integer disburseNum) {
        this.disburseNum = disburseNum;
    }

    /**
     * 获取支付金额
     *
     * @return amount - 支付金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置支付金额
     *
     * @param amount 支付金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取支付时间
     *
     * @return pay_date - 支付时间
     */
    public String getPayDate() {
        return payDate;
    }

    /**
     * 设置支付时间
     *
     * @param payDate 支付时间
     */
    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    /**
     * 获取是否验收 0：不验收 1：需要验收 2：已验收未生成支付申请
     *
     * @return is_acceptance - 是否验收 0：不验收 1：需要验收 2：已验收未生成支付申请
     */
    public Integer getIsAcceptance() {
        return isAcceptance;
    }

    /**
     * 设置是否验收 0：不验收 1：需要验收 2：已验收未生成支付申请
     *
     * @param isAcceptance 是否验收 0：不验收 1：需要验收 2：已验收未生成支付申请
     */
    public void setIsAcceptance(Integer isAcceptance) {
        this.isAcceptance = isAcceptance;
    }

    /**
     * 获取付款比例
     *
     * @return pay_scale - 付款比例
     */
    public Float getPayScale() {
        return payScale;
    }

    /**
     * 设置付款比例
     *
     * @param payScale 付款比例
     */
    public void setPayScale(Float payScale) {
        this.payScale = payScale;
    }

    /**
     * 获取本批次金额
     *
     * @return total_sum - 本批次金额
     */
    public BigDecimal getTotalSum() {
        return totalSum;
    }

    /**
     * 设置本批次金额
     *
     * @param totalSum 本批次金额
     */
    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    /**
     * 获取本批次金额（预算内）
     *
     * @return total_sum01 - 本批次金额（预算内）
     */
    public BigDecimal getTotalSum01() {
        return totalSum01;
    }

    /**
     * 设置本批次金额（预算内）
     *
     * @param totalSum01 本批次金额（预算内）
     */
    public void setTotalSum01(BigDecimal totalSum01) {
        this.totalSum01 = totalSum01;
    }

    /**
     * 获取本批次金额（预算外）
     *
     * @return total_sum02 - 本批次金额（预算外）
     */
    public BigDecimal getTotalSum02() {
        return totalSum02;
    }

    /**
     * 设置本批次金额（预算外）
     *
     * @param totalSum02 本批次金额（预算外）
     */
    public void setTotalSum02(BigDecimal totalSum02) {
        this.totalSum02 = totalSum02;
    }

    /**
     * 获取本批次金额（其它）
     *
     * @return total_sum03 - 本批次金额（其它）
     */
    public BigDecimal getTotalSum03() {
        return totalSum03;
    }

    /**
     * 设置本批次金额（其它）
     *
     * @param totalSum03 本批次金额（其它）
     */
    public void setTotalSum03(BigDecimal totalSum03) {
        this.totalSum03 = totalSum03;
    }

    /**
     * 获取业务年度
     *
     * @return bdg_year - 业务年度
     */
    public Integer getBdgYear() {
        return bdgYear;
    }

    /**
     * 设置业务年度
     *
     * @param bdgYear 业务年度
     */
    public void setBdgYear(Integer bdgYear) {
        this.bdgYear = bdgYear;
    }

    /**
     * 获取区划
     *
     * @return region - 区划
     */
    public Integer getRegion() {
        return region;
    }

    /**
     * 设置区划
     *
     * @param region 区划
     */
    public void setRegion(Integer region) {
        this.region = region;
    }
}