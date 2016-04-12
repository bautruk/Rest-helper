package com.exadel.service;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Provides common methods to work with requests and responses
 */
public interface MainService {

    /**
     * Creates HttpEntity object based on given parameters
     *
     * @param requestData map of request data. Parameters string contains request parameters in "name(param-delim)value(line-separator)" form
     * @return {@link org.apache.http.HttpEntity} object containing all parameters accepted in argument
     */
    public HttpEntity constructRequestBody(Map<String, String> requestData);

    /**
     * Adds a new parameter "encryptedPassword" to given parameters and replace them with new request parameters
     * in "name(param-delim)value(line-separator)" form: "data" - encoded with zero vector parameters (Base64 string),
     * "key" - key for decrypting data (Base64 string)
     *
     * @param requestData map of request data. Parameters string contains request parameters in "name(param-delim)value(line-separator)" form
     * @return key for decrypting encrypted password (Base64 string)
     */
    public String encodeRequestParameters(Map<String, String> requestData);

    /**
     * Creates an array of {@link org.apache.http.Header} objects from given headers
     *
     * @param requestHeaders String containing headers in "name(header-delim)value(line-separator)" form
     */
    public Header[] constructHeadersArray(String requestHeaders);

    /**
     * Creates {@link com.exadel.dto.Response} DTO containing response body, status code, status message and response headers
     * extracted from given {@link org.apache.http.HttpResponse} object
     */
    public Response constructSuccessResponse(HttpResponse response) throws IOException;
    public Response constructSuccessResponse(HttpResponse response, String responseBodyStr) throws IOException;

    /**
     * Creates {@link com.exadel.dto.PredefinedRequestData} object based on line separator that is used in implementation
     */
    public PredefinedRequestData createPredefinedRequestData();

    public String getResponseParameter(String responseBodyStr, HttpResponse response, String... keys) throws IOException;

    public String getResponseBody(HttpResponse response) throws IOException;

    String getUriParameter(Map<String, String> requestData);
    List<NameValuePair> constructGetMethodParameters(Map<String, String> requestData);
}
