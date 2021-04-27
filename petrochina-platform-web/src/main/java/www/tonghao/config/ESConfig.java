//package www.tonghao.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import www.tonghao.common.es.ElasticSearchClient;
//
//
//@Configuration
//public class ESConfig {
//
//	@Value("${spring.es.host}")
//	private String hostName;
//
//	@Value("${spring.es.port}")
//	private int port;
//
//	@Value("${spring.es.clusterName}")
//	private String clusterName;
//
//	@Value("${spring.es.enable}")
//	private boolean enable;
//
//	@Bean
//	public ElasticSearchClient elasticSearchClient(){
//		ElasticSearchClient elasticSearchClient = new ElasticSearchClient(hostName, port, clusterName);
//		elasticSearchClient.setEnable(enable);
//		return elasticSearchClient;
//	}
//}
