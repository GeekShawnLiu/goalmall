package www.tonghao.platform.entity;

import java.math.BigDecimal;

public class SupplierIntegrityVo {
 
	//供应商id
   private Long id;
   //供应商名称
   private String name;
   //供应商信用分
   private BigDecimal score;
   //扣分次数
   private int deductingScoreNum;
   //暂停次数
   private int tranNum;
   //暂停时间
   private String createAt;
   //恢复时间
   private String recoverAt;
   //状态
   private int isTransaction;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public BigDecimal getScore() {
	return score;
}
public void setScore(BigDecimal score) {
	this.score = score;
}
public int getDeductingScoreNum() {
	return deductingScoreNum;
}
public void setDeductingScoreNum(int deductingScoreNum) {
	this.deductingScoreNum = deductingScoreNum;
}
public int getTranNum() {
	return tranNum;
}
public void setTranNum(int tranNum) {
	this.tranNum = tranNum;
}
public String getCreateAt() {
	return createAt;
}
public void setCreateAt(String createAt) {
	this.createAt = createAt;
}
public String getRecoverAt() {
	return recoverAt;
}
public void setRecoverAt(String recoverAt) {
	this.recoverAt = recoverAt;
}
public int getIsTransaction() {
	return isTransaction;
}
public void setIsTransaction(int isTransaction) {
	this.isTransaction = isTransaction;
}
   
   
   
}
