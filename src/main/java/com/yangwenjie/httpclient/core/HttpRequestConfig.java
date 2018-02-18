package com.yangwenjie.httpclient.core;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * http client 请求配置类
 * @author Yang WenJie
 * @date 2018/2/10 下午1:56
 */
public class HttpRequestConfig {


    private HttpRequestConfig(){

    }

    public static HttpRequestConfig create() {
        return new HttpRequestConfig();
    }

    /**
     * httpClient 对象
     */
    private HttpClient httpClient;

    /**
     * requestMethod 请求方法类型
     */
    private HttpRequestMethod requestMethod;

    /**
     * 用于cookie操作
     */
    private HttpContext context;

    /**
     *  实体类型
     */
    private HttpEntityType httpEntityType = HttpEntityType.ENTITY_FORM;

    /**
     * 请求参数
     */
    private Map<String, Object> paramMap = new LinkedHashMap<>();

    /**
     * 请求 header
     */
    private Map<String, Header> headerMap = new LinkedHashMap<>();

    /**
     * 以json格式作为输入参数
     */
    private String jsonParam;

    /**
     * 字节数组方式作为输入参数
     */
    private byte[] bytes;

    /**
     * 使用ssl
     */
    private boolean useSSL = false;

    /**
     * 请求编码类型
     */
    private String requestCharset = "UTF-8";

    /**
     * 响应编码类型
     */
    private String responseCharset = "UTF-8";

    /**
     * 请求url ，解决多线程处理时，url被覆盖问题
     */
    private static final ThreadLocal<String> urls = new ThreadLocal<String>();

    /**
     * 解决多线程下载时，strean被close的问题
     */
    private static final ThreadLocal<OutputStream> outs = new ThreadLocal<OutputStream>();

    /**
     * @param httpClient	HttpClient对象
     * @return 返回当前对象
     */
    public HttpRequestConfig client(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    /**
     * @param url	资源url
     * @return	返回当前对象
     */
    public HttpRequestConfig url(String url) {
        urls.set(url);
        return this;
    }

    /**
     * @param out	输出流对象
     * @return	返回当前对象
     */
    public HttpRequestConfig out(OutputStream out) {
        outs.set(out);
        return this;
    }

    /**
     * @param headerMap	Header头信息
     * @return	返回当前对象
     */
    public HttpRequestConfig headers(Map<String, Header> headerMap) {
        this.headerMap = headerMap;
        return this;
    }

    /**
     * 添加单个header
     * @param name
     * @param value
     * @return
     */
    public HttpRequestConfig addHeader(String name, String value) {
        this.headerMap.put(name, new BasicHeader(name, value));
        return this;
    }

    /**
     * @param requestMethod	请求方法
     * @return	返回当前对象
     */
    public HttpRequestConfig requestMethod(HttpRequestMethod requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    /**
     * @param context	cookie操作相关
     * @return	返回当前对象
     */
    public HttpRequestConfig context(HttpContext context) {
        this.context = context;
        return this;
    }

    /**
     *
     * @param httpEntityType httpEntity 类型
     * @return 返回当前对象
     */
    public HttpRequestConfig httpEntityType (HttpEntityType httpEntityType) {
        this.httpEntityType = httpEntityType;
        return this;
    }

    /**
     * @param paramMap	传递参数
     * @return	返回当前对象
     */
    public HttpRequestConfig paramMap(Map<String, Object> paramMap) {
        if(this.paramMap == null || paramMap == null ){
            this.paramMap = paramMap;
        }else {
            this.paramMap.putAll(paramMap);
        }
        return this;
    }

    /**
     * 设置单个参数
     * @param name
     * @param value
     * @return
     */
    public HttpRequestConfig addParam(String name,Object value) {
        this.paramMap.put(name, value);
        return this;
    }

    /**
     * @param jsonParam	以json格式字符串作为参数
     * @return	返回当前对象
     */
    public HttpRequestConfig json(String jsonParam) {
        this.jsonParam = jsonParam;
        paramMap = new HashMap<>();
        return this;
    }

    /**
     *
     * @param bytes 字节数组作为参数
     * @return
     */
    public HttpRequestConfig bytes(byte[] bytes) {
        this.bytes = bytes;
        paramMap = new HashMap<>();
        return this;
    }

    /**
     * 使用 ssl，默认关闭
     * @return
     */
    public HttpRequestConfig useSSL() {
        this.useSSL = true;
        return this;
    }

    /**
     * 请求编码
     * @param requestCharset
     * @return
     */
    public HttpRequestConfig requestCharset(String requestCharset) {
        this.requestCharset = requestCharset;
        return this;
    }

    /**
     * 响应编码
     * @param responseCharset
     * @return
     */
    public HttpRequestConfig responseCharset(String responseCharset) {
        this.responseCharset = responseCharset;
        return this;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public HttpRequestMethod getRequestMethod() {
        return requestMethod;
    }

    public HttpContext getContext() {
        return context;
    }

    public HttpEntityType getHttpEntityType() {
        return httpEntityType;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public Map<String, Header> getHeaderMap() {
        return headerMap;
    }

    public Header[] getHeaders() {
        return headerMap.values().toArray(new Header[headerMap.size()]);
    }

    public String getJsonParam() {
        return jsonParam;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public String getRequestCharset() {
        return requestCharset;
    }

    public String getResponseCharset() {
        return responseCharset;
    }

    public static String getUrl() {
        return urls.get();
    }

    public static OutputStream getOut() {
        return outs.get();
    }

    public HttpEntity getEntity() {
        HttpEntity entity = null;
        switch (this.httpEntityType) {
            case ENTITY_STRING:
                entity = new StringEntity(this.getJsonParam(), this.getRequestCharset());
                break;
            case ENTITY_BYTES:
                entity = new ByteArrayEntity(getBytes());
                break;
            case ENTITY_FORM:
                List<NameValuePair> pairList = new ArrayList<>(paramMap.size());
                for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                            .getValue().toString());
                    pairList.add(pair);
                }

                entity = new UrlEncodedFormEntity(pairList, Charset.forName(getRequestCharset()));
                break;
                default:
                    break;

        }
        return entity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HttpRequestConfig{");
        sb.append("httpClient=").append(httpClient);
        sb.append(", requestMethod=").append(requestMethod);
        sb.append(", paramMap=").append(paramMap);
        sb.append(", headerMap=").append(headerMap);
        sb.append(", jsonParam='").append(jsonParam).append('\'');
        sb.append(", useSSL=").append(useSSL);
        sb.append(", requestCharset='").append(requestCharset).append('\'');
        sb.append(", responseCharset='").append(responseCharset).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
