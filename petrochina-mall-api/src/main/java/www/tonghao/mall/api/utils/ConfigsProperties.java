package www.tonghao.mall.api.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.google.common.collect.Lists;

@Configuration
public class ConfigsProperties implements EnvironmentPostProcessor, Ordered{

	@SuppressWarnings("rawtypes")
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		MutablePropertySources propertySources = environment.getPropertySources();
        String[] profiles = environment.getActiveProfiles();
        Properties props = getConfig(profiles);
        propertySources.addLast(new PropertiesPropertySource("thirdEnv", props));
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource.getSource() instanceof Map) {
                Map map = (Map)propertySource.getSource();
                for (Object key : map.keySet()) {
                    String keyStr = key.toString();
                    Object value = map.get(key);
                    PropertiesUtil.getProperties().put(keyStr, value.toString());
                }
            }
        }
	}
	
	@Override
	public int getOrder() {
		return ConfigFileApplicationListener.DEFAULT_ORDER + 1;
	}
	
	// 加载配置文件
    private Properties getConfig(String[] profiles) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> resouceList = Lists.newArrayList();
        addResources(resolver, resouceList, "classpath*:config/*.properties");
        if (profiles != null) {
            for (String p : profiles) {
                if (StringUtils.isNotEmpty(p)) {
                    p = p + "/";
                }
                addResources(resolver, resouceList, "classpath*:config/" + p + "*.properties");
            }
        }
        try {
            PropertiesFactoryBean config = new PropertiesFactoryBean();
            config.setLocations(resouceList.toArray(new Resource[]{}));
            config.afterPropertiesSet();
            return config.getObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 加载配置文件
    private void addResources(PathMatchingResourcePatternResolver resolver, List<Resource> resouceList, String path) {
        try {
            Resource[] resources = resolver.getResources(path);
            for (Resource resource : resources) {
                resouceList.add(resource);
            }
        } catch (Exception e) {
        }
    }
	
}
