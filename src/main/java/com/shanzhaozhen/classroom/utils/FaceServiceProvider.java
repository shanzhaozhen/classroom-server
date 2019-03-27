package com.shanzhaozhen.classroom.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class FaceServiceProvider {

    private static Logger logger = LoggerFactory.getLogger(FaceServiceProvider.class);

    @Value("${face.key}")
    private String key;

    @Value("${face.secret}")
    private String secret;

    @Value("${face.detectApi}")
    private String detectApi;

    @Value("${face.compareApi}")
    private String compareApi;

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

    public String getDetectApi() {
        return detectApi;
    }

    public String getCompareApi() {
        return compareApi;
    }

    public String getFaceToken(MultipartFile multipartFile) {

        byte[] bytes;
        try (
            InputStream inputStream = multipartFile.getInputStream()
        ) {
            bytes = IOUtils.toByteArray(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String base64 = Base64.getEncoder().encodeToString(bytes);
        Map<String, Object> map = new HashMap<>();
        map.put("api_key", key);
        map.put("api_secret", secret);
        map.put("image_base64", base64);
        String result = HttpClientUtils.sendPost(detectApi, map);

        return null;
    }

}
