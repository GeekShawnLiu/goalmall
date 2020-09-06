package www.tonghao.common.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import www.tonghao.common.ApplicationContextHolder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public final class FreemarkerUtils {

	/**
	 * 不可实例化
	 */
	private FreemarkerUtils() {
	}

	/**
	 * 解析字符串模板
	 * 
	 * @param template
	 *            字符串模板
	 * @param model
	 *            数据
	 * @return 解析后内容
	 */
	public static String process(String template, Map<String, ?> model) throws IOException, TemplateException {
		Configuration configuration = null;
		ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
		if (applicationContext != null) {
			FreeMarkerConfigurer freeMarkerConfigurer = ApplicationContextHolder.getBean(FreeMarkerConfigurer.class);
			if (freeMarkerConfigurer != null) {
				configuration = freeMarkerConfigurer.getConfiguration();
			}
		}
		return process(template, model, configuration);
	}

	/**
	 * 解析字符串模板
	 * 
	 * @param template
	 *            字符串模板
	 * @param model
	 *            数据
	 * @param configuration
	 *            配置
	 * @return 解析后内容
	 */
	public static String process(String template, Map<String, ?> model, Configuration configuration) throws IOException, TemplateException {
		if (template == null) {
			return null;
		}
		if (configuration == null) {
			configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		}
		StringWriter out = new StringWriter();
		new Template("template", new StringReader(template), configuration).process(model, out);
		return out.toString();
	}


	/**
	 * 解析文件模板
	 * 
	 * @param filePath
	 *            模板文件路径
	 * @param model
	 *            数据
	 * @param configuration
	 *            配置
	 * @return 解析后内容
	 */
	public static String processFile(String filePath, Map<String, ?> model) throws IOException, TemplateException {
		if (filePath == null) {
			return null;
		}
		
		Configuration configuration = null;
		ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
		if (applicationContext != null) {
			FreeMarkerConfigurer freeMarkerConfigurer = ApplicationContextHolder.getBean(FreeMarkerConfigurer.class);
			if (freeMarkerConfigurer != null) {
				configuration = freeMarkerConfigurer.getConfiguration();
			}
		}
		if (configuration == null) {
			configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		}
    	StringBuffer buffer = new StringBuffer("");
        InputStream is = FreemarkerUtils.class.getResourceAsStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            line = reader.readLine(); // 读取下一行
        }
        String template = buffer.toString();
		StringWriter out = new StringWriter();
		new Template("template", new StringReader(template), configuration).process(model, out);
		reader.close();
		is.close();
		return out.toString();
	}
	
	/**  
	 * <p>Title: processFileByFtlContent</p>
	 * <p>Description: 根据模板内容渲染数据</p>  
	 * @author YML 
	 * @param templateContent 模板内容
	 * @param data 数据
	 * @return 渲染数据后内容
	 * @throws TemplateException
	 * @throws IOException 
	 */
	public static String processFileByFtlContent(String templateContent, Object data) throws TemplateException, IOException{
		if (StringUtils.isBlank(templateContent)) {
			return null;
		}
		Configuration configuration = null;
		ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
		if (applicationContext != null) {
			FreeMarkerConfigurer freeMarkerConfigurer = ApplicationContextHolder.getBean(FreeMarkerConfigurer.class);
			if (freeMarkerConfigurer != null) {
				configuration = freeMarkerConfigurer.getConfiguration();
			}
		}
		if (configuration == null) {
			configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		}
		StringWriter out = new StringWriter();
		new Template("template", new StringReader(templateContent), configuration).process(data, out);
		return out.toString();
	}
	
}