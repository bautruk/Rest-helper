package com.exadel.controller;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import com.exadel.service.MainService;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/")
@SessionAttributes(value = {"authToken"})
public class MainController {

    @Autowired
    private MainService mainService;

    private final String loginUrl = "http://localhost:25945/service/session/logins";
    private final String forwardUrl = "http://localhost:25945/forwardurl";
    private final String exchangeUrl = "http://localhost:25945/exchjson/service/do/login";

    private HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
    private HttpContext httpContext;

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

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response authenticate(@RequestBody Map<String, String> requestData, Model model) throws IOException {
        HttpPost authRequest = new HttpPost(loginUrl);
        authRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));

        initHttpContext();

        HttpResponse response = httpClient.execute(authRequest, httpContext);
        Header[] responseHeaders = response.getHeaders("X-UBSAS-AuthToken");
        model.addAttribute("authToken", responseHeaders[0].getValue());

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/forwardUrl", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response forwardUrl(@RequestBody Map<String, String> requestData) throws IOException {
        httpClient = HttpClients.createDefault();
        HttpGet forwardRequest = new HttpGet(forwardUrl);
        forwardRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(forwardRequest, httpContext);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchange", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response exchange(@RequestBody Map<String, String> requestData) throws IOException {
        HttpPost exchangeRequest = new HttpPost(exchangeUrl);
        exchangeRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));
        exchangeRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(exchangeRequest, httpContext);

        return mainService.constructSuccessResponse(response);
    }

    private void initHttpContext() {
        CookieStore cookieStore = new BasicCookieStore();
        httpContext = new BasicHttpContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
    }
}
