package com.shanzhaozhen.classroom.config;

import com.alibaba.fastjson.JSONObject;
import com.shanzhaozhen.classroom.param.AccessToken;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class WechatServiceProvider {

    private static Logger logger = LoggerFactory.getLogger(WechatServiceProvider.class);

    @Value("${wechat.auth}")
    private String auth;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    @Value("${wechat.projectUrl}")
    private String projectUrl;

    @Value("${wechat.codeUrl}")
    private String codeUrl;

    @Value("${wechat.accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${wechat.refreshTokenUrl}")
    private String refreshTokenUrl;

    @Value("${wechat.userInfoUrl}")
    private String userInfoUrl;

    @Value("${wechat.code2SessionUrl}")
    private String code2SessionUrl;

    @Value("${wechat.baseScope}")
    private String baseScope;

    @Value("${wechat.userinfoScope}")
    private String userinfoScope;

    private static Map<String, String> wxtoken = new HashMap<>();

    // 获取code的请求地址
    public String getCodeUrl(String redirectUrl) {

        try {
            redirectUrl = URLEncoder.encode(projectUrl + redirectUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String redirect = String.format(this.codeUrl, appId, redirectUrl, userinfoScope);
        return redirect;
    }

    // 获取Web_access_tokenhttps的请求地址
    public String getAccessTokenUrl(String code) {

        logger.info(String.format(accessTokenUrl, appId, appSecret, code));

        return String.format(accessTokenUrl, appId, appSecret, code);
    }

    // 拉取用户信息的请求地址
    public String getUserInfoUrl(String accessToken, String openid) {
        logger.info(String.format(userInfoUrl, accessToken, openid));

        return String.format(userInfoUrl, accessToken, openid);
    }

    // 刷新token地址
    public String getRefreshTokenUrl(String refreshToken) {
        logger.info(String.format(refreshTokenUrl, appId, refreshToken));
        return String.format(refreshTokenUrl, appId, refreshToken);
    }

    // 通过code2Session方式获取openId地址
    public String getOpenIdByCode2SessionUrl(String code) {
        logger.info(String.format(code2SessionUrl, appId, appSecret, code));

        return String.format(code2SessionUrl, appId, appSecret, code);
    }


    public String getOpenId(String url) {
        String result = HttpClientUtils.sendGet(url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getString("openid");
    }

    public String getOpenIdByCode(String code) {
        return this.getOpenId(this.getAccessTokenUrl(code));
    }

    public String getOpenIdByCode2SessionAndCode(String code) {
        return this.getOpenId(this.getOpenIdByCode2SessionUrl(code));
    }

    public AccessToken getAccessToken(String url) {

        String result = HttpClientUtils.sendGet(url);
        return JSONObject.parseObject(result, AccessToken.class);
    }

    public String getOnlyAccessToken(String url) {

        String result = HttpClientUtils.sendGet(url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getString("access_token");
    }

    public Map<String, String> getAllResult(String url) {
        String result = HttpClientUtils.sendGet(url);
        Map<String, String> map = (Map) JSONObject.parseObject(result);
        return map;
    }


    /**
     * 根据有效期内存中存储微信access_token
     * @return
     */
    private synchronized Map setTokenMap() {
        //内存中没有
        if (wxtoken == null || wxtoken.isEmpty()) {
            wxtoken = new HashMap<>();
            //重建
            createToken(wxtoken);
        } else if(wxtoken.containsKey("token_yxq")){
            //过期时间字符串
            String token_yxq = wxtoken.get("token_yxq");
            //当前时间
            Calendar now = Calendar.getInstance();
            //token过期时间
            Calendar cal = Calendar.getInstance();
            Date d;
            try {
                d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(token_yxq);
                cal.setTime(d);
                //时间比较
                int ret = now.compareTo(cal);
                //token过期
                if(ret >= 0) {
                    //重新获取
                    createToken(wxtoken);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            createToken(wxtoken);
        }
        return wxtoken;
    }

    /**
     * 创建微信access_token 公众号的全局唯一票据
     * @param wxtoken
     * @return
     */
    private Map createToken(Map wxtoken) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+getAppId()+"&secret="+getAppSecret());
            //使用POST方法
            HttpResponse response = httpClient.execute(httpGet);

            //打印服务器返回的状态
            if (response.getStatusLine().getStatusCode()==200){
                // 打印返回的信息
                JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                String access_token = jsonObject.get("access_token").toString();
                if(access_token != null && !"".equals(access_token)) {
                    wxtoken.put("access_token", access_token);
                    Calendar calendar = Calendar.getInstance();
                    //有效期提前一分钟[-60]
                    calendar.add(Calendar.SECOND, Integer.parseInt(jsonObject.get("expires_in").toString())-60);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String token_yxq = sdf.format(calendar.getTime());
                    wxtoken.put("token_yxq", token_yxq);
                } else {
                    wxtoken = null;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wxtoken;
    }


    /**
     * 获取微信access_token
     * @return
     */
    public  String getToken() {
        Map map = setTokenMap();
        return (String) map.get("access_token");
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getProjectUrl() {
        return projectUrl;
    }
}
