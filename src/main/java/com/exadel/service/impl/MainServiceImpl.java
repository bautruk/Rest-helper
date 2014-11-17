package com.exadel.service.impl;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import com.exadel.service.MainService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class MainServiceImpl implements MainService {
    private static final String CONTENT_TYPE_FORM = "form";
    private static final String CONTENT_TYPE_JSON = "json";

    private ObjectMapper jsonMapper;
    private String paramKeyValDelim;
    private String headerKeyValDelim;
    private String lineSeparator;

    @Override
    public HttpEntity constructRequestBody(Map<String, String> requestData) {
        String contentType = requestData.get("type");
        String requestParameters = requestData.get("parameters").trim();

        if (CONTENT_TYPE_FORM.equals(contentType)) {
            try {
                return new UrlEncodedFormEntity(parseParametersStringForm(requestParameters), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (CONTENT_TYPE_JSON.equals(contentType)) {
            try {
                return new StringEntity(jsonMapper.writeValueAsString(parseParametersStringJson(requestParameters)), ContentType.APPLICATION_JSON);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public Header[] constructHeadersArray(String requestHeaders) {
        List<Header> headers = new ArrayList<Header>();
        String[] headersStrArr = requestHeaders.split(lineSeparator);

        for (String headerStr : headersStrArr) {
            String[] headerKeyVal = headerStr.split(headerKeyValDelim, 2);
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
        return new PredefinedRequestData(headerKeyValDelim, paramKeyValDelim, lineSeparator);
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

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    private List<NameValuePair> parseParametersStringForm(String requestParameters) {
        List<NameValuePair> parsedParameters = new ArrayList<NameValuePair>();
        HashMap<String, String> parameters = parseParametersStringJson(requestParameters);
        for (String parameter : parameters.keySet()) {
            parsedParameters.add(new BasicNameValuePair(parameter, parameters.get(parameter)));
        }

        return parsedParameters;
    }

    private HashMap<String, String> parseParametersStringJson(String requestParameters) {
        HashMap<String, String> parametersMap = new HashMap<String, String>();
        String[] pairs = requestParameters.split(lineSeparator);

        for (String pair : pairs) {
            String[] parsedPair = pair.split("=");
            parametersMap.put(parsedPair[0], parsedPair[1]);
        }

        return parametersMap;
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
