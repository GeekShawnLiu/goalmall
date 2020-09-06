package www.tonghao.platform.entity;

import javax.persistence.*;

import www.tonghao.service.common.base.BaseEntity;


@Table(name = "interface_logs")
public class InterfaceLogs extends BaseEntity {

    @Column(name = "json_content")
    private String jsonContent;

    @Column(name = "created_at")
    private String createdAt;

    private String token;

    private String url;

    private String code;


    /**
     * @return json_content
     */
    public String getJsonContent() {
        return jsonContent;
    }

    /**
     * @param jsonContent
     */
    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    /**
     * @return created_at
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }
}