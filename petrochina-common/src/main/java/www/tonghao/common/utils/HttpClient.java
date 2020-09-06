package www.tonghao.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http客户端工具
 * @author developer001
 *
 */
public class HttpClient {
	private static CloseableHttpClient httpClient = null;
	public static final String CHARSET = "UTF-8";

	static {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000)
				.setSocketTimeout(15000).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config)
				.build();
	}
	
	/**
	 * 获取httpClient
	 * @return
	 */
	public static CloseableHttpClient getHttpClient(){
		return httpClient;
	}

	public static String doGet(String url, Map<String, String> params) {
		return doGet(url, params, null,"UTF-8");
	}
	
	public static String doGet(String url, Map<String, String> params,Map<String,String> headers) {
		return doGet(url, params, headers,"UTF-8");
	}

	public static String doPost(String url, Map<String, String> params) {
		return doPost(url, params,null, "UTF-8");
	}
	
	public static String doPost(String url, Map<String, String> params,Map<String,String> headers) {
		return doPost(url, params,headers, "UTF-8");
	}

	public static String doProxyPost(String url, Map<String, String> params,
			HttpHost proxy) {
		return doProxyPost(url, params, "UTF-8", proxy);
	}

	@SuppressWarnings("rawtypes")
	public static String doGet(String url, Map<String, String> params,
			Map<String,String> headers,String charset) {
		if (StringUtils.isBlank(url))
			return null;
		try {
			if ((params != null) && (!params.isEmpty())) {
				List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(params.size());
				for (Map.Entry entry : params.entrySet()) {
					String value = (String) entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair((String) entry
								.getKey(), value));
					}
				}
				url = url
						+ "?"
						+ EntityUtils.toString(new UrlEncodedFormEntity(pairs,
								charset));
			}
			HttpGet httpGet = new HttpGet(url);
			if(headers!=null&&!headers.isEmpty()){
				for (Map.Entry<String,String> entry : headers.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						httpGet.addHeader(entry.getKey(), entry.getValue());
					}
				}
			}
			CloseableHttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :"
						+ statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String doPost(String url, Map<String, String> params,
			Map<String,String> headers,String charset) {
		if (StringUtils.isBlank(url))
			return null;
		try {
			List<BasicNameValuePair> pairs = null;
			if ((params != null) && (!params.isEmpty())) {
				pairs = new ArrayList<BasicNameValuePair>(params.size());
				for (Map.Entry<String,String> entry : params.entrySet()) {
					String value =  entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair((String) entry
								.getKey(), value));
					}
				}
			}
			HttpPost httpPost = new HttpPost(url);
			if(headers!=null&&!headers.isEmpty()){
				for (Map.Entry<String,String> entry : headers.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						httpPost.addHeader(entry.getKey(), entry.getValue());
					}
				}
			}
			if ((pairs != null) && (pairs.size() > 0)) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
			}
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :"
						+ statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static String doProxyPost(String url, Map<String, String> params,
			String charset, HttpHost proxy) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		CloseableHttpResponse response = null;
		try {
			List<BasicNameValuePair> pairs = null;
			if ((params != null) && (!params.isEmpty())) {
				pairs = new ArrayList<BasicNameValuePair>(params.size());
				for (Map.Entry entry : params.entrySet()) {
					String value = (String) entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair((String) entry
								.getKey(), value));
					}
				}
			}
			RequestConfig config = RequestConfig.custom().setProxy(proxy)
					.build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(config);

			if ((pairs != null) && (pairs.size() > 0)) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
			}

			httpClient = HttpClientBuilder.create()
					.setDefaultRequestConfig(config).build();
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :"
						+ statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}
