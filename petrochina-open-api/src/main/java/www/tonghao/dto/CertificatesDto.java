package www.tonghao.dto;

import java.io.Serializable;

public class CertificatesDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private String sku;

    /**
     * 节能证书编号
     */
    private String save_energy_cert_no;

    /**
     * 节能证书图片地址
     */
    private String save_energy_cert_image;

    /**
     * 节能证书有效期
     */
    private String save_energy_cert_indate;

    /**
     * 环保证书编号
     */
    private String environment_protect_cert_no;

    /**
     * 环保证书图片地址
     */
    private String environment_protect_cert_image;

    /**
     * 环保证书有效期
     */
    private String environment_protect_cert_indate;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSave_energy_cert_no() {
        return save_energy_cert_no;
    }

    public void setSave_energy_cert_no(String save_energy_cert_no) {
        this.save_energy_cert_no = save_energy_cert_no;
    }

    public String getSave_energy_cert_image() {
        return save_energy_cert_image;
    }

    public void setSave_energy_cert_image(String save_energy_cert_image) {
        this.save_energy_cert_image = save_energy_cert_image;
    }

    public String getSave_energy_cert_indate() {
        return save_energy_cert_indate;
    }

    public void setSave_energy_cert_indate(String save_energy_cert_indate) {
        this.save_energy_cert_indate = save_energy_cert_indate;
    }

    public String getEnvironment_protect_cert_no() {
        return environment_protect_cert_no;
    }

    public void setEnvironment_protect_cert_no(String environment_protect_cert_no) {
        this.environment_protect_cert_no = environment_protect_cert_no;
    }

    public String getEnvironment_protect_cert_image() {
        return environment_protect_cert_image;
    }

    public void setEnvironment_protect_cert_image(String environment_protect_cert_image) {
        this.environment_protect_cert_image = environment_protect_cert_image;
    }

    public String getEnvironment_protect_cert_indate() {
        return environment_protect_cert_indate;
    }

    public void setEnvironment_protect_cert_indate(String environment_protect_cert_indate) {
        this.environment_protect_cert_indate = environment_protect_cert_indate;
    }
}
