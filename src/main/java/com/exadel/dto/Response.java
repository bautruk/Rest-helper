package com.exadel.dto;

import java.util.List;

/**
 * Created by pmitrafanau on 5/29/2014.
 */
public class Response {

    private String meta;
    private String body;
    private String headers;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
}
