package www.tonghao.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:dict.properties")
@ConfigurationProperties(prefix = "enumdict")
public class OrderStatusConfig {

	private Map<String, String> orderstatus = new HashMap<>();

	public Map<String, String> getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Map<String, String> orderstatus) {
		this.orderstatus = orderstatus;
	}
}
