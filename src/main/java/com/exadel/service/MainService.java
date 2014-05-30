package com.exadel.service;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

public interface MainService {
    public HttpEntity constructRequestBody(String requestParameters);

    public Header[] constructHeadersArray(String requestHeaders);

    public Response constructErrorResponse(int statusCode, String statusMessage);

    public Response constructSuccessResponse(HttpResponse response) throws IOException;

    public PredefinedRequestData createPredefinedRequestData();
}
