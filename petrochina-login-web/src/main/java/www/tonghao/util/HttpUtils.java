package www.tonghao.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 发送http请求
 * @Author: zbf
 * @Date: 2019/5/8 14:45
 */
public class HttpUtils {

    /**
     * 发送post请求
     * @param url 请求接口地址
     * @param json  参数
     * @param headerMap header中需要加上的信息
     * @return
     */
    public static String doPost(String url, JSONObject json, Map<String, String> headerMap){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        try {
            post.addHeader("Content-Type","application/json; charset=UTF-8");
            post.setHeader("Accept","application/json");

            if(headerMap != null && headerMap.size() > 0)
                for(Map.Entry<String, String> entry : headerMap.entrySet())
                    post.addHeader(entry.getKey(), entry.getValue());

            post.setEntity(new StringEntity(json.toString(), Charset.forName("UTF-8")));
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity(),"UTF-8");// 返回json格式：
                return result;
            }
        } catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            // 释放连接
                try {
                    httpclient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
        }
        return "";
    }


}
