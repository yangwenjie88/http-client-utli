package com.httpclient.test;

import com.yangwenjie.httpclient.HttpClientUtils;
import com.yangwenjie.httpclient.core.HttpEntityType;
import com.yangwenjie.httpclient.core.HttpRequestConfig;
import com.yangwenjie.httpclient.core.HttpRequestResult;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Yang WenJie
 * @date 2018/2/10 下午9:26
 */
public class HttpClientDemo {

    @Test
    public void get() throws IOException{
        HttpRequestConfig config = HttpRequestConfig.create().url("http://localhost:8090/peoples/");
        HttpRequestResult result = HttpClientUtils.get(config);
        System.out.println(result.getResponseText());
    }

    @Test
    public void post() throws IOException {
        HttpRequestConfig config = HttpRequestConfig.create().url("http://localhost:8090/peoples/")
                                    .addParam("id",2)
                                    .addParam("name","yangwenjie2")
                                    .addParam("age",19);
        HttpRequestResult result = HttpClientUtils.post(config);
        System.out.println(result.getResponseText());

    }

    @Test
    public void postJson() throws IOException {
        HttpRequestConfig config = HttpRequestConfig.create().url("http://localhost:8090/peoples/body")
                .httpEntityType(HttpEntityType.ENTITY_STRING)
                .json("{\"name\",\"杨文杰\"}");
        HttpRequestResult result = HttpClientUtils.post(config);
        System.out.println(result.getResponseText());
    }

    @Test
    public void postByte() throws IOException {
        HttpRequestConfig config = HttpRequestConfig.create().url("http://localhost:8090/peoples/body")
                .httpEntityType(HttpEntityType.ENTITY_BYTES)
                .bytes("{\"name\",\"杨文杰\"}".getBytes("UTF-8"));
        HttpRequestResult result = HttpClientUtils.post(config);
        System.out.println(result.getResponseText());
    }

    @Test
    public void postInputStream() throws IOException {
        HttpRequestConfig config = HttpRequestConfig.create().url("http://localhost:8090/peoples/inputStream")
                .httpEntityType(HttpEntityType.ENTITY_STRING)
                .json("{\"name\",\"杨文杰\"}");
        HttpRequestResult result = HttpClientUtils.post(config);
        System.out.println(result);
    }

    @Test
    public void down() throws IOException {
        String imgUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png"; //百度logo
        File file = new File("/Users/yangwenjie/test/baidu.png");
        HttpClientUtils.down(HttpRequestConfig.create().url(imgUrl).out(new FileOutputStream(file)));
        if (file.exists()) {
            System.out.println("图片下载成功了！存放在：" + file.getPath());
        }

    }


}
