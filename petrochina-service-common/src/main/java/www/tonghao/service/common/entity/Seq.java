package www.tonghao.service.common.entity;

import www.tonghao.service.common.base.BaseEntity;


public class Seq extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * sequence名称
     */
    private String name;

    /**
     * 最大id
     */
    private Long max;

    /**
     * 生成序列后的长度,以0补全
     */
    private Integer length;

    /**
     * 增长的长度
     */
    private Integer next;

    /**
     * 规则以###max_id###做为替换
     */
    private String rules;


    /**
     * 获取sequence名称
     *
     * @return name - sequence名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置sequence名称
     *
     * @param name sequence名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取最大id
     *
     * @return max - 最大id
     */
    public Long getMax() {
        return max;
    }

    /**
     * 设置最大id
     *
     * @param max 最大id
     */
    public void setMax(Long max) {
        this.max = max;
    }

    /**
     * 获取生成序列后的长度,以0补全
     *
     * @return length - 生成序列后的长度,以0补全
     */
    public Integer getLength() {
        return length;
    }

    /**
     * 设置生成序列后的长度,以0补全
     *
     * @param length 生成序列后的长度,以0补全
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * 获取增长的长度
     *
     * @return next - 增长的长度
     */
    public Integer getNext() {
        return next;
    }

    /**
     * 设置增长的长度
     *
     * @param next 增长的长度
     */
    public void setNext(Integer next) {
        this.next = next;
    }

    /**
     * 获取规则以###max_id###做为替换
     *
     * @return rules - 规则以###max_id###做为替换
     */
    public String getRules() {
        return rules;
    }

    /**
     * 设置规则以###max_id###做为替换
     *
     * @param rules 规则以###max_id###做为替换
     */
    public void setRules(String rules) {
        this.rules = rules;
    }
}