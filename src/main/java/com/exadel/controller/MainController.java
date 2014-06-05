package com.exadel.controller;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import com.exadel.service.MainService;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
@SessionAttributes(value = {"authToken", "setCookieHeaderValue", "exchgToken"})
public class MainController {

    @Autowired
    private MainService mainService;

    private final String loginUrl = "http://localhost:80/service/session/logins";
    private final String forwardUrl = "http://localhost:80/forwardurl";
    private final String exchangeUrl = "http://localhost:80/exchjson/service/do/login";
    private final String foldersUrl = "http://localhost:80/exchjson/service/do/folders";

    private HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();

    @RequestMapping("/")
    public String getMainPageName() {
        return "index";
    }

    @RequestMapping(value = "/login/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData getPredefParamsForLogin() {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestParam("username", "skoval");
        requestData.addRequestParam("password", "Exadel1");
        requestData.addRequestParam("domain", "botf03.net");

        return requestData;
    }

    @RequestMapping(value = "/forwardUrl/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData getPredefParamsForForward(@ModelAttribute("authToken") String authToken) {
        // TODO: check if authToken is empty
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-UBSAS-Proxy-Authorization", authToken);
        requestData.addRequestHeader("X-UBSAS-FORWARDURL", "http://www.google.com/");

        return requestData;
    }

    @RequestMapping(value = "/exchange/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData getPredefParamsForExchange(@ModelAttribute("authToken") String authToken) {
        // TODO: check if authToken is empty
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();

        requestData.addRequestParam("username", "skoval");
        requestData.addRequestParam("password", "Exadel1");
        requestData.addRequestParam("domain", "botf03.net");
        requestData.addRequestParam("RequestId", "authStepOne");
        requestData.addRequestParam("jsonSupport", "true");

        requestData.addRequestHeader("Authorization", "Bearer " + authToken);

        return requestData;
    }

    @RequestMapping(value = "/folders/predefined")
    @ResponseBody
    public PredefinedRequestData getPredefParamsForFolders(@ModelAttribute("authToken") String authToken,
                                                           @ModelAttribute("setCookieHeaderValue") String setCookieHeaderValue,
                                                           @ModelAttribute("exchgToken") String exchgToken) {
        // TODO: check if authToken is empty
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();

        requestData.addRequestHeader("Cookie", setCookieHeaderValue);
        requestData.addRequestHeader("Authorization", "Bearer " + authToken);
        requestData.addRequestHeader("X-UBSAS-Exchg-Token", exchgToken);

        requestData.addRequestParam("AllTree", "false");

        return requestData;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response authenticate(@RequestBody Map<String, String> requestData, Model model) throws IOException {
        HttpPost authRequest = new HttpPost(loginUrl);
        authRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));

        HttpResponse response = httpClient.execute(authRequest);
        Header[] responseHeaders = response.getHeaders("X-UBSAS-AuthToken");

        model.addAttribute("authToken", responseHeaders[0].getValue());
        model.addAttribute("setCookieHeaderValue", "");

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/forwardUrl", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response forwardUrl(@RequestBody Map<String, String> requestData) throws IOException {
        httpClient = HttpClients.createDefault();
        HttpGet forwardRequest = new HttpGet(forwardUrl);
        forwardRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(forwardRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchange", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response exchange(@RequestBody Map<String, String> requestData,
                             @ModelAttribute(value = "setCookieHeaderValue") String setCookieHeaderValue,
                             Model model) throws IOException {

        HttpPost exchangeRequest = new HttpPost(exchangeUrl);
        exchangeRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));
        exchangeRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        if ((setCookieHeaderValue != null) && !setCookieHeaderValue.isEmpty()) {
            exchangeRequest.setHeader("Cookie", setCookieHeaderValue);
        }

        HttpResponse response = httpClient.execute(exchangeRequest);
        Response result = mainService.constructSuccessResponse(response);
        Matcher matcher = getMatcherForSecurityToken(result);

        // check matcher.find()
        if (matcher.find() || (setCookieHeaderValue == null) || setCookieHeaderValue.isEmpty()) {
            String exchgToken = matcher.group(1);
            model.addAttribute("setCookieHeaderValue", response.getFirstHeader("Set-Cookie").getValue());
            model.addAttribute("exchgToken", exchgToken);
        }

        return result;
    }

    @RequestMapping(value = "/folders", method = RequestMethod.POST)
    @ResponseBody
    public Response folders(@RequestBody Map<String, String> requestData) throws IOException {
        HttpPost foldersRequest = new HttpPost(foldersUrl);
        foldersRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));
        foldersRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(foldersRequest);

        return mainService.constructSuccessResponse(response);
    }

    private Matcher getMatcherForSecurityToken(Response response) {
        Pattern pattern = Pattern.compile("\"SecurityToken\":\"(.*)\"");
        return pattern.matcher(response.getBody());
    }
}
