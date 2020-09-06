package www.tonghao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
@EnableEurekaServer
public class RegistryApplication implements CommandLineRunner {

	@Value("${spring.profiles.active}")
	private String active;
	
	public static void main(String[] args) {
		SpringApplication.run(RegistryApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
        System.out.println("petrochina-registry 启动成功，启动的类型是"+active);
		
	}
	
}
