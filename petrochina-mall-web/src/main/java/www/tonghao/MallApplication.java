package www.tonghao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "www.tonghao")
@EnableEurekaClient
public class MallApplication implements CommandLineRunner {
    @Value("${spring.profiles.active}")
    private String active;

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("petrochina-mall-web 启动成功，启动的类型是" + active);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        if ("pro".equals(active) || "test".equals(active)) {
            factory.setLocation("/data/tmp");
        } else {
            factory.setLocation("E:/tmp");
        }
        return factory.createMultipartConfig();
    }
}
