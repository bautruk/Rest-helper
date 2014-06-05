package com.exadel.dto;

public class PredefinedRequestData {

    private String headerKeyValDelim;
    private String paramKeyValDelim;
    private String lineSeparator;

    private StringBuilder requestParams = new StringBuilder();
    private StringBuilder requestHeaders = new StringBuilder();

    public PredefinedRequestData(String headerKeyValDelim, String paramKeyValDelim, String lineSeparator) {
        this.headerKeyValDelim = headerKeyValDelim;
        this.paramKeyValDelim = paramKeyValDelim;
        this.lineSeparator = lineSeparator;
    }

    public String getRequestParams() {
        return requestParams.toString();
    }

    public String getRequestHeaders() {
        return requestHeaders.toString();
    }

    public void addRequestParam(String name, String value) {
        requestParams.append(name);
        requestParams.append(paramKeyValDelim);
        requestParams.append(value);
        requestParams.append(lineSeparator);
    }

    public void addRequestHeader(String name, String value) {
        requestHeaders.append(name);
        requestHeaders.append(headerKeyValDelim);
        requestHeaders.append(value);
        requestHeaders.append(lineSeparator);
    }
}
