package www.tonghao.service.common.entity;


import javax.persistence.Column;
import javax.persistence.Table;

import www.tonghao.service.common.base.BaseEntity;

@Table(name="messages")
public class Messages extends BaseEntity {
	
	/**
	 * 业务标识号
	 */
	@Column(name = "msg_code")
	private String msgCode;
	/**
	 * 类别   1 订单积分消费
	 */
	@Column(name = "msg_type")
	private Integer msgType;
    /**
     * 0 未消费，1已消费
     */
	@Column(name = "msg_status")
	private Integer msgStatus;
	
	@Column(name = "create_at")
	private String createAt;
	
	@Column(name = "update_at")
	private String updateAt;

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(Integer msgStatus) {
		this.msgStatus = msgStatus;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public Messages(String msgCode, Integer msgType, Integer msgStatus,
			String createAt) {
		super();
		this.msgCode = msgCode;
		this.msgType = msgType;
		this.msgStatus = msgStatus;
		this.createAt = createAt;
	}

	public Messages() {}
}
