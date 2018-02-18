package com.yangwenjie.httpclient.core;

import org.apache.http.Header;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 响应结果
 * @author Yang WenJie
 * @date 2018/2/10 下午8:58
 */
public class HttpRequestResult {

    private int code;
    private String responseText;
    private String contentType;
    private String contentEncoding;

    private Map<String, Header> headersMap;

    public HttpRequestResult(int code) {
        this(code, null, null, null, null);
    }

    public HttpRequestResult(int code, Header[] headers) {
        this(code, null, null, null, headers);
    }


    public HttpRequestResult(int code, String responseText, String contentType
            , String contentEncoding, Header[] headers) {
        this.code = code;
        this.responseText = getNotNullString(responseText);
        this.contentType = getNotNullString(contentType);
        this.contentEncoding = getNotNullString(contentEncoding);
        headersMap = new LinkedHashMap<>();
        addHeaders(headers);
    }

    private void addHeaders(Header[] headers) {
        if (null != headers) {
            for (Header header : headers) {
                headersMap.put(header.getName(), header);
            }
        }
    }

    private String getNotNullString(String str) {
        return null == str ? "" : str;
    }

    private Header[] getNotNullHeaders(Header[] headers) {
        return null == headers ? new Header[0] : headers;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = getNotNullString(responseText);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = getNotNullString(contentType);
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = getNotNullString(contentEncoding);
    }

    public Header getHeader(String name) {
        return headersMap.get(name);
    }

    public Header[] getAllHeaders() {
        return headersMap.values().toArray(new Header[headersMap.size()]);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HttpRequestResult{");
        sb.append("code=").append(code);
        sb.append(", responseText='").append(responseText).append('\'');
        sb.append(", contentType='").append(contentType).append('\'');
        sb.append(", contentEncoding='").append(contentEncoding).append('\'');
        sb.append(", headersMap=").append(headersMap);
        sb.append('}');
        return sb.toString();
    }
}
