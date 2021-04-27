package www.tonghao;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableZuulProxy
@EnableEurekaClient
public class GatewayApplication  implements CommandLineRunner{
	
	@Value("${spring.profiles.active}")
	private String active;
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		if("pro".equals(active) || "test".equals(active)){
			factory.setLocation("/data/tmp");
		}else{
			factory.setLocation("E:/tmp");
		}
		return factory.createMultipartConfig();
	}
	@Override
	public void run(String... args) throws Exception {
        System.out.println("petrochina-gateway 启动成功，启动的类型是"+active);
		
	}
}
