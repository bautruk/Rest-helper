package com.exadel.service.impl;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import com.exadel.security.certificate.CertificateManager;
import com.exadel.security.encoder.Encoder;
import com.exadel.service.MainService;
import org.apache.commons.codec.binary.Base64;
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
    private static final String CONTENT_TYPE_TEXT = "text";
    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    private static final String LINE_SEPARATOR = "\n";

    private ObjectMapper jsonMapper;
    private String paramKeyValDelim;
    private String headerKeyValDelim;

    private Encoder loginDataEncoder;
    private CertificateManager certificateManager;

    private String encodeData(String requestParameters) {
        try {
            requestParameters = jsonMapper.writeValueAsString(parseParametersStringJson(requestParameters));
            byte[] key = loginDataEncoder.generateKey();
            String encryptedData = Base64.encodeBase64String(
                    loginDataEncoder.encodeWithZeroVector(requestParameters.getBytes(), key));
            String encryptedKey = certificateManager.encodeWIthCertificate(key);
            PredefinedRequestData predefinedRequestData = createPredefinedRequestData();
            predefinedRequestData.addRequestParam("data", encryptedData);
            predefinedRequestData.addRequestParam("key", encryptedKey);
            return predefinedRequestData.getRequestParams();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return requestParameters;
    }

    @Override
    public String encodeRequestParameters(Map<String, String> requestData){
        String requestParameters = requestData.get("parameters").trim();
        Map <String, String> parameters = parseParametersStringJson(requestParameters);
        String encodedPasswordKey = "";
        try {
            byte[] passwordKey = loginDataEncoder.generateKey();
            encodedPasswordKey = Base64.encodeBase64String(passwordKey);
            String encryptedPassword = Base64.encodeBase64String(
                    loginDataEncoder.encodeWithZeroVector(parameters.get("password").getBytes(), passwordKey));
            StringBuilder newRequestParameters = new StringBuilder(requestParameters);
            newRequestParameters.append(LINE_SEPARATOR);
            newRequestParameters.append("encryptedPassword");
            newRequestParameters.append(paramKeyValDelim);
            newRequestParameters.append(encryptedPassword);
            newRequestParameters.append(LINE_SEPARATOR);
            requestData.put("parameters", encodeData(newRequestParameters.toString()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return encodedPasswordKey;
    }


    @Override
    public HttpEntity constructRequestBody(Map<String, String> requestData) {
        String contentType = requestData.get("type");
        String requestParameters = requestData.get("parameters");
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
        } else if (CONTENT_TYPE_TEXT.equals(contentType)) {
            return new StringEntity(requestParameters, "UTF-8");
        }

        return null;
    }

    @Override
    public Header[] constructHeadersArray(String requestHeaders) {
        List<Header> headers = new ArrayList<Header>();
        String[] headersStrArr = requestHeaders.split(LINE_SEPARATOR);

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

        HttpEntity entity = response.getEntity();
        String responseBodyStr = "";
        if (entity != null) {
            responseBodyStr = EntityUtils.toString(entity);
        }

        responseRepresentation.setBody(responseBodyStr);

        String statusMessage = "Status message: " + response.getStatusLine().getReasonPhrase();
        String statusCode = "Status code: " + response.getStatusLine().getStatusCode();
        String headers = join(Arrays.asList(response.getAllHeaders()), LINE_SEPARATOR);

        String metaStr = statusCode + LINE_SEPARATOR + statusMessage + LINE_SEPARATOR + headers;

        responseRepresentation.setMeta(metaStr);

        return responseRepresentation;
    }

    @Override
    public Response constructSuccessResponse(HttpResponse response, String responseBodyStr) throws IOException {
        Response responseRepresentation = new Response();

        responseRepresentation.setBody(responseBodyStr);

        String statusMessage = "Status message: " + response.getStatusLine().getReasonPhrase();
        String statusCode = "Status code: " + response.getStatusLine().getStatusCode();
        String headers = join(Arrays.asList(response.getAllHeaders()), LINE_SEPARATOR);

        String metaStr = statusCode + LINE_SEPARATOR + statusMessage + LINE_SEPARATOR + headers;

        responseRepresentation.setMeta(metaStr);

        return responseRepresentation;
    }

    @Override
    public String getResponseBody(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    @Override
    public String getResponseParameter(String responseBodyStr, HttpResponse response, String... keys) throws IOException {
        Object resultValue;
        String result = null;
        String contentType = response.getEntity().getContentType().getValue();
        if (CONTENT_TYPE_APPLICATION_JSON.equals(contentType)) {
            resultValue = jsonMapper.readValue(responseBodyStr, Map.class);
            for (String key : keys) {
                if (resultValue instanceof Map) {
                    resultValue = ((Map) resultValue).get(key);
                }
            }
            if (resultValue != null && resultValue instanceof String) {
                result = (String) resultValue;
            }
        }
        
        return result;
    }

    @Override
    public PredefinedRequestData createPredefinedRequestData() {
        return new PredefinedRequestData(headerKeyValDelim, paramKeyValDelim, LINE_SEPARATOR);
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
        return LINE_SEPARATOR;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void setLoginDataEncoder(Encoder loginDataEncoder) {
        this.loginDataEncoder = loginDataEncoder;
    }

    public void setCertificateManager(CertificateManager certificateManager) {
        this.certificateManager = certificateManager;
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
        String[] pairs = requestParameters.split(LINE_SEPARATOR);

        for (String pair : pairs) {
            String[] parsedPair = pair.split("=", 2);
            if (parsedPair.length == 2) {
                parametersMap.put(parsedPair[0], parsedPair[1]);
            }
        }

        return parametersMap;
    }

    private<T> String join(List<T> objects, String delimiter) {
        StringBuilder result = new StringBuilder();

        for (T object : objects) {
            result.append(object);
            result.append(delimiter);
        }

        result.delete(result.lastIndexOf(delimiter), result.length());

        return result.toString();
    }
}
