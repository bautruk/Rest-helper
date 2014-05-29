package com.exadel.controller;

import com.exadel.dto.Response;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes(value = {"authToken"})
public class MainController {

    @RequestMapping("/")
    public String getMainPageName() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response authenticate(@RequestBody String requestParameters, Model model) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost authRequest = new HttpPost("http://localhost:25945/service/session/logins");
        authRequest.setEntity(constructRequestBody(requestParameters));

        HttpResponse response = client.execute(authRequest);
        Header[] headers = response.getHeaders("X-UBSAS-AuthToken");
        model.addAttribute("authToken", headers[0].getValue());

        return constructResponseRepresentation(response);
    }

    private HttpEntity constructRequestBody(String requestParameters) {
        List<NameValuePair> params = parseParametersString(requestParameters);

        try {
            return new UrlEncodedFormEntity(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<NameValuePair> parseParametersString(String requestParameters) {
        List<NameValuePair> parsedParameters = new ArrayList<NameValuePair>();
        String[] pairs = requestParameters.split("&");

        for (String pair : pairs) {
            String[] parsedPair = pair.split("=");
            parsedParameters.add(new BasicNameValuePair(parsedPair[0], parsedPair[1]));
        }

        return parsedParameters;
    }

    private Response constructResponseRepresentation(HttpResponse response) throws IOException {
        Response responseRepresentation = new Response();

        String responseBodyStr = EntityUtils.toString(response.getEntity());
        responseRepresentation.setBody(responseBodyStr);

        String statusMessage = "Status message: " + response.getStatusLine().getReasonPhrase();
        String statusCode = "Status code: " + response.getStatusLine().getStatusCode();
        String headers = Arrays.toString(response.getAllHeaders());

        String metaStr = statusMessage + "&#013;&#010;" + statusCode + "&#013;&#010;" + headers;

        responseRepresentation.setMeta(metaStr);

        return responseRepresentation;
    }
}
