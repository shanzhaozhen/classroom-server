package com.shanzhaozhen.classroom.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 发送get请求工具
     * @param url
     * @return
     */
    public static String sendGet(String url) {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);

        String result = "";

        try {
            result = getResponseResult(httpClient, httpGet);
            logger.info("请求地址：" + url + "，返回数据：" + result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("get请求，地址：" + url + " 失败:" + url, e);
        }
        return result;
    }

    /**
     * 发送post请求工具
     * @param url
     * @param paramMap
     * @return
     */
    public static String sendPost(String url, Map<String, Object> paramMap) {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);

        String result = "";

        try {
            //设置添加post数据
            List<NameValuePair> postData = new ArrayList<>();
            for (String key : paramMap.keySet()) {
                postData.add(new BasicNameValuePair(key, (String) paramMap.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(postData, "UTF-8"));
            result = getResponseResult(httpClient, httpPost);
            logger.info("请求地址：" + url + "，返回数据：" + result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("post请求，地址：" + url + " 失败:" + url, e);
        }

        return result;
    }

    public static String getResponseResult(CloseableHttpClient httpClient, HttpRequestBase httpRequestBase) throws IOException {
        String result;
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(httpRequestBase);
        //判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            //获取响应实体
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } else {
//            JSONObject jsonObject = new JSONObject();
            Map<String, Object> map = new HashMap<>();
            map.put("code", response.getStatusLine().getStatusCode());
            map.put("reason", response.getStatusLine().getReasonPhrase());
            map.put("protocolVersion", response.getStatusLine().getProtocolVersion());
            result = JSONObject.toJSONString(map);
        }
        return result;
    }

}
