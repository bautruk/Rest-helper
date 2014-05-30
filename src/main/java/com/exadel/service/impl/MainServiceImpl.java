package com.exadel.service.impl;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import com.exadel.service.MainService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MainServiceImpl implements MainService {
    private String paramKeyValDelim;

    private String headerKeyValDelim;

    private String lineSeparator;

    @Override
    public HttpEntity constructRequestBody(String requestParameters) {
        List<NameValuePair> params = parseParametersString(requestParameters);

        try {
            return new UrlEncodedFormEntity(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Header[] constructHeadersArray(String requestHeaders) {
        List<Header> headers = new ArrayList<Header>();
        String[] headersStrArr = requestHeaders.split(lineSeparator);

        for (String headerStr : headersStrArr) {
            String[] headerKeyVal = headerStr.split(headerKeyValDelim);
            headers.add(new BasicHeader(headerKeyVal[0], headerKeyVal[1]));
        }

        Header[] result = new Header[headers.size()];

        return headers.toArray(result);
    }

    @Override
    public Response constructSuccessResponse(HttpResponse response) throws IOException {
        Response responseRepresentation = new Response();

        String responseBodyStr = EntityUtils.toString(response.getEntity());
        responseRepresentation.setBody(responseBodyStr);

        String statusMessage = "Status message: " + response.getStatusLine().getReasonPhrase();
        String statusCode = "Status code: " + response.getStatusLine().getStatusCode();
        String headers = join(Arrays.asList(response.getAllHeaders()), lineSeparator);

        String metaStr = statusCode + lineSeparator + statusMessage + lineSeparator + headers;

        responseRepresentation.setMeta(metaStr);

        return responseRepresentation;
    }

    @Override
    public PredefinedRequestData createPredefinedRequestData() {
        return new PredefinedRequestData(lineSeparator);
    }

    public String getParamKeyValDelim() {
        return paramKeyValDelim;
    }

    public void setParamKeyValDelim(String paramKeyValDelim) {
        this.paramKeyValDelim = paramKeyValDelim;
    }

    public String getHeaderKeyValDelim() {
        return headerKeyValDelim;
    }

    public void setHeaderKeyValDelim(String headerKeyValDelim) {
        this.headerKeyValDelim = headerKeyValDelim;
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    private List<NameValuePair> parseParametersString(String requestParameters) {
        List<NameValuePair> parsedParameters = new ArrayList<NameValuePair>();
        String[] pairs = requestParameters.split(lineSeparator);

        for (String pair : pairs) {
            String[] parsedPair = pair.split("=");
            parsedParameters.add(new BasicNameValuePair(parsedPair[0], parsedPair[1]));
        }

        return parsedParameters;
    }

    private<T> String join(List<T> objects, String delimiter) {
        StringBuilder result = new StringBuilder();

        for (T object : objects) {
            result.append(object);
            result.append(delimiter);
        }

        // Removing the last lineSeparator
        result.delete(result.lastIndexOf(delimiter), result.length());

        return result.toString();
    }
}
