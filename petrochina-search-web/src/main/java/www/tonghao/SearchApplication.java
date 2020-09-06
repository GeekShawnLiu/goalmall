package www.tonghao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "www.tonghao")
@EnableEurekaClient
public class SearchApplication implements CommandLineRunner {
	@Value("${spring.profiles.active}")
	private String active;
	
	public static void main(String[] args) {
		SpringApplication.run(SearchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        System.out.println("petrochina-search-web 启动成功，启动的类型是"+active);
		
	}

}
