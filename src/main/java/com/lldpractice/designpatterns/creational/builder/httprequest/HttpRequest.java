package com.lldpractice.designpatterns.creational.builder.httprequest;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HttpRequest {
    // Implementation goes here
    private final String url;
    private final HttpMethod method;
    private final String body;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;
    private final int timeout;
    private final boolean followRedirects;

    @Override
    public String toString() {
        return "HttpRequest {\n" +
                "  URL: " + url + "\n" +
                "  Method: " + method + "\n" +
                "  Headers: " + headers + "\n" +
                "  Query Params: " + queryParams + "\n" +
                "  Body: " + body + "\n" +
                "  Timeout: " + timeout + "s\n" +
                "  Follow Redirects: " + followRedirects + "\n" +
                "}";
    }

    private HttpRequest(HttpRequestBuilder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.body = builder.body;
        this.headers = Map.copyOf(builder.headers);
        this.queryParams = Map.copyOf(builder.queryParams);
        this.timeout = builder.timeout;
        this.followRedirects = builder.followRedirects;
    }

    public static class HttpRequestBuilder {
        private final String url;
        private final HttpMethod method;
        private String body = null;
        private Map<String, String> headers = new HashMap<>();
        private Map<String, String> queryParams = new HashMap<>();
        private int timeout = 30;
        private boolean followRedirects = true;

        public HttpRequestBuilder(String url, HttpMethod method) {
            if (StringUtils.isBlank(url)) {
                throw new IllegalArgumentException("URL cannot be null or empty.");
            }
            if (method == null) {
                throw new IllegalArgumentException("HTTP Method cannot be null.");
            }
            this.method = method;
            this.url = url;
        }

        public HttpRequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequestBuilder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public HttpRequestBuilder followRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        public HttpRequestBuilder headers(Map<String, String> headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }


        public HttpRequestBuilder queryParams(Map<String, String> queryParams) {
            if (queryParams != null) {
                this.queryParams.putAll(queryParams);
            }
            return this;
        }


        public HttpRequestBuilder addHeader(String key, String value) {
            if (key != null && value != null) {
                this.headers.put(key, value);
            }
            return this;
        }

        public HttpRequestBuilder addQueryParam(String key, String value) {
            if (key != null && value != null) {
                this.queryParams.put(key, value);
            }
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}
