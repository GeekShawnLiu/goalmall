package www.tonghao.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	private CorsConfiguration buildConfig() {
		CorsConfiguration configuration=new CorsConfiguration();
		// 允许cookie跨域
		configuration.setAllowCredentials(true);
        // 允许任何域名使用
		configuration.addAllowedOrigin("*");
        // 允许任何头
		configuration.addAllowedHeader("*");
        // 允许任何方法（post、get等）
		configuration.addAllowedMethod("*");
        // 设置跨域缓存时间，单位为秒
		configuration.setMaxAge(300L);

        return configuration;
	}
	
	@Bean
	public CorsFilter corsFilter() {
		 final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		 source.registerCorsConfiguration("/**", buildConfig());
		 return new CorsFilter(source);
	}
	
}
